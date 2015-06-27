package ru.yaal.hamcrest.verbose;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * @author yablokov a.
 */
class NotEqualFields<T> {
    private final StringBuilder description = new StringBuilder();
    private final List<Object> processed = new ArrayList<>();
    private int mismatchIndex = 0;

    NotEqualFields(T actual, T expected) {
        assert actual != null;
        assert expected != null;
        assert actual.getClass() == expected.getClass();
        try {
            verboseEquals(actual.getClass().getName(), actual, expected, description);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private void verboseEquals(String place, Object actual, Object expected, StringBuilder description) throws IllegalAccessException {
        assert place != null;
        if (actual == null && expected == null) {
            return;
        }
        if (actual == null) {
            description.append(expected.toString());
            return;
        }
        if (expected == null) {
            description.append("null");
            return;
        }
        if (actual.getClass() != expected.getClass()) {
            description.append(++mismatchIndex);
            description.append(") ");
            description.append(place);
            description.append(": ");
            description.append(format("Different types: actual=%s, expected=%s\n",
                    actual.getClass().getName(), expected.getClass().getName()));
            return;
        }
        if (!processed.contains(actual)) {
            processed.add(actual);
            if (!actual.equals(expected)) {
                if (isPrimitive(expected.getClass())) {
                    description.append(++mismatchIndex);
                    description.append(") ");
                    description.append(place);
                    description.append(" = ");
                    description.append(expected.toString());
                    description.append("\n");
                } else {
                    for (Field subField : getAllFields(actual)) {
                        if (!subField.isAccessible()) {
                            subField.setAccessible(true);
                        }
                        String placeName = subField.getDeclaringClass().getName() + "#" + subField.getName();
                        verboseEquals(placeName, subField.get(actual), subField.get(expected), description);
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
