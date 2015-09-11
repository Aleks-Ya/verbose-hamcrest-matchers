package ru.yaal.hamcrest.verbose;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.assertThat;
import static ru.yaal.hamcrest.verbose.VerboseEqualsMatcher.verboseEqualTo;

/**
 * @author yablokov a.
 */
public class MaxDepthTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void collectionField() {
        CollectionContainer actual = new CollectionContainer();
        actual.value = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4));

        CollectionContainer expected = new CollectionContainer();
        expected.value = Arrays.asList(Arrays.asList(5, 2), Arrays.asList(6, 4));

        thrown.expect(AssertionError.class);
        thrown.expectMessage("1) ru.yaal.hamcrest.verbose.CollectionContainer#value :  different objects");
        assertThat(actual, verboseEqualTo(expected, 1));
    }
}
