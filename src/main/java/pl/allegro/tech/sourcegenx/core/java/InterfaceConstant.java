package pl.allegro.tech.sourcegenx.core.java;

import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE;

public class InterfaceConstant extends Field {

    public InterfaceConstant(String type, String name, String value) {
        super(PACKAGE_PRIVATE, NONE, type, name);
        setValue(value);
    }
}
