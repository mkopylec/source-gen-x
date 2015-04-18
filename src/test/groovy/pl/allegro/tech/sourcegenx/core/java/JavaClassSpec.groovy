package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import pl.allegro.tech.sourcegenx.exceptions.InvalidValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.CommonUtils.emptyValues
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PROTECTED
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC
import static pl.allegro.tech.sourcegenx.core.java.Modifier.ABSTRACT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.DEFAULT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.FINAL
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC_FINAL

class JavaClassSpec extends Specification {

    def "Should create Java class"() {
        given:
        def message = new Field(PRIVATE, FINAL, 'String', 'message')
                .addAnnotation(new Annotation('Deprecated'))

        def constructor = new Constructor('MessageProvider')
                .addAnnotation(new Annotation('Logged').addAttribute('level', 'INFO'))
                .addParameter(new Parameter('String', 'message').addAnnotation(new Annotation('NotNull')))
                .setBody('    this.message = message;')

        def concatToMessage = new Method(PUBLIC, FINAL, 'String', 'concatToMessage')
                .addAnnotation(new Annotation('Logged').addAttribute('level', 'ERROR'))
                .addParameter(new Parameter('String', 'text', FINAL).addAnnotation(new Annotation('NotNull')))
                .addParameter(new Parameter('int', 'number'))
                .addThrownException('Exception')
                .addThrownException('RuntimeException')
                .setBody('    if (message.length() < MAX_MESSAGE_LENGTH) {\n' +
                '        return message + text + number;\n' +
                '    }\n' +
                '    throw new Exception("Invalid message length");')

        def javaClass = new JavaClass('MessageProvider', 'pl.allegro.tech', 'MessageProvider', 'src', PUBLIC, ABSTRACT)
                .addImport(new Import('java.io.Serializable'))
                .addImport(new Import('pl.allegro.tech.Logged'))
                .addImport(new Import('pl.allegro.tech.NotNull'))
                .addImport(new Import('pl.allegro.tech.Provider'))
                .addImport(new Import(STATIC, 'pl.allegro.tech.LogLevel.INFO'))
                .addImport(new Import(STATIC, 'pl.allegro.tech.LogLevel.ERROR'))
                .addAnnotation(new Annotation('SuppressWarnings').addAttribute('value', '"unchecked"'))
                .setSuperClass("Object")
                .addImplementedInterface("Serializable")
                .addImplementedInterface("Provider")
                .addField(new Constant('int', 'MAX_MESSAGE_LENGTH', '64'))
                .addField(message)
                .addMethod(constructor)
                .addMethod(concatToMessage)
                .addMethod(new AbstractMethod('void', 'print'))

        when:
        def string = javaClass.toString().replaceAll('\r', '')

        then:
        string == getClass().getResource('/MessageProvider.txt').text
    }

    @Unroll
    def "Should fail to create Java class when package name is '#packageName'"() {
        when:
        new JavaClass(packageName, 'MessageProvider', 'src')

        then:
        thrown EmptyValueException

        where:
        packageName << emptyValues()
    }

    @Unroll
    def "Should fail to create Java class when class name is '#className'"() {
        when:
        new JavaClass('pl.allegro.tech', className, 'src')

        then:
        thrown EmptyValueException

        where:
        className << emptyValues()
    }

    def "Should fail to create Java class when access modifier is empty"() {
        when:
        new JavaClass('MessageProvider', 'pl.allegro.tech', 'MessageProvider', 'src', null, NONE)

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to create Java class when access modifier is '#accessModifier'"() {
        when:
        new JavaClass('MessageProvider', 'pl.allegro.tech', 'MessageProvider', 'src', accessModifier, NONE)

        then:
        thrown InvalidValueException

        where:
        accessModifier << [PROTECTED, PRIVATE]
    }

    def "Should fail to create Java class when modifier is empty"() {
        when:
        new JavaClass('MessageProvider', 'pl.allegro.tech', 'MessageProvider', 'src', PUBLIC, null)

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to create Java class when modifier is '#modifier'"() {
        when:
        new JavaClass('MessageProvider', 'pl.allegro.tech', 'MessageProvider', 'src', PUBLIC, modifier)

        then:
        thrown InvalidValueException

        where:
        modifier << [STATIC, STATIC_FINAL, DEFAULT]
    }

    def "Should fail to add empty import to Java class"() {
        given:
        def javaClass = new JavaClass('pl.allegro.tech', 'MessageProvider', 'src')

        when:
        javaClass.addImport(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add empty annotation to Java class"() {
        given:
        def javaClass = new JavaClass('pl.allegro.tech', 'MessageProvider', 'src')

        when:
        javaClass.addAnnotation(null)

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to set '#superClass' super class to Java class"() {
        given:
        def javaClass = new JavaClass('pl.allegro.tech', 'MessageProvider', 'src')

        when:
        javaClass.setSuperClass(superClass)

        then:
        thrown EmptyValueException

        where:
        superClass << emptyValues()
    }

    @Unroll
    def "Should fail to add '#interfac' implemented interface to Java class"() {
        given:
        def javaClass = new JavaClass('pl.allegro.tech', 'MessageProvider', 'src')

        when:
        javaClass.addImplementedInterface(interfac)

        then:
        thrown EmptyValueException

        where:
        interfac << emptyValues()
    }

    def "Should fail to add empty field to Java class"() {
        given:
        def javaClass = new JavaClass('pl.allegro.tech', 'MessageProvider', 'src')

        when:
        javaClass.addField(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add empty method to Java class"() {
        given:
        def javaClass = new JavaClass('pl.allegro.tech', 'MessageProvider', 'src')

        when:
        javaClass.addMethod(null)

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to add #description method to Java class"() {
        given:
        def javaClass = new JavaClass('pl.allegro.tech', 'MessageProvider', 'src')

        when:
        javaClass.addMethod(method)

        then:
        thrown InvalidValueException

        where:
        method                                      | description
        new InterfaceMethod('String', 'getMessage') | 'interface'
        new EnumConstructor('MessageType')          | 'enum constructor'
    }
}
