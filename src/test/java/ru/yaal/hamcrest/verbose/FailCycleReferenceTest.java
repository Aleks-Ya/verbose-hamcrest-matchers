package ru.yaal.hamcrest.verbose;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertThat;

/**
 * @author yablokov a.
 */
public class FailCycleReferenceTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void cyclicReference() {
        CyclicReference expected = new CyclicReference();
        expected.a = 1;
        expected.ref = expected;

        CyclicReference actual = new CyclicReference();
        actual.a = 2;
        actual.ref = actual;
        thrown.expect(AssertionError.class);
        thrown.expectMessage("1) ru.yaal.hamcrest.verbose.FailCycleReferenceTest$CyclicReference#a = 1");
        assertThat(actual, VerboseEqualsMatcher.verboseEqualTo(expected));
    }

    class CyclicReference {
        int a;
        CyclicReference ref;
    }
}
