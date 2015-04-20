package pl.allegro.tech.sourcegenx.core.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.repeat;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfEmpty;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNotEmpty;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

@SuppressWarnings("unchecked")
public class YamlNode {

    private final String name;
    private int indent;
    private final List<YamlNode> children = new ArrayList<>();
    private final List<String> values = new ArrayList<>();

    public YamlNode(String name) {
        failIfBlank(name, "Empty YAML node name");
        this.name = name;
        indent = 0;
    }

    public String getName() {
        return name;
    }

    public <N extends YamlNode> N addChild(YamlNode child) {
        failIfNull(child, "Empty YAML node child");
        failIfNotEmpty(values, "Cannot add YAML node child because the node has values");
        child.incrementIndent();
        children.add(child);
        return (N) this;
    }

    public List<YamlNode> getChildren() {
        return children;
    }

    public List<String> getValues() {
        return values;
    }

    public <N extends YamlNode> N addValue(String value) {
        failIfNull(value, "Empty YAML node value");
        failIfNotEmpty(children, "Cannot add YAML node value because the node has children");
        values.add(value);
        return (N) this;
    }

    protected int getIndent() {
        return indent;
    }

    protected void incrementIndent() {
        indent++;
        for (YamlNode child : children) {
            child.incrementIndent();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name).append(":");
        if (children.isEmpty()) {
            failIfEmpty(values, "YAML node does not have any children, nor any values");
            if (values.size() == 1) {
                if (isNotBlank(values.get(0))) {
                    builder.append(" ").append(values.get(0));
                }
            } else {
                for (String value : values) {
                    builder.append("\n").append(repeat("  ", indent + 1)).append("- ").append(value);
                }
            }
        } else {
            for (YamlNode node : children) {
                builder.append("\n").append(repeat("  ", node.getIndent())).append(node);
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        YamlNode root = new YamlNode("root")
                .addChild(new YamlNode("message.default").addValue("Some message"))
                .addChild(new YamlNode("message.special")
                                .addChild(new YamlNode("server").addValue("1"))
                                .addChild(new YamlNode("client").addValue("2"))
                );

        ConfigYaml yaml = new ConfigYaml().addNode(root);

        yaml.createSourceFile("spec", "application");
    }
}
