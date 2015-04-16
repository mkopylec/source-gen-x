package pl.allegro.tech.sourcegenx.core.java;

public class Constant extends Field {

    public Constant(String type, String name, String value) {
        this(AccessModifier.PUBLIC, type, name, value);
    }

    public Constant(AccessModifier accessModifier, String type, String name, String value) {
        super(accessModifier, Modifier.STATIC_FINAL, type, name);
        setValue(value);
    }
}
