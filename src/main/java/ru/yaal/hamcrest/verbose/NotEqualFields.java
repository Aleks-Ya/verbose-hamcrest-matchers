package ru.yaal.hamcrest.verbose;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

/**
 * @author yablokov a.
 */
class NotEqualFields<T> {
    private final StringBuilder description = new StringBuilder();
    private final List<Object> processed = new ArrayList<>();
    private final int maxDeep;
    private int mismatchIndex = 0;
    private boolean isEquals = true;

    public NotEqualFields(T actual, T expected, int maxDeep) {
        this.maxDeep = maxDeep;
        try {
            String place = actual != null ? actual.getClass().getName() : "";
            verboseEquals(place, actual, expected, 0);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private void verboseEquals(String place, Object actual, Object expected, int deep) throws IllegalAccessException {
        assert place != null;
        assert deep >= 0;
        if (actual == null && expected == null) {
            return;
        }
        if (actual == null) {
            description.append(expected.toString());
            description.append("\n");
            isEquals = false;
            return;
        }
        if (expected == null) {
            description.append("null");
            description.append("\n");
            isEquals = false;
            return;
        }
        if (actual.getClass() != expected.getClass()) {
            description.append(nextMismatchIndex());
            description.append(place);
            description.append(": ");
            description.append(format("Different types: actual=%s, expected=%s\n",
                    actual.getClass().getName(), expected.getClass().getName()));
            isEquals = false;
            return;
        }
        if (!processed.contains(actual)) {
            processed.add(actual);
            if (!isEquals(expected, actual)) {
                isEquals = false;
                if (isPrimitive(expected.getClass())) {
                    description.append(nextMismatchIndex());
                    description.append(place);
                    description.append(" = ");
                    description.append(expected.toString());
                    description.append("\n");
                } else if (expected.getClass().isArray()) {
                    Object[] expectedItems = (Object[]) expected;
                    Object[] actualItems = (Object[]) actual;
                    if (expectedItems.length != actualItems.length) {
                        description.append(nextMismatchIndex());
                        description.append(place);
                        description.append(" : ");
                        description.append(format(" different array lengths: actual=%d, expected=%d\n",
                                actualItems.length, expectedItems.length));
                    } else {
                        processArray(place, expectedItems, actualItems, deep);
                    }
                } else if (expected instanceof Collection) {
                    Collection actCollection = (Collection) actual;
                    Collection expCollection = (Collection) expected;
                    if (actCollection.size() != expCollection.size()) {
                        description.append(nextMismatchIndex());
                        description.append(place);
                        description.append(" : ");
                        description.append(format(" different collection sizes: actual=%d, expected=%d\n",
                                actCollection.size(), expCollection.size()));
                    } else {
                        processArray(place, expCollection.toArray(), actCollection.toArray(), deep);
                    }
                } else {
                    if (deep < maxDeep) {
                        for (Field subField : getAllFields(actual)) {
                            if (!subField.isAccessible()) {
                                subField.setAccessible(true);
                            }
                            String placeName = subField.getDeclaringClass().getName() + "#" + subField.getName();
                            verboseEquals(placeName, subField.get(actual), subField.get(expected), ++deep);
                        }
                    } else {
                        reportDifferentObjects(place);
                    }
                }
            }
        }
    }

    private void processArray(String place, Object[] expectedItems, Object[] actualItems, int deep) throws IllegalAccessException {
        if (deep < maxDeep) {
            for (int i = 0; i < expectedItems.length; i++) {
                String placeInArray = place + "[" + i + "]";
                verboseEquals(placeInArray, actualItems[i], expectedItems[i], ++deep);
            }
        } else {
            reportDifferentObjects(place);
        }
    }

    private void reportDifferentObjects(String place) {
        description.append(nextMismatchIndex());
        description.append(place);
        description.append(" : ");
        description.append(" different objects\n");
    }


    private String nextMismatchIndex() {
        return ++mismatchIndex + ") ";
    }

    private static List<Field> getAllFields(Object object) {
        List<Field> result = new ArrayList<>();
        getAllFields(object.getClass(), result);
        return result;
    }

    private static void getAllFields(Class clazz, List<Field> result) {
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != Object.class) {
            getAllFields(superclass, result);
        }
    }

    public String getDescription() {
        return description.toString();
    }

    private static boolean isPrimitive(Class clazz) {
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

    public boolean isEquals() {
        return isEquals;
    }

    private static boolean isEquals(Object expected, Object actual) {
        if (actual.getClass().isArray()) {
            return Arrays.deepEquals((Object[]) actual, (Object[]) expected);
        } else {
            return actual.equals(expected);
        }
    }
}
