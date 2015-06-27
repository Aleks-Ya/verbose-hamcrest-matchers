package ru.yaal.hamcrest.verbose;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;
import static ru.yaal.hamcrest.verbose.VerboseEqualsMatcher.verboseEqualTo;

/**
 * @author yablokov a.
 */
public class FailCollectionTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void collection() {
        List<String> exp = Arrays.asList("a", "b");
        List<String> act = Arrays.asList("c", "d");
        thrown.expect(AssertionError.class);
        thrown.expectMessage("1) java.util.Arrays$ArrayList#a[0] = a");
        thrown.expectMessage("2) java.util.Arrays$ArrayList#a[1] = b");
        thrown.expectMessage("in: [a, b]");
        assertThat(act, VerboseEqualsMatcher.verboseEqualTo(exp));
    }

    @Test
    public void collectionField() {
        CollectionContainer actual = new CollectionContainer();
        actual.value = Arrays.asList("a", "b");

        CollectionContainer expected = new CollectionContainer();
        expected.value = Arrays.asList("c", "d");

        thrown.expect(AssertionError.class);
        thrown.expectMessage("1) java.util.Arrays$ArrayList#a[0] = c");
        thrown.expectMessage("2) java.util.Arrays$ArrayList#a[1] = d");
        assertThat(actual, verboseEqualTo(expected));
    }
}
