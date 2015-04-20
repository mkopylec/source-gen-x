package pl.allegro.tech.sourcegenx.core.config;

import org.stringtemplate.v4.ST;
import pl.allegro.tech.sourcegenx.core.SourceFile;

import java.util.ArrayList;
import java.util.List;

import static pl.allegro.tech.sourcegenx.core.SourceFileType.CONFIG_YAML;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

@SuppressWarnings("unchecked")
public class ConfigYaml extends SourceFile {

    private final List<YamlNode> nodes = new ArrayList<>();

    public ConfigYaml() {
        super(CONFIG_YAML);
    }

    public List<YamlNode> getNodes() {
        return nodes;
    }

    public <C extends ConfigYaml> C addNode(YamlNode node) {
        failIfNull(node, "Empty config YAML node");
        nodes.add(node);
        return (C) this;
    }

    @Override
    protected void fillTemplate(ST template) {
        template
                .add("nodes", nodes);
    }
}
