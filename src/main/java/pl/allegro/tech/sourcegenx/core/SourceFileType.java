package pl.allegro.tech.sourcegenx.core;

public enum SourceFileType {

    JAVA_CLASS("java-class", ".java"),
    JAVA_INTERFACE("java-interface", ".java"),
    JAVA_ENUM("java-enum", ".java"),
    JAVA_ANNOTATION("java-annotation", ".java"),
    PROPERTIES("properties", ".properties");

    private final String templateName;
    private final String fileExtension;

    SourceFileType(String templateName, String fileExtension) {
        this.templateName = templateName;
        this.fileExtension = fileExtension;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
