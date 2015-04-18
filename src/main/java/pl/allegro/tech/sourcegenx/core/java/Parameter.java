package pl.allegro.tech.sourcegenx.core.java;

import java.util.ArrayList;
import java.util.List;

import static pl.allegro.tech.sourcegenx.core.java.Modifier.FINAL;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE;
import static pl.allegro.tech.sourcegenx.utils.StringHelper.removeRedundantSpaces;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNotOneOf;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

public class Parameter {

    private final List<Annotation> annotations = new ArrayList<>();
    private final String type;
    private final String name;
    private final Modifier modifier;

    public Parameter(String type, String name) {
        this(type, name, NONE);
    }

    public Parameter(String type, String name, Modifier modifier) {
        failIfBlank(type, "Empty parameter type");
        failIfBlank(name, "Empty parameter name");
        failIfNull(modifier, "Empty parameter modifier");
        failIfNotOneOf(modifier, "Invalid Java class modifier: " + modifier, NONE, FINAL);
        this.type = type;
        this.name = name;
        this.modifier = modifier;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public Parameter addAnnotation(Annotation annotation) {
        failIfNull(annotation, "Empty parameter annotation");
        annotations.add(annotation);
        return this;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Modifier getModifier() {
        return modifier;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Annotation annotation : annotations) {
            builder.append(annotation).append(" ");
        }
        return removeRedundantSpaces(builder.toString() + modifier + " " + type + " " + name);
    }
}
