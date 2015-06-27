package ru.yaal.hamcrest.verbose;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;
import static ru.yaal.hamcrest.verbose.VerboseEqualsMatcher.verboseEqualTo;

/**
 * @author yablokov a.
 */
public class PassCollectionTest {

    @Test
    public void collection() {
        List<String> exp = Arrays.asList("a", "b");
        List<String> act = Arrays.asList("a", "b");
        assertThat(act, verboseEqualTo(exp));
    }

    @Test
    public void collectionField() {
        CollectionContainer actual = new CollectionContainer();
        actual.value = Arrays.asList("a", "b");

        CollectionContainer expected = new CollectionContainer();
        expected.value = Arrays.asList("a", "b");

        assertThat(actual, verboseEqualTo(expected));
    }
}

