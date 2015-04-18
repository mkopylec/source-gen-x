package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.CommonUtils.emptyValues

class AnnotationSpec extends Specification {

    def "Should create annotation without attributes"() {
        given:
        def annotation = new Annotation('Logged')

        when:
        def string = annotation.toString()

        then:
        string == '@Logged'
    }

    def "Should create annotation with attributes"() {
        given:
        def annotation = new Annotation('Logged')
                .addAttribute('level', 'ERROR')
                .addAttribute('stackTrace', 'true')

        when:
        def string = annotation.toString()

        then:
        string == '@Logged(level = ERROR, stackTrace = true)'
    }

    def "Should create annotation with shorten value attribute"() {
        given:
        def annotation = new Annotation('Logged')
                .addAttribute('value', 'ERROR')

        when:
        def string = annotation.toString()

        then:
        string == '@Logged(ERROR)'
    }

    @Unroll
    def "Should fail to create annotation when name is '#name'"() {
        when:
        new Annotation(name)

        then:
        thrown EmptyValueException

        where:
        name << emptyValues()
    }

    @Unroll
    def "Should fail to add annotation attribute when attribute name is '#name'"() {
        given:
        def annotation = new Annotation('Logged')

        when:
        annotation.addAttribute(name, '"invalid"')

        then:
        thrown EmptyValueException

        where:
        name << emptyValues()
    }

    @Unroll
    def "Should fail to add annotation attribute when attribute value is '#value'"() {
        given:
        def annotation = new Annotation('Logged')

        when:
        annotation.addAttribute('level', value)

        then:
        thrown EmptyValueException

        where:
        value << emptyValues()
    }
}
