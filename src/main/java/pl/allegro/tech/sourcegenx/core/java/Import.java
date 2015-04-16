package pl.allegro.tech.sourcegenx.core.java;

import static org.apache.commons.lang3.StringUtils.endsWith;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.STATIC;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfDoesNotMatch;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNotOneOf;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

public class Import {

    private static final String CONTENT_PATTERN = "^([a-z]{2,3}(\\.[a-zA-Z][a-zA-Z_$0-9]*)*)\\.([A-Z][a-zA-Z_$0-9]*)[;]?$";

    private final Modifier modifier;
    private String content;

    public Import(String content) {
        this(NONE, content);
    }

    public Import(Modifier modifier, String content) {
        failIfNull(modifier, "Empty import modifier");
        failIfNotOneOf(modifier, "Invalid import modifier: " + modifier, NONE, STATIC);
        failIfBlank(content, "Empty import content");
        failIfDoesNotMatch(content, CONTENT_PATTERN, "Invalid import content: " + content);
        this.modifier = modifier;
        this.content = correctContent(content);
    }

    public Modifier getModifier() {
        return modifier;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "import " + modifier + " " + content;
    }

    private String correctContent(String content) {
        if (!endsWith(content, ";")) {
            content += ";";
        }
        return content;
    }
}
