package pl.allegro.tech.sourcegenx.core.java

import spock.lang.Specification

class InterfaceConstantSpec extends Specification {

    def "Should create interface constant"() {
        given:
        def constant = new InterfaceConstant('String', 'message', '"I am message"')

        when:
        def string = constant.toString()

        then:
        string == 'String message = "I am message";'
    }
}
