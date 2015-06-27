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
    private final List<Object> processed = new ArrayList<>();

    NotEqualFields(Object actual, Object expected) {
        try {
            verboseEquals(null, actual, expected, description);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private void verboseEquals(Field field, Object actual, Object expected, StringBuilder description) throws IllegalAccessException {
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
        if (!processed.contains(actual)) {
            processed.add(actual);
            if (!actual.equals(expected)) {
                if (isPrimitive(expected.getClass())) {
                    description.append(field != null ? field.getDeclaringClass().getName() + "#" + field.getName() : "");
                    description.append(" = ");
                    description.append(expected.toString());
                } else {
                    for (Field subField : getAllFields(actual)) {
                        if (!subField.isAccessible()) {
                            subField.setAccessible(true);
                        }
                        Object actualValue = subField.get(actual);
                        Object expectedValue = subField.get(expected);
                        verboseEquals(subField, actualValue, expectedValue, description);
                    }
                }
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

    private boolean isPrimitive(Class clazz) {
        return clazz.isPrimitive() ||
                clazz == Boolean.class ||
                clazz == Byte.class ||
                clazz == Short.class ||
                clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Float.class ||
                clazz == Double.class ||
                clazz == Character.class ||
                clazz == String.class;
    }
}
