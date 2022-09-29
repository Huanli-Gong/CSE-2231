import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Huanli Gong, Ziyang Lin
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Test cases for constructor
     */
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    /*
     * Test cases for add
     */
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddNonEmptyOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red");
        m.add("red");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddNonEmptyMoreThanOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red", "blue");
        m.add("blue");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "blue", "yellow", "white");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red", "blue", "yellow", "white", "black");
        m.add("black");
        assertEquals(mExpected, m);
    }
    /*
     * Test cases for changeToExtractionMode
     */

    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeNonEmptyOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeNonEmptyMoreThanOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "blue", "yellow", "white", "black");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "blue", "yellow", "white", "black");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }
    /*
     * Test cases for removeFirst
     */

    @Test
    public final void testRemoveFirstLeavingEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String x = m.removeFirst();
        assertEquals(mExpected, m);
        assertEquals("green", x);
    }

    @Test
    public final void testremoveFirstLeavingNonEmptyOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "red");
        String x = m.removeFirst();
        assertEquals(mExpected, m);
        assertEquals(x, "green");
    }

    @Test
    public final void testRemoveFirstLeavingNonEmptyMoreThanOne() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red");
        String x = m.removeFirst();
        assertEquals(mExpected, m);
        assertEquals("blue", x);
    }

    @Test
    public final void testRemoveFirstMultiple() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "blue", "yellow", "white", "black");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "blue", "yellow", "white");
        String x = m.removeFirst();
        assertEquals(mExpected, m);
        assertEquals("black", x);
    }

    /*
     * Test cases for isInInsertionMode
     */
    @Test
    public final void testIsInInsertionModeEmptyTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, true);
        boolean x = m.isInInsertionMode();
        assertEquals(mExpected, m);
        assertEquals(true, x);
    }

    @Test
    public final void testIsInInsertionModeEmptyFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER,
                false);
        boolean x = m.isInInsertionMode();
        assertEquals(mExpected, m);
        assertEquals(false, x);
    }

    @Test
    public final void testIsInInsertionModeNonEmptyOneTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, true,
                "green");
        boolean x = m.isInInsertionMode();
        assertEquals(mExpected, m);
        assertEquals(true, x);
    }

    @Test
    public final void testIsInInsertionModeNonEmptyOneFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "green");
        boolean x = m.isInInsertionMode();
        assertEquals(mExpected, m);
        assertEquals(false, x);
    }

    @Test
    public final void testIsInInsertionModeNonEmptyMoreThanOneTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "yellow");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, true,
                "green", "yellow");
        boolean x = m.isInInsertionMode();
        assertEquals(mExpected, m);
        assertEquals(true, x);
    }

    @Test
    public final void testIsInInsertionModeNonEmptyMoreThanOneFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "yellow");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "green", "yellow");
        boolean x = m.isInInsertionMode();
        assertEquals(mExpected, m);
        assertEquals(false, x);
    }

    @Test
    public final void testIsInInsertionModeMultipleTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "blue", "yellow", "white", "black");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red", "blue", "yellow", "white", "black");
        boolean x = m.isInInsertionMode();
        assertEquals(mExpected, m);
        assertEquals(true, x);
    }

    @Test
    public final void testIsInInsertionModeMultipleFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "blue", "yellow", "white", "black");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "blue", "yellow", "white", "black");
        boolean x = m.isInInsertionMode();
        assertEquals(mExpected, m);
        assertEquals(false, x);
    }

    /*
     * Test cases for size
     */

    @Test
    public final void testSizeEmptyIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        int i = m.size();
        assertEquals(mExpected, m);
        assertEquals(i, 0);
    }

    @Test
    public final void testSizeEmptyIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        int i = m.size();
        assertEquals(mExpected, m);
        assertEquals(i, 0);
    }

    @Test
    public final void testSizeNonEmptyOneTrueIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        int i = m.size();
        assertEquals(mExpected, m);
        assertEquals(i, 1);
    }

    @Test
    public final void testSizeNonEmptyOneTrueIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green");
        int i = m.size();
        assertEquals(mExpected, m);
        assertEquals(i, 1);
    }

    @Test
    public final void testSizeNonEmptyMoreThanOneIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red");
        int i = m.size();
        assertEquals(mExpected, m);
        assertEquals(i, 2);
    }

    @Test
    public final void testSizeNonEmptyMoreThanOneIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red");
        int i = m.size();
        assertEquals(mExpected, m);
        assertEquals(i, 2);
    }

    @Test
    public final void testSizeMultipleIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "blue", "yellow", "white", "black");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, true,
                "green", "red", "blue", "yellow", "white", "black");
        int i = m.size();
        assertEquals(mExpected, m);
        assertEquals(6, i);
    }

    @Test
    public final void testSizeMultipleIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "blue", "yellow", "white", "black");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "green", "red", "blue", "yellow", "white", "black");
        int i = m.size();
        assertEquals(mExpected, m);
        assertEquals(6, i);
    }

    /*
     * Test cases for order
     */
    @Test
    public final void testOrderEmptyIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        Comparator<String> temp = m.order();
        assertEquals(mExpected, m);
        assertEquals(ORDER, temp);
    }

    @Test
    public final void testOrderEmptyIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        Comparator<String> temp = m.order();
        assertEquals(mExpected, m);
        assertEquals(ORDER, temp);
    }

    @Test
    public final void testOrderNonEmptyOneTrueIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        Comparator<String> temp = m.order();
        assertEquals(mExpected, m);
        assertEquals(ORDER, temp);
    }

    @Test
    public final void testOrderNonEmptyOneTrueIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green");
        Comparator<String> temp = m.order();
        assertEquals(mExpected, m);
        assertEquals(ORDER, temp);
    }

    @Test
    public final void testOrderNonEmptyMoreThanOneIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red");
        Comparator<String> temp = m.order();
        assertEquals(mExpected, m);
        assertEquals(ORDER, temp);
    }

    @Test
    public final void testOrderNonEmptyMoreThanOneIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red");
        Comparator<String> temp = m.order();
        assertEquals(mExpected, m);
        assertEquals(ORDER, temp);
    }

    @Test
    public final void testOrderMultipleIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "blue", "yellow", "white", "black");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, true,
                "green", "red", "blue", "yellow", "white", "black");
        Comparator<String> temp = m.order();
        assertEquals(mExpected, m);
        assertEquals(ORDER, temp);
    }

    @Test
    public final void testOrderMultipleIsInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "green", "red", "blue", "yellow", "white", "black");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false,
                "green", "red", "blue", "yellow", "white", "black");
        Comparator<String> temp = m.order();
        assertEquals(mExpected, m);
        assertEquals(ORDER, temp);
    }

    @Test
    public final void test1() {
        int[] array = { 1, 2, 1, 3 };

        System.out.print(smallestIsUnique(array, 1));
    }

    public static boolean smallestIsUnique(int[] array, int last) {
//        boolean unique = true;
//        if (1 <= last && array[1] == array[0]) {
//            unique = false;
//        } else if (2 <= last && array[2] == array[0]) {
//            unique = false;
//        }
//        return unique;
        return !((last >= 1 && array[0] == array[1])
                || (last >= 2 && array[0] == array[2]));
    }
}
