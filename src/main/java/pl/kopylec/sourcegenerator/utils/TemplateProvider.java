package pl.kopylec.sourcegenerator.utils;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.StringRenderer;
import pl.kopylec.sourcegenerator.core.SourceFileType;

public final class TemplateProvider {

    private static final STGroupDir SOURCE_FILES_TEMPLATES = new STGroupDir("templates", '$', '$');

    static {
        SOURCE_FILES_TEMPLATES.registerRenderer(String.class, new StringRenderer());
    }

    public static ST getTemplate(SourceFileType fileType) {
        return SOURCE_FILES_TEMPLATES.getInstanceOf(fileType.getTemplateName());
    }

    private TemplateProvider() {
    }
}
