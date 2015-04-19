package pl.allegro.tech.sourcegenx.core.java;

import java.util.ArrayList;
import java.util.List;

import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PRIVATE;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.FINAL;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC_FINAL;
import static pl.allegro.tech.sourcegenx.utils.StringHelper.removeRedundantSpaces;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNotOneOf;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

@SuppressWarnings("unchecked")
public class Field {

    private final List<Annotation> annotations = new ArrayList<>();
    private final AccessModifier accessModifier;
    private final Modifier modifier;
    private final String type;
    private final String name;
    private String value;

    public Field(String type, String name) {
        this(PRIVATE, NONE, type, name);
    }

    public Field(AccessModifier accessModifier, Modifier modifier, String type, String name) {
        failIfNull(accessModifier, "Empty field access modifier");
        failIfNull(modifier, "Empty field modifier");
        failIfNotOneOf(modifier, "Invalid Java class modifier: " + modifier, NONE, FINAL, STATIC, STATIC_FINAL);
        failIfBlank(type, "Empty field type");
        failIfBlank(name, "Empty field name");
        this.accessModifier = accessModifier;
        this.modifier = modifier;
        this.type = type;
        this.name = name;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public <F extends Field> F addAnnotation(Annotation annotation) {
        failIfNull(annotation, "Empty field annotation");
        annotations.add(annotation);
        return (F) this;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public <F extends Field> F setValue(String value) {
        failIfBlank(value, "Empty field value");
        this.value = value;
        return (F) this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Annotation annotation : annotations) {
            builder.append(annotation).append("\n");
        }
        String s = builder.toString() + removeRedundantSpaces(accessModifier + " " + modifier + " " + type + " " + name);
        if (value != null) {
            s += " = " + value;
        }
        return removeRedundantSpaces(s + ";");
    }
}
