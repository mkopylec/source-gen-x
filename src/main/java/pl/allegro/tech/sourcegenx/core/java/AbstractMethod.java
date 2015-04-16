package pl.allegro.tech.sourcegenx.core.java;

import pl.allegro.tech.sourcegenx.utils.Validator;

import static org.apache.commons.lang3.StringUtils.join;

public class AbstractMethod extends Method {

    public AbstractMethod(String returnType, String name) {
        this(AccessModifier.PROTECTED, returnType, name);
    }

    public AbstractMethod(AccessModifier accessModifier, String returnType, String name) {
        super(accessModifier, Modifier.ABSTRACT, returnType, name);
        Validator.failIfNotOneOf(accessModifier, "Invalid abstract method access modifier: " + accessModifier, AccessModifier.PACKAGE_PRIVATE, AccessModifier.PROTECTED, AccessModifier.PUBLIC);
    }

    @Override
    public Method setBody(String body) {
        throw new IllegalStateException("Abstract method cannot have body");
    }

    @Override
    public String toString() {
        return getAccessModifier() + " " + getModifier() + " " + getReturnType() + " " + getName() + "(" + join(getParameters(), ", ") + ");";
    }
}
