package ru.yaal.hamcrest.verbose;

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
        thrown.expectMessage("null");
        assertThat(1, verboseEqualsTo(null));
    }

    @Test
    public void actualIsNull() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("1");
        assertThat(null, verboseEqualsTo(1));
    }

    @Test
    public void primitive() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("2");
        assertThat(1, verboseEqualsTo(2));
    }

    @Test
    public void cyclicReference() {
        CyclicReference expected = new CyclicReference();
        expected.a = 1;
        expected.ref = expected;

        CyclicReference actual = new CyclicReference();
        actual.a = 2;
        actual.ref = actual;
        thrown.expect(AssertionError.class);
        thrown.expectMessage("ru.yaal.hamcrest.verbose.CyclicReference#a = 1");
        assertThat(actual, verboseEqualsTo(expected));
    }

    @Test
    public void differentClasses() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Different classes: actual=java.lang.Integer, expected=java.lang.Long");
        assertThat(1, verboseEqualsTo((Object) 1L));
    }
}

class CyclicReference {
    int a;
    CyclicReference ref;
}
