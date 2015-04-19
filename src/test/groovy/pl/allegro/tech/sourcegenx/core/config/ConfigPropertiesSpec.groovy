package pl.allegro.tech.sourcegenx.core.config

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import spock.lang.Specification

class ConfigPropertiesSpec extends Specification {

    def "Should create config properties"() {
        given:
        def properties = new ConfigProperties()
                .addProperty(new Property('message.length', '64'))
                .addProperty(new Property('message.default.content'))
                .addProperty(new Property('message.default.type', 'text'))

        when:
        def string = properties.toString().replaceAll('\r', '')

        then:
        string == getClass().getResource('/application.properties').text

        when:
        properties.createSourceFile('spec', 'application')

        then:
        new File('spec/application.properties').text.replaceAll('\r', '') == getClass().getResource('/application.properties').text
    }

    def "Should fail to add empty config properties property"() {
        given:
        def properties = new ConfigProperties()

        when:
        properties.addProperty(null)

        then:
        thrown EmptyValueException
    }
}
