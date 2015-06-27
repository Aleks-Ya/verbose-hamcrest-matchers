package ru.yaal.hamcrest.verbose;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static ru.yaal.hamcrest.verbose.VerboseEqualsMatcher.verboseEqualsTo;

/**
 * @author yablokov a.
 */
public class PassTest {

    @Test
    public void bothNull() {
        assertThat(null, verboseEqualsTo(null));
    }

    @Test
    public void pass() {
        assertThat(1, verboseEqualsTo(1));
    }
}