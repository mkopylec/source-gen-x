package pl.allegro.tech.sourcegenx.core.config

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import spock.lang.Specification

class ConfigYamlSpec extends Specification {

    def "Should create config YAML"() {
        given:
        def messageConfig = new YamlNode('message.text')
                .addChild(new YamlNode('error')
                .addChild(new YamlNode('fatal').addValue(''))
                .addChild(new YamlNode('available').addValue('invalid value').addValue('illegal operation').addValue('empty value')))
                .addChild(new YamlNode('default').addValue('I am message'))

        def serverConfig = new YamlNode('server')
                .addChild(new YamlNode('port').addValue('8080'))
                .addChild(new YamlNode('host').addValue('localhost'))

        def yaml = new ConfigYaml()
                .addNode(messageConfig)
                .addNode(serverConfig)

        when:
        def string = yaml.toString().replaceAll('\r', '')

        then:
        string == getClass().getResource('/application.yaml').text

        when:
        yaml.createSourceFile('spec', 'application')

        then:
        new File('spec/application.yaml').text.replaceAll('\r', '') == getClass().getResource('/application.yaml').text
    }

    def "Should fail to add empty config YAML node"() {
        given:
        def yaml = new ConfigYaml()

        when:
        yaml.addNode(null)

        then:
        thrown EmptyValueException
    }
}
