package pl.allegro.tech.sourcegenx.core.java;

public enum AccessModifier {

    PUBLIC("public"),
    PROTECTED("protected"),
    PACKAGE_PRIVATE(""),
    PRIVATE("private");

    private final String keyword;

    AccessModifier(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return keyword;
    }
}
