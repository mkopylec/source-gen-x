package pl.allegro.tech.sourcegenx.core.java;

import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC_FINAL;

public class Constant extends Field {

    public Constant(String type, String name, String value) {
        this(PUBLIC, type, name, value);
    }

    public Constant(AccessModifier accessModifier, String type, String name, String value) {
        super(accessModifier, STATIC_FINAL, type, name);
        setValue(value);
    }
}
