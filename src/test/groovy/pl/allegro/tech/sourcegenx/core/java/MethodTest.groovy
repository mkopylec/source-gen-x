package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PROTECTED
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC
import static pl.allegro.tech.sourcegenx.core.java.Modifier.ABSTRACT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.FINAL
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC_FINAL

class MethodTest extends Specification {

    private static final def EMPTY_VALUES = [null, '', ' ', '  ']

    @Unroll
    def "Should create empty #accessModifier #modifier method"() {
        given:
        Method method = new Method(accessModifier, modifier, 'String', 'getMessage')

        when:
        def string = method.toString()

        then:
        "$string" == "$accessModifier $modifier String getMessage() {\n\n}"

        where:
        accessModifier  | modifier
        PUBLIC          | ABSTRACT
        PROTECTED       | STATIC
        PACKAGE_PRIVATE | STATIC_FINAL
        PRIVATE         | NONE
        PRIVATE         | FINAL
    }

    def "Should create non empty, annotated method with parameters"() {
        given:
        Method method = new Method('String', 'getMessage')
                .setBody('\treturn "I am message";')
                .addAnnotation(new Annotation("Deprecated"))
                .addAnnotation(new Annotation("Override"))
                .addParameter(new Parameter("int", "age"))
                .addParameter(new Parameter("boolean", "valid"))

        when:
        def string = method.toString()

        then:
        string ==
                '@Deprecated\n' +
                '@Override\n' +
                'public  String getMessage( int age,  boolean valid) {\n' +
                '\treturn "I am message";\n' +
                '}'
    }

    def "Should fail to create method when access modifier is empty"() {
        when:
        new Method(null, NONE, 'String', 'getMessage')

        then:
        thrown EmptyValueException
    }

    def "Should fail to create method when modifier is empty"() {
        when:
        new Method(PUBLIC, null, 'String', 'getMessage')

        then:
        thrown EmptyValueException
    }

    def "Should fail to create method when return type is null"() {
        when:
        new Method(null, 'getMessage')

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to create method when name is '#name'"() {
        when:
        new Method('String', name)

        then:
        thrown EmptyValueException

        where:
        name << EMPTY_VALUES
    }

    def "Should fail to add empty annotation to method"() {
        given:
        def method = new Method('String', 'getMessage')

        when:
        method.addAnnotation(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add empty parameter to method"() {
        given:
        def method = new Method('String', 'getMessage')

        when:
        method.addParameter(null)

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to set '#body' method body"() {
        given:
        def method = new Method('String', 'getMessage')

        when:
        method.setBody(body)

        then:
        thrown EmptyValueException

        where:
        body << EMPTY_VALUES
    }
}
