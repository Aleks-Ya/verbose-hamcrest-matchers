package ru.yaal.hamcrest.verbose;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * If both objects are null then return true.
 * If one of objects is null then return false.
 *
 * @author yablokov a.
 */
public class VerboseEqualsMatcher extends BaseMatcher<Object> {
    private final Object expected;
    private final int deep;
    private final StringBuilder description = new StringBuilder();

    private VerboseEqualsMatcher(Object expected, int deep) {
        this.expected = expected;
        this.deep = deep;
    }

    @Factory
    public static Matcher<Object> verboseEqualsTo(Object other) {
        return new VerboseEqualsMatcher(other, Integer.MAX_VALUE);
    }

    @Factory
    public static Matcher<Object> verboseEqualsTo(Object other, int maxDeep) {
        if (maxDeep < 0) {
            throw new IllegalArgumentException("maxDeep is less than 0: " + maxDeep);
        }
        return new VerboseEqualsMatcher(other, maxDeep);
    }

    @Override
    public boolean matches(Object actual) {
        if (actual == null && expected == null) {
            return true;
        }
        if (actual == null) {
            description.append("Actual object is null");
            return false;
        }
        if (expected == null) {
            description.append("Expected object is null");
            return false;
        }
        if (actual.equals(expected)) {
            return true;
        } else {
            NotEqualFields notEqualFields = new NotEqualFields(actual, expected);
            description.append(notEqualFields.getDescription());
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.description.toString());
    }
}
