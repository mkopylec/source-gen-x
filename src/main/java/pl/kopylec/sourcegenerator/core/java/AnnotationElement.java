package pl.kopylec.sourcegenerator.core.java;

import static org.apache.commons.lang3.StringUtils.join;
import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.kopylec.sourcegenerator.core.java.Modifier.NONE;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfBlank;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfOneOf;

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
    public AnnotationElement addParameter(Parameter parameter) {
        return (AnnotationElement) super.addParameter(parameter);
    }

    @Override
    public AnnotationElement setBody(String body) {
        throw new IllegalStateException("Annotation element cannot have body");
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public AnnotationElement setDefaultValue(String defaultValue) {
        failIfBlank(defaultValue, "Empty annotation element default value");
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public String toString() {
        String value = getReturnType() + " " + getName() + "(" + join(getParameters(), ", ") + ")";
        if (defaultValue != null) {
            value += " default " + defaultValue;
        }
        return value + ";";
    }
}
