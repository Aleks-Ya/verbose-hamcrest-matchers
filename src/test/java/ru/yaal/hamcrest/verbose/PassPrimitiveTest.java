package ru.yaal.hamcrest.verbose;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static ru.yaal.hamcrest.verbose.VerboseEqualsMatcher.verboseEqualTo;

/**
 * @author yablokov a.
 */
public class PassPrimitiveTest {

    @Test
    public void bothNull() {
        assertThat(null, verboseEqualTo(null));
    }

    @Test
    public void primitives() {
        assertThat(1, verboseEqualTo(1));
        assertThat(1L, verboseEqualTo(1L));
        assertThat(1.1D, verboseEqualTo(1.1D));
        assertThat(1.1F, verboseEqualTo(1.1F));
        assertThat("str", verboseEqualTo("str"));
    }
}