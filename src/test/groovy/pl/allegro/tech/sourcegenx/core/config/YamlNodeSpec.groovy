package pl.allegro.tech.sourcegenx.core.config

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException
import pl.allegro.tech.sourcegenx.exceptions.IllegalOperationException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.SpecCommonUtils.emptyValues

class YamlNodeSpec extends Specification {

    def "Should create single YAML node"() {
        given:
        def node = new YamlNode('message.text').addValue('I am message')

        when:
        def string = node.toString()

        then:
        string == 'message.text: I am message'
    }

    def "Should create YAML node with children, empty and multiple values"() {
        given:
        def node = new YamlNode('message.text')
                .addChild(new YamlNode('error')
                .addChild(new YamlNode('fatal').addValue(''))
                .addChild(new YamlNode('available').addValue('invalid value').addValue('illegal operation').addValue('empty value')))
                .addChild(new YamlNode('default').addValue('I am message'))

        when:
        def string = node.toString()

        then:
        string ==
                'message.text:\n' +
                '  error:\n' +
                '    fatal:\n' +
                '    available:\n' +
                '      - invalid value\n' +
                '      - illegal operation\n' +
                '      - empty value\n' +
                '  default: I am message'
    }

    @Unroll
    def "Should fail to create YAML node when name is '#name'"() {
        when:
        new YamlNode(name)

        then:
        thrown EmptyValueException

        where:
        name << emptyValues()
    }

    def "Should fail to add empty YAML node child"() {
        given:
        def node = new YamlNode('message.text')

        when:
        node.addChild(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add YAML node child when node has values"() {
        given:
        def node = new YamlNode('message.text')
                .addValue('I am message')

        when:
        node.addChild(new YamlNode('error'))

        then:
        thrown IllegalOperationException
    }

    def "Should fail to add null YAML node value"() {
        given:
        def node = new YamlNode('message.text')

        when:
        node.addValue(null)

        then:
        thrown EmptyValueException
    }

    def "Should fail to add YAML node value when node has children"() {
        given:
        def node = new YamlNode('message.text')
                .addChild(new YamlNode('error'))

        when:
        node.addValue('I am message')

        then:
        thrown IllegalOperationException
    }

    def "Should fail to render YAML node when node has no children and no values"() {
        given:
        def node = new YamlNode('message.text')

        when:
        node.toString()

        then:
        thrown IllegalOperationException
    }
}
