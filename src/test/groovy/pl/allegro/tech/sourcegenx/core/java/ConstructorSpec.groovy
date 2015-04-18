package pl.allegro.tech.sourcegenx.core.java

import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PROTECTED
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC

class ConstructorSpec extends Specification {

    @Unroll
    def "Should create #accessModifier constructor"() {
        given:
        def constructor = new Constructor(accessModifier, 'MessageGenerator')

        when:
        def string = constructor.toString()

        then:
        string == result

        where:
        accessModifier  | result
        PUBLIC          | 'public MessageGenerator() {\n}'
        PROTECTED       | 'protected MessageGenerator() {\n}'
        PACKAGE_PRIVATE | 'MessageGenerator() {\n}'
        PRIVATE         | 'private MessageGenerator() {\n}'
    }
}
