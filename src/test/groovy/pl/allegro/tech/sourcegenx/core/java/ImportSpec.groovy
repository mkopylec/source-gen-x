package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import pl.allegro.tech.sourcegenx.exceptions.InvalidValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.SpecCommonUtils.emptyValues
import static pl.allegro.tech.sourcegenx.core.java.Modifier.ABSTRACT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.DEFAULT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.FINAL
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC_FINAL

class ImportSpec extends Specification {

    @Unroll
    def "Should create #modifier import"() {
        given:
        def imp = new Import(modifier, 'pl.allegro.tech.SomeClass')

        when:
        def string = imp.toString()

        then:
        string == result

        where:
        modifier | result
        NONE     | 'import pl.allegro.tech.SomeClass;'
        STATIC   | 'import static pl.allegro.tech.SomeClass;'
    }

    def "Should fail to create import when modifier is empty"() {
        when:
        new Import(null, 'pl.allegro.tech.SomeClass')

        then:
        thrown EmptyValueException
    }

    @Unroll
    def "Should fail to create import when modifier is #modifier"() {
        when:
        new Import(modifier, 'pl.allegro.tech.SomeClass')

        then:
        thrown InvalidValueException

        where:
        modifier << [ABSTRACT, DEFAULT, STATIC_FINAL, FINAL]
    }

    @Unroll
    def "Should fail to create import when content is '#content'"() {
        when:
        new Import(NONE, content)

        then:
        thrown EmptyValueException

        where:
        content << emptyValues()
    }
}
