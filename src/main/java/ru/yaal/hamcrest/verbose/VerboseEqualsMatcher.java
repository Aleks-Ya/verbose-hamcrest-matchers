package ru.yaal.hamcrest.verbose;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import static java.lang.String.format;

/**
 * If both objects are null then return true.
 * If one of objects is null then return false.
 * todo documentation
 *
 * @author yablokov a.
 */
public class VerboseEqualsMatcher<M> extends BaseMatcher<M> {
    private final Object expected;
    private final int deep;// todo support deep
    private final StringBuilder description = new StringBuilder();

    private VerboseEqualsMatcher(M expected, int deep) {
        this.expected = expected;
        this.deep = deep;
    }

    @Factory
    public static <T> Matcher<T> verboseEqualsTo(T expected) {
        return new VerboseEqualsMatcher<>(expected, Integer.MAX_VALUE);
    }

    @Factory
    public static <T> Matcher<T> verboseEqualsTo(T expected, int maxDeep) {
        if (maxDeep < 0) {
            throw new IllegalArgumentException("maxDeep is less than 0: " + maxDeep);
        }
        return new VerboseEqualsMatcher<>(expected, maxDeep);
    }

    @Override
    public boolean matches(Object actual) {
        if (actual == null && expected == null) {
            return true;
        }
        if (actual == null) {
            description.append(expected.toString());
            return false;
        }
        if (expected == null) {
            description.append("null");
            return false;
        }
        if (actual.getClass() != expected.getClass()) {
            description.append(format("Different types: actual=%s, expected=%s",
                    actual.getClass().getName(), expected.getClass().getName()));
            return false;
        }
        if (actual.equals(expected)) {
            return true;
        } else {
            NotEqualFields notEqualFields = new NotEqualFields<>(actual, expected);
            description.append(notEqualFields.getDescription());
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.description.toString());
    }
}
