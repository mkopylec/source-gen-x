package pl.allegro.tech.sourcegenx.core.java;

import pl.allegro.tech.sourcegenx.utils.Validator;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.join;

public class Method {

    private final List<Annotation> annotations = new ArrayList<>();
    private final AccessModifier accessModifier;
    private final Modifier modifier;
    private final String returnType;
    private final String name;
    private final List<Parameter> parameters = new ArrayList<>();
    private String body;

    public Method(String returnType, String name) {
        this(AccessModifier.PUBLIC, Modifier.NONE, returnType, name);
    }

    public Method(AccessModifier accessModifier, Modifier modifier, String returnType, String name) {
        Validator.failIfNull(accessModifier, "Empty method access modifier");
        Validator.failIfNull(modifier, "Empty method modifier");
        Validator.failIfNull(returnType, "Empty method return type");
        Validator.failIfBlank(name, "Empty method name");
        this.accessModifier = accessModifier;
        this.modifier = modifier;
        this.returnType = returnType;
        this.name = name;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public Method addAnnotation(Annotation annotation) {
        Validator.failIfNull(annotation, "Empty method annotation");
        annotations.add(annotation);
        return this;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public Method addParameter(Parameter parameter) {
        Validator.failIfNull(parameter, "Empty method parameter");
        parameters.add(parameter);
        return this;
    }

    public String getBody() {
        return body;
    }

    public Method setBody(String body) {
        Validator.failIfBlank(body, "Empty method body");
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Annotation annotation : annotations) {
            builder.append(annotation).append("\n");
        }
        return builder.toString() +
                accessModifier + " " + modifier + " " + returnType + " " + name + "(" + join(parameters, ", ") + ") {\n"
                + (body == null ? "" : body)
                + "\n}";
    }
}
