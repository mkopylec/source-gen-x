package pl.kopylec.sourcegenerator.core.java;

import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PUBLIC;
import static pl.kopylec.sourcegenerator.core.java.Modifier.NONE;

public class Constructor extends Method {

    public Constructor(String name) {
        this(PUBLIC, name);
    }

    public Constructor(AccessModifier accessModifier, String name) {
        super(accessModifier, NONE, "", name);
    }
}
