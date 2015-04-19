package pl.allegro.tech.sourcegenx.core.config;

import org.stringtemplate.v4.ST;
import pl.allegro.tech.sourcegenx.core.SourceFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.allegro.tech.sourcegenx.core.SourceFileType.CONFIG_PROPERTIES;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

@SuppressWarnings("unchecked")
public class ConfigProperties extends SourceFile {

    private final List<Property> properties = new ArrayList<>();

    public ConfigProperties() {
        super(CONFIG_PROPERTIES);
    }

    public List<Property> getProperties() {
        return properties;
    }

    public <C extends ConfigProperties> C addProperty(Property property) {
        failIfNull(property, "Empty config properties property");
        properties.add(property);
        return (C) this;
    }

    @Override
    protected void fillTemplate(ST template) {
        template
                .add("properties", properties);
    }

    public static void main(String[] args) throws IOException {
        ConfigProperties properties = new ConfigProperties()
                .addProperty(new Property("database.name", "test"))
                .addProperty(new Property("database.host", "localhost"));

        properties.createSourceFile("spec", "application");
    }
}
