package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import pl.allegro.tech.sourcegenx.exceptions.IllegalOperationException
import pl.allegro.tech.sourcegenx.exceptions.InvalidValueException
import spock.lang.Specification

import static pl.allegro.tech.sourcegenx.CommonUtils.emptyValues

class AnnotationElementSpec extends Specification {

    def "Should create annotation element"() {
        given:
        def element = new AnnotationElement('String', 'message')

        when:
        def string = element.toString()

        then:
        string == 'String message();'
    }

    def "Should create annotation element with default value"() {
        given:
        def element = new AnnotationElement('String', 'message')
                .setDefaultValue('"I am message"')

        when:
        def string = element.toString()

        then:
        string == 'String message() default "I am message";'
    }

    def "Should fail to create annotation element when type is void"() {
        when:
        new AnnotationElement('void', 'message')

        then:
        thrown InvalidValueException
    }

    def "Should fail to add parameter to annotation element"() {
        given:
        def element = new AnnotationElement('String', 'message')

        when:
        element.addParameter(new Parameter('int', 'number'))

        then:
        thrown IllegalOperationException
    }

    def "Should fail to set annotation element body"() {
        given:
        def element = new AnnotationElement('String', 'message')

        when:
        element.setBody('\treturn "I am message";')

        then:
        thrown IllegalOperationException
    }

    def "Should fail to set '#defaultValue' annotation element default value"() {
        given:
        def element = new AnnotationElement('String', 'message')

        when:
        element.setDefaultValue(defaultValue)

        then:
        thrown EmptyValueException

        where:
        defaultValue << emptyValues()
    }
}
