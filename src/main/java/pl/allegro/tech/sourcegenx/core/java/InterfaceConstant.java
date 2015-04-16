package pl.allegro.tech.sourcegenx.core.java;

public class InterfaceConstant extends Field {

    public InterfaceConstant(String type, String name, String value) {
        super(AccessModifier.PACKAGE_PRIVATE, Modifier.NONE, type, name);
        setValue(value);
    }
}
