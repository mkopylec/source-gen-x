package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.SpecCommonUtils.emptyValues

class EnumValueSpec extends Specification {

    def "Should create enum value without arguments"() {
        given:
        def value = new EnumValue('TEXT')

        when:
        def string = value.toString()

        then:
        string == 'TEXT'
    }

    def "Should create enum value with arguments"() {
        given:
        def value = new EnumValue('TEXT')
                .addArgument('"text"')
                .addArgument('1')

        when:
        def string = value.toString()

        then:
        string == 'TEXT("text", 1)'
    }

    @Unroll
    def "Should fail to create enum value when name is '#name'"() {
        when:
        new EnumValue(name)

        then:
        thrown EmptyValueException

        where:
        name << emptyValues()
    }

    @Unroll
    def "Should fail to add '#argument' argument to enum value"() {
        given:
        def value = new EnumValue('TEXT')

        when:
        value.addArgument(argument)

        then:
        thrown EmptyValueException

        where:
        argument << emptyValues()
    }
}
