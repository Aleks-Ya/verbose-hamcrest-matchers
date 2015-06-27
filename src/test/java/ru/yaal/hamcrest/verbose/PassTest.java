package ru.yaal.hamcrest.verbose;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;
import static ru.yaal.hamcrest.verbose.VerboseEqualsMatcher.verboseEqualsTo;

/**
 * @author yablokov a.
 *         todo check collections
 *         todo check arrays
 */
public class PassTest {

    @Test
    public void bothNull() {
        assertThat(null, verboseEqualsTo(null));
    }

    @Test
    public void primitives() {
        assertThat(1, verboseEqualsTo(1));
        assertThat(1L, verboseEqualsTo(1L));
        assertThat(1.1D, verboseEqualsTo(1.1D));
        assertThat(1.1F, verboseEqualsTo(1.1F));
        assertThat("str", verboseEqualsTo("str"));
        assertThat("str", verboseEqualsTo("str"));
        assertThat('a', verboseEqualsTo('a'));
    }

    @Test
    public void collection() {
        List<String> exp = Arrays.asList("a", "b");
        List<String> act = Arrays.asList("a", "b");
        assertThat(act, verboseEqualsTo(exp));
    }

    @Test
    public void array() {
        String[] exp = {"a", "b"};
        String[] act = {"a", "b"};
        assertThat(act, verboseEqualsTo(exp));
    }
}