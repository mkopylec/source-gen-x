package pl.allegro.tech.sourcegenx.utils;

import pl.allegro.tech.sourcegenx.exceptions.EmptyValueException;
import pl.allegro.tech.sourcegenx.exceptions.IllegalOperationException;
import pl.allegro.tech.sourcegenx.exceptions.InvalidValueException;

import java.util.Collection;

import static org.apache.commons.lang3.ArrayUtils.contains;
import static org.apache.commons.lang3.StringUtils.isBlank;

public final class Validator {

    public static void failIfNull(Object value, String message) {
        if (value == null) {
            throw new EmptyValueException(message);
        }
    }

    public static void failIfBlank(String value, String message) {
        if (isBlank(value)) {
            throw new EmptyValueException(message);
        }
    }

    public static void failIfNotOneOf(Object value, String message, Object... allowedValues) {
        if (!contains(allowedValues, value)) {
            throw new InvalidValueException(message);
        }
    }

    public static void failIfOneOf(Object value, String message, Object... allowedValues) {
        if (contains(allowedValues, value)) {
            throw new InvalidValueException(message);
        }
    }

    public static void failIfNotEmpty(Collection<?> collection, String message) {
        if (!collection.isEmpty()) {
            throw new IllegalOperationException(message);
        }
    }

    public static void failIfEmpty(Collection<?> collection, String message) {
        if (collection.isEmpty()) {
            throw new IllegalOperationException(message);
        }
    }

    public static void failIfInstanceOf(Object value, String message, Class<?>... allowedClasses) {
        if (contains(allowedClasses, value.getClass())) {
            throw new InvalidValueException(message);
        }
    }

    private Validator() {
    }
}
