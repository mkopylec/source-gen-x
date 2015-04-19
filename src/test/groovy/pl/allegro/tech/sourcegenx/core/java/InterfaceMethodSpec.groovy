package pl.allegro.tech.sourcegenx.core.java

import pl.allegro.tech.sourcegenx.exceptions.IllegalOperationException
import pl.allegro.tech.sourcegenx.exceptions.InvalidValueException
import spock.lang.Specification
import spock.lang.Unroll

import static pl.allegro.tech.sourcegenx.core.java.Modifier.ABSTRACT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.DEFAULT
import static pl.allegro.tech.sourcegenx.core.java.Modifier.FINAL
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC_FINAL

class InterfaceMethodSpec extends Specification {

    @Unroll
    def "Should create empty #modifier interface method with thrown exceptions"() {
        given:
        InterfaceMethod method = new InterfaceMethod(modifier, 'String', 'getMessage')
                .addThrownException("Exception")
                .addThrownException("RuntimeException")

        when:
        def string = method.toString()

        then:
        string == result

        where:
        modifier | result
        NONE    | 'String getMessage() throws Exception, RuntimeException;'
        DEFAULT | 'default String getMessage() throws Exception, RuntimeException {\n}'
        STATIC  | 'static String getMessage() throws Exception, RuntimeException {\n}'
    }

    @Unroll
    def "Should create non empty, #modifier interface method with parameters and thrown exceptions"() {
        given:
        InterfaceMethod method = new InterfaceMethod(modifier, 'String', 'getMessage')
                .addParameter(new Parameter('int', 'age'))
                .addParameter(new Parameter('boolean', 'valid'))
                .addThrownException("Exception")
                .addThrownException("RuntimeException")
                .setBody('\treturn "I am message";')

        when:
        def string = method.toString()

        then:
        "$string" ==
                "$modifier String getMessage(int age, boolean valid) throws Exception, RuntimeException {\n" +
                "\treturn \"I am message\";\n" +
                "}"

        where:
        modifier << [STATIC, DEFAULT]
    }

    def "Should fail to create interface method when modifier is #modifier"() {
        when:
        new InterfaceMethod(modifier, 'String', 'getMessage')

        then:
        thrown InvalidValueException

        where:
        modifier << [FINAL, STATIC_FINAL, ABSTRACT]
    }

    def "Should fail to set interface method body when there is no modifier"() {
        given:
        def method = new InterfaceMethod('String', 'getMessage')

        when:
        method.setBody('\treturn "I am message";')

        then:
        thrown IllegalOperationException
    }
}
