package pl.allegro.tech.sourcegenx.core.java;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.removeEnd;
import static pl.allegro.tech.sourcegenx.utils.StringHelper.removeRedundantSpaces;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;

@SuppressWarnings("unchecked")
public class Annotation {

    private final String name;
    private final Map<String, String> attributes = new HashMap<>();

    public Annotation(String name) {
        failIfBlank(name, "Empty annotation name");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getAttributeValue(String attributeName) {
        return attributes.get(attributeName);
    }

    public <A extends Annotation> A addAttribute(String name, String value) {
        failIfBlank(name, "Empty annotation attribute name");
        failIfBlank(value, "Empty annotation attribute value");
        attributes.put(name, value);
        return (A) this;
    }

    @Override
    public String toString() {
        if (attributes.isEmpty()) {
            return "@" + name;
        }
        StringBuilder builder = new StringBuilder();
        if (attributes.size() == 1 && attributes.containsKey("value")) {
            builder.append(attributes.get("value"));
        } else {
            for (Map.Entry attribute : attributes.entrySet()) {
                builder.append(attribute.getKey()).append(" = ").append(attribute.getValue()).append(", ");
            }
        }
        return removeRedundantSpaces("@" + name + "(" + removeEnd(builder.toString(), ", ") + ")");
    }
}
