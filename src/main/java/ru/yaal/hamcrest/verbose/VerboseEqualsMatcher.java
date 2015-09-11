package ru.yaal.hamcrest.verbose;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String message;
    private final Object expected;
    private final int maxDepth;
    private final StringBuilder description = new StringBuilder();

    private VerboseEqualsMatcher(M expected, String message, int maxDepth) {
        this.expected = expected;
        this.message = message;
        this.maxDepth = maxDepth;
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
    public static <T> Matcher<T> verboseEqualTo(T expected, int maxDepth) {
        return verboseEqualTo("", expected, maxDepth);
    }

    @Factory
    public static <T> Matcher<T> verboseEqualTo(String message, T expected, int maxDepth) {
        if (maxDepth < 0) {
            throw new IllegalArgumentException("maxDepth is less than 0: " + maxDepth);
        }
        return new VerboseEqualsMatcher<>(expected, message, maxDepth);
    }

    @Override
    public boolean matches(Object actual) {
        NotEqualFields notEqualFields = new NotEqualFields<>(actual, expected, maxDepth);
        if (notEqualFields.isEquals()) {
            return true;
        } else {
            if (message != null && !message.isEmpty()) {
                description.append(message);
                description.append("\n");
            }
            description.append(gson.toJson(actual));
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

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText(gson.toJson(item));
    }
}
