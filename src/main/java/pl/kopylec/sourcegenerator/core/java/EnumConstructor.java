package pl.kopylec.sourcegenerator.core.java;

import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PACKAGE_PRIVATE;

public class EnumConstructor extends Constructor {

    public EnumConstructor(String name) {
        super(PACKAGE_PRIVATE, name);
    }
}
