package ru.yaal.hamcrest.verbose;

import java.util.Arrays;

/**
 * @author yablokov a.
 */
class Helper {
    static boolean isEquals(Object expected, Object actual) {
        if (actual.getClass().isArray()) {
            return Arrays.deepEquals((Object[]) actual, (Object[]) expected);
        } else {
            return actual.equals(expected);
        }
    }

    static String asString(Object object) {
        if (object.getClass().isArray()) {
            return Arrays.deepToString((Object[]) object);
        } else {
            return object.toString();
        }
    }
}
