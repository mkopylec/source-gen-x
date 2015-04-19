package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import pl.allegro.tech.sourcegenx.exceptions.InvalidValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.CommonUtils.emptyValues
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PROTECTED
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC

class JavaAnnotationSpec extends Specification {

    def "Should create Java annotation"() {
        given:
        def javaAnnotation = new JavaAnnotation('spec', 'pl.allegro.tech', PUBLIC, 'Message')
                .addImport(new Import('pl.allegro.tech.NotNull'))
                .addImport(new Import('java.lang.annotation.Retention'))
                .addImport(new Import('java.lang.annotation.Target'))
                .addImport(new Import(STATIC, 'java.lang.annotation.ElementType.FIELD'))
                .addImport(new Import(STATIC, 'java.lang.annotation.ElementType.PARAMETER'))
                .addImport(new Import(STATIC, 'java.lang.annotation.RetentionPolicy.RUNTIME'))
                .addAnnotation(new Annotation('Target').addAttribute('value', '{FIELD, PARAMETER}'))
                .addAnnotation(new Annotation('Retention').addAttribute('value', 'RUNTIME'))
                .addElement(new AnnotationElement('String', 'message').addAnnotation(new Annotation('NotNull')))
                .addElement(new AnnotationElement('Class<?>[]', 'type').setDefaultValue('{}'))

        when:
        def string = javaAnnotation.toString().replaceAll('\r', '')

        then:
        string == getClass().getResource('/Message.txt').text

        when:
        javaAnnotation.createSourceFile()

        then:
        new File('spec/Message.java').text.replaceAll('\r', '') == getClass().getResource('/Message.txt').text
    }

    @Unroll
    def "Should fail to create Java annotation when package name is '#packageName'"() {
        when:
        new JavaAnnotation('spec', packageName, 'Message')

        then:
        thrown EmptyValueException

        where:
        packageName << emptyValues()
    }

    @Unroll
    def "Should fail to create Java annotation when annotation name is '#annotationName'"() {
        when:
        new JavaAnnotation('spec', 'pl.allegro.tech', annotationName)

        then:
        thrown EmptyValueException

        where:
        annotationName << emptyValues()
    }

    def "Should fail to create Java annotation when access modifier is empty"() {
        when:
        new JavaAnnotation('spec', 'pl.allegro.tech', null, 'Message')

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to create Java annotation when access modifier is '#accessModifier'"() {
        when:
        new JavaAnnotation('spec', 'pl.allegro.tech', accessModifier, 'Message')

        then:
        thrown InvalidValueException

        where:
        accessModifier << [PROTECTED, PRIVATE]
    }

    def "Should fail to add empty import to Java annotation"() {
        given:
        def javaAnnotation = new JavaAnnotation('spec', 'pl.allegro.tech', 'Message')

        when:
        javaAnnotation.addImport(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add empty annotation to Java annotation"() {
        given:
        def javaAnnotation = new JavaAnnotation('spec', 'pl.allegro.tech', 'Message')

        when:
        javaAnnotation.addAnnotation(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add empty annotation element to Java annotation"() {
        given:
        def javaAnnotation = new JavaAnnotation('spec', 'pl.allegro.tech', 'Message')

        when:
        javaAnnotation.addElement(null)

        then:
        thrown EmptyValueException
    }
}
