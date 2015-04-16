package pl.allegro.tech.sourcegenx.core.java;

public class Constructor extends Method {

    public Constructor(String name) {
        this(AccessModifier.PUBLIC, name);
    }

    public Constructor(AccessModifier accessModifier, String name) {
        super(accessModifier, Modifier.NONE, "", name);
    }
}
