package pl.allegro.tech.sourcegenx.core.java

import spock.lang.Specification

class EnumConstructorSpec extends Specification {

    def "Should create enum constructor"() {
        given:
        def constructor = new EnumConstructor('MessageType')

        when:
        def string = constructor.toString()

        then:
        string == 'MessageType() {\n}'
    }
}
