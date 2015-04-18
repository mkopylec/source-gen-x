package pl.allegro.tech.sourcegenx.core.java;

import pl.allegro.tech.sourcegenx.exceptions.IllegalOperationException;

import static org.apache.commons.lang3.StringUtils.join;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PROTECTED;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.ABSTRACT;
import static pl.allegro.tech.sourcegenx.utils.StringHelper.removeRedundantSpaces;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNotOneOf;

public class AbstractMethod extends Method {

    public AbstractMethod(String returnType, String name) {
        this(PROTECTED, returnType, name);
    }

    public AbstractMethod(AccessModifier accessModifier, String returnType, String name) {
        super(accessModifier, ABSTRACT, returnType, name);
        failIfNotOneOf(accessModifier, "Invalid abstract method access modifier: " + accessModifier, PACKAGE_PRIVATE, PROTECTED, PUBLIC);
    }

    @Override
    public <M extends Method> M setBody(String body) {
        throw new IllegalOperationException("Abstract method cannot have body");
    }

    @Override
    public String toString() {
        return removeRedundantSpaces(getAccessModifier() + " " + getModifier() + " " + getReturnType() + " " + getName() + "(" + join(getParameters(), ", ") + ");");
    }
}
