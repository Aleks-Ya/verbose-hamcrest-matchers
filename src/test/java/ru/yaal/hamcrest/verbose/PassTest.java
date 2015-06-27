package ru.yaal.hamcrest.verbose;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * @author yablokov a.
 *         todo check collections
 *         todo check arrays
 */
public class PassTest {

    @Test
    public void bothNull() {
        assertThat(null, VerboseEqualsMatcher.verboseEqualTo(null));
    }

    @Test
    public void primitives() {
        assertThat(1, VerboseEqualsMatcher.verboseEqualTo(1));
        assertThat(1L, VerboseEqualsMatcher.verboseEqualTo(1L));
        assertThat(1.1D, VerboseEqualsMatcher.verboseEqualTo(1.1D));
        assertThat(1.1F, VerboseEqualsMatcher.verboseEqualTo(1.1F));
        assertThat("str", VerboseEqualsMatcher.verboseEqualTo("str"));
        assertThat("str", VerboseEqualsMatcher.verboseEqualTo("str"));
        assertThat('a', VerboseEqualsMatcher.verboseEqualTo('a'));
    }

    @Test
    public void collection() {
        List<String> exp = Arrays.asList("a", "b");
        List<String> act = Arrays.asList("a", "b");
        assertThat(act, VerboseEqualsMatcher.verboseEqualTo(exp));
    }

    @Test
    public void array() {
        String[] exp = {"a", "b"};
        String[] act = {"a", "b"};
        assertThat(act, VerboseEqualsMatcher.verboseEqualTo(exp));
    }
}