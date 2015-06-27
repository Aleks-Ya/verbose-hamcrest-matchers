package ru.yaal.hamcrest.verbose;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * @author yablokov a.
 */
class NotEqualFields {
    private final StringBuilder description = new StringBuilder();

    NotEqualFields(Object actual, Object expected) {
        try {
            verboseEquals(actual, expected, description);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private void verboseEquals(Object actual, Object expected, StringBuilder description) throws IllegalAccessException {
        if (actual == null && expected == null) {
            return;
        }
        if (actual == null) {
            description.append("Actual object is null");
            return;
        }
        if (expected == null) {
            description.append("Expected object is null");
            return;
        }
        if (actual.getClass() != expected.getClass()) {
            description.append(format("Different classes: actual=%s, expected=%s",
                    actual.getClass().getName(), expected.getClass().getName()));
            return;
        }
        // todo cyclic references check
        // todo primitive types check
        if (!actual.equals(expected)) {
            for (Field field : getAllFields(actual)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Object actualValue = field.get(actual);
                Object expectedValue = field.get(expected);
                verboseEquals(actualValue, expectedValue, description);
            }
        }
    }

    private List<Field> getAllFields(Object object) {
        List<Field> result = new ArrayList<>();
        getAllFields(object.getClass(), result);
        return result;
    }

    private void getAllFields(Class clazz, List<Field> result) {
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != Object.class) {
            getAllFields(superclass, result);
        }
    }

    String getDescription() {
        return description.toString();
    }
}
