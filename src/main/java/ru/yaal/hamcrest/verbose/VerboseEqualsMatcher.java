package ru.yaal.hamcrest.verbose;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import java.util.Arrays;

/**
 * If both objects are null then return true.
 * If one of objects is null then return false.
 * todo documentation
 *
 * @author yablokov a.
 */
public class VerboseEqualsMatcher<M> extends BaseMatcher<M> {
    private final String message;
    private final Object expected;
    private final int deep;// todo support deep
    private final StringBuilder description = new StringBuilder();

    private VerboseEqualsMatcher(M expected, String message, int deep) {
        this.expected = expected;
        this.message = message;
        this.deep = deep;
    }

    @Factory
    public static <T> Matcher<T> verboseEqualTo(T expected) {
        return verboseEqualTo("", expected);
    }

    @Factory
    public static <T> Matcher<T> verboseEqualTo(String message, T expected) {
        return verboseEqualTo(message, expected, Integer.MAX_VALUE);
    }

    @Factory
    public static <T> Matcher<T> verboseEqualTo(T expected, int maxDeep) {
        return verboseEqualTo("", expected, maxDeep);
    }

    @Factory
    public static <T> Matcher<T> verboseEqualTo(String message, T expected, int maxDeep) {
        if (maxDeep < 0) {
            throw new IllegalArgumentException("maxDeep is less than 0: " + maxDeep);
        }
        return new VerboseEqualsMatcher<>(expected, message, maxDeep);
    }

    @Override
    public boolean matches(Object actual) {
        NotEqualFields notEqualFields = new NotEqualFields<>(actual, expected);
        if (notEqualFields.isEquals()) {
            return true;
        } else {
            if (message != null && !message.isEmpty()) {
                description.append(message);
                description.append("\n");
            }
            description.append("\n");
            description.append(notEqualFields.getDescription());
            description.append("     in: ");
            description.append(asString(expected));
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.description.toString());
    }

    private static String asString(Object object) {
        if (object == null) {
            return "null";
        } else if (object.getClass().isArray()) {
            return Arrays.deepToString((Object[]) object);
        } else {
            return object.toString();
        }
    }

}
