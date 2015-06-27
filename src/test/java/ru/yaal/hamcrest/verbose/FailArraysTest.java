package ru.yaal.hamcrest.verbose;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertThat;

/**
 * @author yablokov a.
 */
public class FailArraysTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void array() {
        String[] exp = {"a", "b"};
        String[] act = {"c", "d"};
        thrown.expect(AssertionError.class);
        thrown.expectMessage("1) [Ljava.lang.String;[0] = a");
        thrown.expectMessage("2) [Ljava.lang.String;[1] = b");
        thrown.expectMessage("in: [a, b]");
        assertThat(act, VerboseEqualsMatcher.verboseEqualTo(exp));
    }

    @Test
    public void differentArraySize() {
        String[] exp = {"a", "b"};
        String[] act = {"a", "b", "c"};
        thrown.expect(AssertionError.class);
        thrown.expectMessage("1) [Ljava.lang.String; :  different arrays size: actual=3, expected=2");
        thrown.expectMessage("     in: [a, b]");
        assertThat(act, VerboseEqualsMatcher.verboseEqualTo(exp));
    }
}

