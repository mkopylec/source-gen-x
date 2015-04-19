package pl.allegro.tech.sourcegenx.core.config;

import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

public class Property {

    private final String name;
    private final String value;

    public Property(String name) {
        this(name, "");
    }

    public Property(String name, String value) {
        failIfBlank(name, "Empty property name");
        failIfNull(value, "Empty property value");
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
