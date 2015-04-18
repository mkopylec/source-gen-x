package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.IllegalOperationException
import pl.allegro.tech.sourcegenx.exceptions.InvalidValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PROTECTED
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC

class AbstractMethodSpec extends Specification {

    @Unroll
    def "Should create #accessModifier abstract method"() {
        given:
        def method = new AbstractMethod(accessModifier, 'String', 'getMessage')

        when:
        def string = method.toString()

        then:
        string == result

        where:
        accessModifier  | result
        PUBLIC          | 'public abstract String getMessage();'
        PROTECTED       | 'protected abstract String getMessage();'
        PACKAGE_PRIVATE | 'abstract String getMessage();'
    }

    def "Should fail to create private abstract method"() {
        when:
        new AbstractMethod(PRIVATE, 'String', 'getMessage')

        then:
        thrown InvalidValueException
    }

    def "Should fail to set abstract method body"() {
        given:
        def method = new AbstractMethod('String', 'getMessage')

        when:
        method.setBody('\treturn "I am message";')

        then:
        thrown IllegalOperationException
    }
}
