package pl.kopylec.sourcegenerator.core.java;

import static org.apache.commons.lang3.StringUtils.join;
import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PROTECTED;
import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PUBLIC;
import static pl.kopylec.sourcegenerator.core.java.Modifier.ABSTRACT;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfNotOneOf;

public class AbstractMethod extends Method {

    public AbstractMethod(String returnType, String name) {
        this(PROTECTED, returnType, name);
    }

    public AbstractMethod(AccessModifier accessModifier, String returnType, String name) {
        super(accessModifier, ABSTRACT, returnType, name);
        failIfNotOneOf(accessModifier, "Invalid abstract method access modifier: " + accessModifier, PACKAGE_PRIVATE, PROTECTED, PUBLIC);
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
