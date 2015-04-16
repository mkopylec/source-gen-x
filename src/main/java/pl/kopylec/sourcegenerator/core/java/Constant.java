package pl.kopylec.sourcegenerator.core.java;

import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PUBLIC;
import static pl.kopylec.sourcegenerator.core.java.Modifier.STATIC_FINAL;

public class Constant extends Field {

    public Constant(String type, String name, String value) {
        this(PUBLIC, type, name, value);
    }

    public Constant(AccessModifier accessModifier, String type, String name, String value) {
        super(accessModifier, STATIC_FINAL, type, name);
        setValue(value);
    }
}
