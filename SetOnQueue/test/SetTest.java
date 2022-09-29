import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size
    /*
     * Test cases for constructors
     */

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /*
     * Test cases for kernel methods
     */

    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        s.add("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddNonEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        s.add("blue");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddMoreThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "blue", "green");
        Set<String> sExpected = this.createFromArgsRef("red", "blue", "yellow",
                "green");
        /*
         * Call method under test
         */
        s.add("yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveLeavingEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        String x = s.remove("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("red", x);
    }

    @Test
    public final void testRemoveLeavingNonEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "blue");
        Set<String> sExpected = this.createFromArgsRef("blue");
        /*
         * Call method under test
         */
        String x = s.remove("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("red", x);
    }

    @Test
    public final void testRemoveLeavingMoreThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "green", "blue");
        Set<String> sExpected = this.createFromArgsRef("red", "blue");
        /*
         * Call method under test
         */
        String x = s.remove("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals("green", x);
    }

    @Test
    public final void testRemoveAnyLeavingEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        String x = s.removeAny();
        assertTrue(sExpected.contains(x));
        String xExpected = sExpected.remove(x);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(xExpected, x);
    }

    @Test
    public final void testRemoveAnyLeavingNonEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "green");
        Set<String> sExpected = this.createFromArgsRef("red", "green");
        /*
         * Call method under test
         */
        String x = s.removeAny();
        assertTrue(sExpected.contains(x));
        String xExpected = sExpected.remove(x);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(xExpected, x);
    }

    @Test
    public final void testRemoveAnyLeavingMoreThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "green", "blue");
        Set<String> sExpected = this.createFromArgsRef("red", "green", "blue");
        /*
         * Call method under test
         */
        String x = s.removeAny();
        assertTrue(sExpected.contains(x));
        String xExpected = sExpected.remove(x);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(xExpected, x);
    }

    @Test
    public final void testContainsEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        boolean contains = s.contains("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(false, contains);
    }

    @Test
    public final void testContainsNonEmptyOneTrue() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        boolean contains = s.contains("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(true, contains);
    }

    @Test
    public final void testContainsNonEmptyOneFalse() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        boolean contains = s.contains("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(false, contains);
    }

    @Test
    public final void testContainsNonEmptyMoreThanOneTrue() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "green", "blue");
        Set<String> sExpected = this.createFromArgsRef("red", "green", "blue");
        /*
         * Call method under test
         */
        boolean contains = s.contains("red");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(true, contains);
    }

    @Test
    public final void testContainsNonEmptyMoreThanOneFalse() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "green", "blue");
        Set<String> sExpected = this.createFromArgsRef("red", "green", "blue");
        /*
         * Call method under test
         */
        boolean contains = s.contains("yellow");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(false, contains);
    }

    @Test
    public final void testSizeEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        int i = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(0, i);
    }

    @Test
    public final void testSizeNonEmptyOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red");
        Set<String> sExpected = this.createFromArgsRef("red");
        /*
         * Call method under test
         */
        int i = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(1, i);
    }

    @Test
    public final void testSizeNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("red", "green", "blue");
        Set<String> sExpected = this.createFromArgsRef("red", "green", "blue");
        /*
         * Call method under test
         */
        int i = s.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(3, i);
    }

}
