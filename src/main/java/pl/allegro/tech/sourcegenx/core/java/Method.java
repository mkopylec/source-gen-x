package pl.allegro.tech.sourcegenx.core.java;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.join;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE;
import static pl.allegro.tech.sourcegenx.utils.StringHelper.removeRedundantSpaces;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

@SuppressWarnings("unchecked")
public class Method {

    private final List<Annotation> annotations = new ArrayList<>();
    private final AccessModifier accessModifier;
    private final Modifier modifier;
    private final String returnType;
    private final String name;
    private final List<Parameter> parameters = new ArrayList<>();
    private String body;
    private final List<String> thrownExceptions = new ArrayList<>();

    public Method(String returnType, String name) {
        this(PUBLIC, NONE, returnType, name);
    }

    public Method(AccessModifier accessModifier, Modifier modifier, String returnType, String name) {
        failIfNull(accessModifier, "Empty method access modifier");
        failIfNull(modifier, "Empty method modifier");
        failIfNull(returnType, "Empty method return type");
        failIfBlank(name, "Empty method name");
        this.accessModifier = accessModifier;
        this.modifier = modifier;
        this.returnType = returnType;
        this.name = name;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public <M extends Method> M addAnnotation(Annotation annotation) {
        failIfNull(annotation, "Empty method annotation");
        annotations.add(annotation);
        return (M) this;
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

    public <M extends Method> M addParameter(Parameter parameter) {
        failIfNull(parameter, "Empty method parameter");
        parameters.add(parameter);
        return (M) this;
    }

    public String getBody() {
        return body;
    }

    public <M extends Method> M setBody(String body) {
        failIfBlank(body, "Empty method body");
        this.body = body;
        return (M) this;
    }

    public List<String> getThrownExceptions() {
        return thrownExceptions;
    }

    public <M extends Method> M addThrownException(String exception) {
        failIfBlank(exception, "Empty method thrown exception");
        thrownExceptions.add(exception);
        return (M) this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Annotation annotation : annotations) {
            builder.append(annotation).append("\n");
        }
        return removeRedundantSpaces(builder.toString() +
                removeRedundantSpaces(accessModifier + " " + modifier + " " + returnType + " " + name) + "(" + join(parameters, ", ") + ") " + (thrownExceptions.isEmpty() ? "" : ("throws " + join(thrownExceptions, ", ") + " "))) + " {\n"
                + (body == null ? "" : (body + "\n"))
                + "}";
    }
}
