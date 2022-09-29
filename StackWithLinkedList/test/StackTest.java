import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Stack<String> s = this.constructorTest();
        Stack<String> sExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testPushEmpty() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest();
        Stack<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        s.push("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testPushNonEmptyOne() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest("red");
        Stack<String> sExpected = this.createFromArgsRef("blue", "red");
        /*
         * Call method under test
         */
        s.push("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testPushNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest("red", "blue");
        Stack<String> sExpected = this.createFromArgsRef("green", "red",
                "blue");
        /*
         * Call method under test
         */
        s.push("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testPopLeavingEmpty() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest("red");
        Stack<String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String x = s.pop();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("red", x);
    }

    @Test
    public final void testPopLeavingNonEmptyOne() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest("red", "blue");
        Stack<String> sExpected = this.createFromArgsRef("blue");
        /*
         * Call method under test
         */
        String x = s.pop();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("red", x);
    }

    @Test
    public final void testPopLeavingNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest("red", "green", "blue");
        Stack<String> sExpected = this.createFromArgsRef("green", "blue");
        /*
         * Call method under test
         */
        String x = s.pop();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("red", x);
    }

    @Test
    public final void testLengthEmpty() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest();
        Stack<String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        int i = s.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(0, i);
    }

    @Test
    public final void testLengthNonEmptyOne() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest("red");
        Stack<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        int i = s.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(1, i);
    }

    @Test
    public final void testLengthNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest("red", "blue");
        Stack<String> sExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        int i = s.length();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(2, i);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testTopOne() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest("red");
        Stack<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        String top = s.top();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("red", top);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testTopMoreThanOne() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest("red", "blue");
        Stack<String> sExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        String top = s.top();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("red", top);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testReplaceTopOne() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest("red");
        Stack<String> sExpected = this.createFromArgsRef("blue");
        /*
         * Call method under test
         */
        String top = s.replaceTop("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("red", top);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testReplaceTopMoreThanOne() {
        /*
         * Set up variables
         */
        Stack<String> s = this.createFromArgsTest("red", "green");
        Stack<String> sExpected = this.createFromArgsRef("blue", "green");
        /*
         * Call method under test
         */
        String top = s.replaceTop("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("red", top);
        assertEquals(sExpected, s);
    }
}
