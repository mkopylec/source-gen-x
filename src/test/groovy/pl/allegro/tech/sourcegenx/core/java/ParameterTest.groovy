package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import pl.allegro.tech.sourcegenx.exceptions.InvalidValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.core.java.Modifier.ABSTRACT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.DEFAULT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.FINAL
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC_FINAL

class ParameterTest extends Specification {

    private static final def EMPTY_VALUES = [null, '', ' ', '  ']

    @Unroll
    def "Should create a #modifier method parameter"() {
        given:
        Parameter parameter = new Parameter('String', 'message', modifier)

        when:
        def string = parameter.toString()

        then:
        "$string" == "$modifier String message"

        where:
        modifier << [NONE, FINAL]
    }

    @Unroll
    def "Should create an annotated #modifier method parameter"() {
        given:
        Parameter parameter = new Parameter('String', 'message', modifier)
                .addAnnotation(new Annotation('NotNull'))
                .addAnnotation(new Annotation('NotEmpty'))

        when:
        def string = parameter.toString()

        then:
        "$string" == "@NotNull @NotEmpty $modifier String message"

        where:
        modifier << [NONE, FINAL]
    }

    @Unroll
    def "Should fail to create method parameter when type is '#type'"() {
        when:
        new Parameter(type, 'message')

        then:
        thrown EmptyValueException

        where:
        type << EMPTY_VALUES
    }

    @Unroll
    def "Should fail to create method parameter when name is '#name'"() {
        when:
        new Parameter('String', name)

        then:
        thrown EmptyValueException

        where:
        name << EMPTY_VALUES
    }

    def "Should fail to create method parameter when modifier is empty"() {
        when:
        new Parameter('String', 'message', null)

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to create method parameter when modifier is #modifier"() {
        when:
        new Parameter('String', 'message', modifier)

        then:
        thrown InvalidValueException

        where:
        modifier << [STATIC, STATIC_FINAL, ABSTRACT, DEFAULT]
    }

    def "Should fail to add empty annotation to method parameter"() {
        given:
        def parameter = new Parameter('String', 'message', NONE)

        when:
        parameter.addAnnotation(null)

        then:
        thrown EmptyValueException
    }
}