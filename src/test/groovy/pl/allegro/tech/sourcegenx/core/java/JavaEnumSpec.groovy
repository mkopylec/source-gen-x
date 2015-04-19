package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import pl.allegro.tech.sourcegenx.exceptions.InvalidValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.CommonUtils.emptyValues
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PROTECTED
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC
import static pl.allegro.tech.sourcegenx.core.java.Modifier.FINAL
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC

class JavaEnumSpec extends Specification {

    def "Should create Java enum"() {
        given:
        def constructor = new EnumConstructor('MessageType')
                .addParameter(new Parameter('String', 'name'))
                .addParameter(new Parameter('int', 'length'))
                .setBody('    this.name = name;\n' +
                '    this.length = length;')

        def getName = new Method('String', 'getName')
                .addAnnotation(new Annotation('Logged').addAttribute('level', 'INFO'))
                .setBody('    return name;')

        def getLength = new Method(PUBLIC, FINAL, 'int', 'getLength')
                .addThrownException('Exception')
                .setBody('    return length;')

        def toString = new Method('String', 'toString')
                .addAnnotation(new Annotation('Override'))
                .setBody('    return name;')

        def javaEnum = new JavaEnum('src', 'pl.allegro.tech', PUBLIC, 'MessageType')
                .addImport(new Import('java.io.Serializable'))
                .addImport(new Import('pl.allegro.tech.Logged'))
                .addImport(new Import(STATIC, 'pl.allegro.tech.LogLevel.INFO'))
                .addAnnotation(new Annotation('SuppressWarnings').addAttribute('value', '"unchecked"'))
                .addAnnotation(new Annotation('Deprecated'))
                .addImplementedInterface('Serializable')
                .addValue(new EnumValue('TEXT').addArgument('"txt"').addArgument('1024'))
                .addValue(new EnumValue('BINARY').addArgument('"bin"').addArgument('2048'))
                .addField(new Constant('String', 'EMPTY_MESSAGE', '""'))
                .addField(new Field('String', 'name').addAnnotation(new Annotation('Deprecated')))
                .addField(new Field(PRIVATE, FINAL, 'int', 'length'))
                .addMethod(constructor)
                .addMethod(getName)
                .addMethod(getLength)
                .addMethod(toString)

        when:
        def string = javaEnum.toString().replaceAll('\r', '')

        then:
        string == getClass().getResource('/MessageType.txt').text
    }

    @Unroll
    def "Should fail to create Java enum when package name is '#packageName'"() {
        when:
        new JavaEnum('src', packageName, 'MessageType')

        then:
        thrown EmptyValueException

        where:
        packageName << emptyValues()
    }

    @Unroll
    def "Should fail to create Java enum when interface name is '#enumName'"() {
        when:
        new JavaEnum('src', 'pl.allegro.tech', enumName)

        then:
        thrown EmptyValueException

        where:
        enumName << emptyValues()
    }

    def "Should fail to create Java enum when access modifier is empty"() {
        when:
        new JavaEnum('src', 'pl.allegro.tech', null, 'MessageType')

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to create Java enum when access modifier is '#accessModifier'"() {
        when:
        new JavaEnum('src', 'pl.allegro.tech', accessModifier, 'MessageType')

        then:
        thrown InvalidValueException

        where:
        accessModifier << [PROTECTED, PRIVATE]
    }

    def "Should fail to add empty import to Java enum"() {
        given:
        def javaEnum = new JavaEnum('src', 'pl.allegro.tech', 'MessageType')

        when:
        javaEnum.addImport(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add empty annotation to Java enum"() {
        given:
        def javaEnum = new JavaEnum('src', 'pl.allegro.tech', 'MessageType')

        when:
        javaEnum.addAnnotation(null)

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to add '#interfac' implemented interface to Java enum"() {
        given:
        def javaEnum = new JavaEnum('src', 'pl.allegro.tech', 'MessageType')

        when:
        javaEnum.addImplementedInterface(interfac)

        then:
        thrown EmptyValueException

        where:
        interfac << emptyValues()
    }

    def "Should fail to add empty value to Java enum"() {
        given:
        def javaEnum = new JavaEnum('src', 'pl.allegro.tech', 'MessageType')

        when:
        javaEnum.addValue(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add empty field to Java enum"() {
        given:
        def javaEnum = new JavaEnum('src', 'pl.allegro.tech', 'MessageType')

        when:
        javaEnum.addField(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add empty method to Java enum"() {
        given:
        def javaEnum = new JavaEnum('src', 'pl.allegro.tech', 'MessageType')

        when:
        javaEnum.addMethod(null)

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to add #description method to Java enum"() {
        given:
        def javaEnum = new JavaEnum('src', 'pl.allegro.tech', 'MessageType')

        when:
        javaEnum.addMethod(method)

        then:
        thrown InvalidValueException

        where:
        method                                      | description
        new InterfaceMethod('String', 'getMessage') | 'interface'
        new AbstractMethod('String', 'getMessage')  | 'abstract'
        new Constructor('MessageType')              | 'constructor'
    }

    def "Should fail to add invalid constructor to Java enum"() {
        given:
        def javaEnum = new JavaEnum('src', 'pl.allegro.tech', 'MessageType')

        when:
        javaEnum.addMethod(new Constructor('InvalidName'))

        then:
        thrown InvalidValueException
    }
}
