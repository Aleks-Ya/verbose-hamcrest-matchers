package ru.yaal.hamcrest.verbose;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertThat;

/**
 * @author yablokov a.
 */
public class FailTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void expectedIsNull() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("null");
        assertThat(1, VerboseEqualsMatcher.verboseEqualTo(null));
    }

    @Test
    public void actualIsNull() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("1");
        assertThat(null, VerboseEqualsMatcher.verboseEqualTo(1));
    }

    @Test
    public void primitive() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("2");
        assertThat(1, VerboseEqualsMatcher.verboseEqualTo(2));
    }

    @Test
    public void differentClasses() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Different types: actual=java.lang.Integer, expected=java.lang.Long");
        assertThat(1, VerboseEqualsMatcher.verboseEqualTo((Object) 1L));
    }
}
