package pl.allegro.tech.sourcegenx.core.java

import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PRIVATE
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PROTECTED
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC

class ConstantSpec extends Specification {

    @Unroll
    def "Should create #accessModifier constant"() {
        given:
        def constant = new Constant(accessModifier, 'String', 'MESSAGE', '"I am message"')

        when:
        def string = constant.toString()

        then:
        string == result

        where:
        accessModifier  | result
        PUBLIC          | 'public static final String MESSAGE = "I am message";'
        PROTECTED       | 'protected static final String MESSAGE = "I am message";'
        PACKAGE_PRIVATE | 'static final String MESSAGE = "I am message";'
        PRIVATE         | 'private static final String MESSAGE = "I am message";'
    }
}
