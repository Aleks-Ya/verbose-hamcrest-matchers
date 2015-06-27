package ru.yaal.hamcrest.verbose;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static ru.yaal.hamcrest.verbose.VerboseEqualsMatcher.verboseEqualTo;

/**
 * @author yablokov a.
 */
public class PassArrayTest {
    @Test
    public void array() {
        String[] exp = {"a", "b"};
        String[] act = {"a", "b"};
        assertThat(act, verboseEqualTo(exp));
    }

    @Test
    public void arrayField() {
        ArrayContainer expected = new ArrayContainer();
        expected.value = new String[]{"a", "b"};

        ArrayContainer actual = new ArrayContainer();
        actual.value = new String[]{"a", "b"};

        assertThat(actual, verboseEqualTo(expected));
    }

}

