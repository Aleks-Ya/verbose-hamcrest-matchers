package ru.yaal.hamcrest.verbose;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertThat;
import static ru.yaal.hamcrest.verbose.VerboseEqualsMatcher.verboseEqualsTo;

/**
 * @author yablokov a.
 */
public class FailTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void expectedIsNull() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected object is null");
        assertThat(1, verboseEqualsTo(null));
    }

    @Test
    public void actualIsNull() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Actual object is null");
        assertThat(null, verboseEqualsTo(1));
    }

    @Test
    @Ignore("doesn't pass still")
    public void cyclicReference() {
        assertThat(1, verboseEqualsTo(2));
    }
}