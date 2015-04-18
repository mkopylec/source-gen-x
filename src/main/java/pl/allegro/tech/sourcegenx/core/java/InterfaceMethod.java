package pl.allegro.tech.sourcegenx.core.java;

import static org.apache.commons.lang3.StringUtils.join;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.DEFAULT;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC;
import static pl.allegro.tech.sourcegenx.utils.StringHelper.removeRedundantSpaces;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNotOneOf;

public class InterfaceMethod extends Method {

    public InterfaceMethod(String returnType, String name) {
        this(NONE, returnType, name);
    }

    public InterfaceMethod(Modifier modifier, String returnType, String name) {
        super(PACKAGE_PRIVATE, modifier, returnType, name);
        failIfNotOneOf(modifier, "Invalid interface method modifier: " + modifier, NONE, DEFAULT, STATIC);
    }

    @Override
    public InterfaceMethod addParameter(Parameter parameter) {
        return (InterfaceMethod) super.addParameter(parameter);
    }

    @Override
    public InterfaceMethod setBody(String body) {
        if (getModifier() == NONE) {
            throw new IllegalStateException("Interface method with no modifier cannot have body");
        }
        return (InterfaceMethod) super.setBody(body);
    }

    @Override
    public String toString() {
        if (getBody() != null || getModifier() != NONE) {
            return super.toString();
        }
        return removeRedundantSpaces(getModifier() + " " + getReturnType() + " " + getName() + "(" + join(getParameters(), ", ") + ");");
    }
}
