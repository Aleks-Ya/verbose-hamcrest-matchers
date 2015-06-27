package ru.yaal.hamcrest.verbose;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

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
        thrown.expectMessage("Different types: actual=java.lang.Integer, expected=java.lang.Long");
        assertThat(1, verboseEqualsTo((Object) 1L));
    }

    @Test
    public void collection() {
        List<String> exp = Arrays.asList("a", "b");
        List<String> act = Arrays.asList("c", "d");
        thrown.expect(AssertionError.class);
        thrown.expectMessage("1) java.util.Arrays$ArrayList#a[0] = a");
        thrown.expectMessage("2) java.util.Arrays$ArrayList#a[1] = b");
        thrown.expectMessage("in: [a, b]");
        assertThat(act, verboseEqualsTo(exp));
    }

    @Test
    public void array() {
        String[] exp = {"a", "b"};
        String[] act = {"c", "d"};
        thrown.expect(AssertionError.class);
        thrown.expectMessage("1) [Ljava.lang.String;[0] = a");
        thrown.expectMessage("2) [Ljava.lang.String;[1] = b");
        thrown.expectMessage("in: [a, b]");
        assertThat(act, verboseEqualsTo(exp));
    }

    @Test
    public void differentArraySize() {
        String[] exp = {"a", "b"};
        String[] act = {"a", "b", "c"};
        thrown.expect(AssertionError.class);
        thrown.expectMessage("1) [Ljava.lang.String; different arrays size: actual=3, expected=2");
        thrown.expectMessage("in: [a, b]");
        assertThat(act, verboseEqualsTo(exp));
    }
}

class CyclicReference {
    int a;
    CyclicReference ref;
}
