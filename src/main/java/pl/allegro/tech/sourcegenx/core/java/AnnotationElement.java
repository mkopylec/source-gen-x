package pl.allegro.tech.sourcegenx.core.java;

import pl.allegro.tech.sourcegenx.exceptions.IllegalOperationException;

import static org.apache.commons.lang3.StringUtils.join;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE;
import static pl.allegro.tech.sourcegenx.utils.StringHelper.removeRedundantSpaces;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfOneOf;

@SuppressWarnings("unchecked")
public class AnnotationElement extends Method {

    private String defaultValue;

    public AnnotationElement(String type, String name) {
        super(PACKAGE_PRIVATE, NONE, type, name);
        failIfOneOf(type, "Invalid annotation element type: " + type, "void");
    }

    public String getType() {
        return getReturnType();
    }

    @Override
    public <M extends Method> M addParameter(Parameter parameter) {
        throw new IllegalOperationException("Annotation element cannot have parameters");
    }

    @Override
    public <M extends Method> M setBody(String body) {
        throw new IllegalOperationException("Annotation element cannot have body");
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public <A extends AnnotationElement> A setDefaultValue(String defaultValue) {
        failIfBlank(defaultValue, "Empty annotation element default value");
        this.defaultValue = defaultValue;
        return (A) this;
    }

    @Override
    public String toString() {
        String value = getReturnType() + " " + getName() + "(" + join(getParameters(), ", ") + ")";
        if (defaultValue != null) {
            value += " default " + defaultValue;
        }
        return removeRedundantSpaces(value + ";");
    }
}
