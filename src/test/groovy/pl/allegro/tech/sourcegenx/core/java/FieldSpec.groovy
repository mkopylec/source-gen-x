package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import pl.allegro.tech.sourcegenx.exceptions.InvalidValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.SpecCommonUtils.emptyValues
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PROTECTED
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC
import static pl.allegro.tech.sourcegenx.core.java.Modifier.ABSTRACT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.DEFAULT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC

class FieldSpec extends Specification {

    @Unroll
    def "Should create #accessModifier #modifier field without initial value"() {
        given:
        def field = new Field(accessModifier, modifier, "String", "message")

        when:
        def string = field.toString()

        then:
        string == result

        where:
        accessModifier  | modifier | result
        PUBLIC          | NONE     | 'public String message;'
        PROTECTED       | STATIC   | 'protected static String message;'
        PACKAGE_PRIVATE | NONE     | 'String message;'
        PRIVATE         | STATIC   | 'private static String message;'
    }

    def "Should create annotated field with initial value"() {
        given:
        def field = new Field('String', 'message')
                .addAnnotation(new Annotation('Deprecated'))
                .addAnnotation(new Annotation('NotNull'))
                .setValue('"I am message"')

        when:
        def string = field.toString()

        then:
        string ==
                '@Deprecated\n' +
                '@NotNull\n' +
                'private String message = "I am message";'
    }

    def "Should fail to create field when access modifier is empty"() {
        when:
        new Field(null, NONE, 'String', 'message')

        then:
        thrown EmptyValueException
    }

    def "Should fail to create field when modifier is empty"() {
        when:
        new Field(PRIVATE, null, 'String', 'message')

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to create field when modifier is #modifier"() {
        when:
        new Field(PRIVATE, modifier, 'String', 'message')

        then:
        thrown InvalidValueException

        where:
        modifier << [ABSTRACT, DEFAULT]
    }

    @Unroll
    def "Should fail to create field when return type is '#returnType'"() {
        when:
        new Field(PRIVATE, NONE, returnType, 'message')

        then:
        thrown EmptyValueException

        where:
        returnType << emptyValues()
    }

    @Unroll
    def "Should fail to create field when name is '#name'"() {
        when:
        new Field(PRIVATE, NONE, 'String', name)

        then:
        thrown EmptyValueException

        where:
        name << emptyValues()
    }

    def "Should fail to add empty annotation to field"() {
        given:
        def field = new Field('String', 'message')

        when:
        field.addAnnotation(null)

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to set '#value' field initial value"() {
        given:
        def field = new Field('String', 'message')

        when:
        field.setValue(value)

        then:
        thrown EmptyValueException

        where:
        value << emptyValues()
    }
}
