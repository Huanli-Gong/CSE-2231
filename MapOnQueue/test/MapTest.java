import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Huanli Gong, Ziyang Lin
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /*
     * Test cases for constructors
     */

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(m, mExpected);
    }

    /*
     * Test cases for kernel methods
     */

    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef("1", "one");
        /*
         * Call method under test
         */
        m.add("1", "one");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddNonEmptyOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one", "2",
                "two");
        /*
         * Call method under test
         */
        m.add("2", "two");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddMoreThanOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one", "2", "two");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one", "2",
                "two", "3", "three");
        /*
         * Call method under test
         */
        m.add("3", "three");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveLeavingEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one");
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        Map.Pair<String, String> p = m.remove("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(p.key(), "1");
        assertEquals(p.value(), "one");
    }

    @Test
    public final void testRemoveLeavingNonEmptyOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one", "2", "two");
        Map<String, String> mExpected = this.createFromArgsRef("2", "two");
        /*
         * Call method under test
         */
        Map.Pair<String, String> p = m.remove("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(p.key(), "1");
        assertEquals(p.value(), "one");
    }

    @Test
    public final void testRemoveLeavingMoreThanOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one", "2", "two",
                "3", "three");
        Map<String, String> mExpected = this.createFromArgsRef("2", "two", "3",
                "three");
        /*
         * Call method under test
         */
        Map.Pair<String, String> p = m.remove("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(p.key(), "1");
        assertEquals(p.value(), "one");
    }

    @Test
    public final void testRemoveAnyLeavingEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one");
        /*
         * Call method under test
         */
        Map.Pair<String, String> p = m.removeAny();
        assertTrue(mExpected.hasKey(p.key()));
        Map.Pair<String, String> pExpected = mExpected.remove(p.key());
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(pExpected, p);
    }

    @Test
    public final void testRemoveAnyLeavingNonEmptyOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one", "2", "two");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one", "2",
                "two");
        /*
         * Call method under test
         */
        Map.Pair<String, String> p = m.removeAny();
        assertTrue(mExpected.hasKey(p.key()));
        Map.Pair<String, String> pExpected = mExpected.remove(p.key());
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(pExpected, p);
    }

    @Test
    public final void testRemoveAnyLeavingMoreThanOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one", "2", "two",
                "3", "three");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one", "2",
                "two", "3", "three");
        /*
         * Call method under test
         */
        Map.Pair<String, String> p = m.removeAny();
        assertTrue(mExpected.hasKey(p.key()));
        Map.Pair<String, String> pExpected = mExpected.remove(p.key());
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(pExpected, p);
    }

    @Test
    public final void testValueNonEmptyOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one");
        /*
         * Call method under test
         */
        String s = m.value("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals("one", s);
    }

    @Test
    public final void testValueNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one", "2", "two",
                "3", "three");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one", "2",
                "two", "3", "three");
        /*
         * Call method under test
         */
        String s = m.value("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals("one", s);
    }

    @Test
    public final void testHasKeyEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        boolean hasKey = m.hasKey("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(false, hasKey);
    }

    @Test
    public final void testHasKeyNonEmptyOneTrue() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one");
        /*
         * Call method under test
         */
        boolean hasKey = m.hasKey("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(true, hasKey);
    }

    @Test
    public final void testHasKeyNonEmptyOneFalse() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        boolean hasKey = m.hasKey("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(false, hasKey);
    }

    @Test
    public final void testHasKeyNonEmptyMoreThanOneTrue() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one", "2", "two",
                "3", "three");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one", "2",
                "two", "3", "three");
        /*
         * Call method under test
         */
        boolean hasKey = m.hasKey("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(true, hasKey);
    }

    @Test
    public final void testHasKeyNonEmptyMoreThanOneFalse() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        boolean hasKey = m.hasKey("4");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(false, hasKey);
    }

    @Test
    public final void testSizeEmpty() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Call method under test
         */
        int i = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(0, i);
    }

    @Test
    public final void testSizeNonEmptyOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one");
        /*
         * Call method under test
         */
        int i = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(1, i);
    }

    @Test
    public final void testSizeNonEmptyMoreThanOne() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one", "2", "two");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one", "2",
                "two");
        /*
         * Call method under test
         */
        int i = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(2, i);
    }

    @Test
    public final void testSizeNonEmptyMoreThantwo() {
        /*
         * Set up variables
         */
        Map<String, String> m = this.createFromArgsTest("1", "one", "2", "two",
                "3", "three");
        Map<String, String> mExpected = this.createFromArgsRef("1", "one", "2",
                "two", "3", "three");
        /*
         * Call method under test
         */
        int i = m.size();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(mExpected, m);
        assertEquals(3, i);
    }
}