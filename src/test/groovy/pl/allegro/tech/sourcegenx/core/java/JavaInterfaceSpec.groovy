package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import pl.allegro.tech.sourcegenx.exceptions.InvalidValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.CommonUtils.emptyValues
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PROTECTED
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC
import static pl.allegro.tech.sourcegenx.core.java.Modifier.DEFAULT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC

class JavaInterfaceSpec extends Specification {

    def "Should create Java interface"() {
        def defaultMessage = new InterfaceConstant('String', 'DEFAULT_MESSAGE', 'new Message()')
                .addAnnotation(new Annotation('Deprecated'))

        def findById = new InterfaceMethod(DEFAULT, 'Message', 'findById')
                .addAnnotation(new Annotation('Logged').addAttribute('level', 'INFO'))
                .addParameter(new Parameter('long', 'id').addAnnotation(new Annotation('NotNull')))
                .addThrownException('RuntimeException')
                .setBody('    return null;')

        def findAll = new InterfaceMethod('List<Message>', 'findAll')
                .addThrownException('DaoException')
                .addThrownException('RuntimeException')

        def javaInterface = new JavaInterface('src', 'MessageDao', 'pl.allegro.tech', PUBLIC, 'MessageDao')
                .addImport(new Import('java.io.Serializable'))
                .addImport(new Import('java.util.List'))
                .addImport(new Import('pl.allegro.tech.Dao'))
                .addImport(new Import('pl.allegro.tech.DaoException'))
                .addImport(new Import('pl.allegro.tech.Logged'))
                .addImport(new Import('pl.allegro.tech.Message'))
                .addImport(new Import('pl.allegro.tech.NotNull'))
                .addImport(new Import(STATIC, 'pl.allegro.tech.LogLevel.INFO'))
                .addAnnotation(new Annotation('SuppressWarnings').addAttribute('value', '"unchecked"'))
                .addSuperInterface('Dao<Message>')
                .addSuperInterface('Serializable')
                .addConstant(defaultMessage)
                .addMethod(findById)
                .addMethod(findAll)

        when:
        def string = javaInterface.toString().replaceAll('\r', '')

        then:
        string == getClass().getResource('/MessageDao.txt').text
    }

    @Unroll
    def "Should fail to create Java interface when package name is '#packageName'"() {
        when:
        new JavaInterface('src', packageName, 'MessageDao')

        then:
        thrown EmptyValueException

        where:
        packageName << emptyValues()
    }

    @Unroll
    def "Should fail to create Java interface when interface name is '#interfaceName'"() {
        when:
        new JavaInterface('src', 'pl.allegro.tech', interfaceName)

        then:
        thrown EmptyValueException

        where:
        interfaceName << emptyValues()
    }

    def "Should fail to create Java interface when access modifier is empty"() {
        when:
        new JavaInterface('src', 'MessageDao', 'pl.allegro.tech', null, 'MessageDao')

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to create Java interface when access modifier is '#accessModifier'"() {
        when:
        new JavaInterface('src', 'MessageDao', 'pl.allegro.tech', accessModifier, 'MessageDao')

        then:
        thrown InvalidValueException

        where:
        accessModifier << [PROTECTED, PRIVATE]
    }

    def "Should fail to add empty import to Java interface"() {
        given:
        def javaInterface = new JavaInterface('src', 'pl.allegro.tech', 'MessageDao')

        when:
        javaInterface.addImport(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add empty annotation to Java interface"() {
        given:
        def javaInterface = new JavaInterface('src', 'pl.allegro.tech', 'MessageDao')

        when:
        javaInterface.addAnnotation(null)

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to add '#interfac' super interface to Java interface"() {
        given:
        def javaInterface = new JavaInterface('src', 'pl.allegro.tech', 'MessageDao')

        when:
        javaInterface.addSuperInterface(interfac)

        then:
        thrown EmptyValueException

        where:
        interfac << emptyValues()
    }

    def "Should fail to add empty interface constant to Java interface"() {
        given:
        def javaInterface = new JavaInterface('src', 'pl.allegro.tech', 'MessageDao')

        when:
        javaInterface.addConstant(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add empty interface method to Java interface"() {
        given:
        def javaInterface = new JavaInterface('src', 'pl.allegro.tech', 'MessageDao')

        when:
        javaInterface.addMethod(null)

        then:
        thrown EmptyValueException
    }
}
