package pl.allegro.tech.sourcegenx.core.config

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.SpecCommonUtils.emptyValues

class PropertySpec extends Specification {

    def "Should create property"() {
        given:
        def property = new Property('message.length', '64')

        when:
        def string = property.toString()

        then:
        string == 'message.length=64'
    }

    @Unroll
    def "Should fail to create property when name is '#name'"() {
        when:
        new Property(name)

        then:
        thrown EmptyValueException

        where:
        name << emptyValues()
    }

    def "Should fail to create property when value is null"() {
        when:
        new Property('message.length', null)

        then:
        thrown EmptyValueException
    }
}
