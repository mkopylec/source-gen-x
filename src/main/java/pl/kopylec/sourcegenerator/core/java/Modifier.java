package pl.kopylec.sourcegenerator.core.java;

public enum Modifier {

    NONE(""),
    STATIC("static"),
    ABSTRACT("abstract"),
    FINAL("final"),
    DEFAULT("default"),
    STATIC_FINAL("static final");

    private final String keyword;

    Modifier(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return keyword;
    }
}
