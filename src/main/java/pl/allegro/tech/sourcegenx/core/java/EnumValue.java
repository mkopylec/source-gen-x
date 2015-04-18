package pl.allegro.tech.sourcegenx.core.java;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.join;
import static pl.allegro.tech.sourcegenx.utils.StringHelper.removeRedundantSpaces;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;

public class EnumValue {

    private final String name;
    private final List<String> arguments = new ArrayList<>();

    public EnumValue(String name) {
        failIfBlank(name, "Empty enum value name");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public EnumValue addArgument(String argument) {
        failIfBlank(argument, "Empty enum value argument");
        arguments.add(argument);
        return this;
    }

    @Override
    public String toString() {
        if (arguments.isEmpty()) {
            return name;
        }
        return removeRedundantSpaces(name + "(" + join(arguments, ", ") + ")");
    }
}
