package pl.allegro.tech.sourcegenx.utils;

public final class StringHelper {

    public static String removeRedundantSpaces(String value) {
        return value == null ? null : value.trim().replaceAll(" +", " ");
    }

    private StringHelper() {
    }
}
