import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Huanli Gong, Ziyang Lin
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /*
     * Test cases for no Argument constructors
     */

    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIntArgumentConstructorZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIntArgumentConstructorOne() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(1);
        NaturalNumber nExpected = this.constructorRef(1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testIntArgumentConstructorRoutine() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(233);
        NaturalNumber nExpected = this.constructorRef(233);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test cases for string constructors
     */

    @Test
    public final void testIntArgumentConsturctorMaxInteger() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testSTRArgumentConsturctorZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef("0");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testSTRArgumentConsturctorOne() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("1");
        NaturalNumber nExpected = this.constructorRef("1");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testSTRArgumentConsturctorRoutine() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("123");
        NaturalNumber nExpected = this.constructorRef("123");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testSTRArgumentConsturctorLarge() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("123352245246262624524");
        NaturalNumber nExpected = this.constructorRef("123352245246262624524");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test cases for NaturalNumber constructors
     */

    @Test
    public final void testNaturalNumberArgumentConsturctorZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(new NaturalNumber1L(0));
        NaturalNumber nExpected = this.constructorRef(new NaturalNumber1L(0));
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNaturalNumberArgumentConsturctorOne() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(new NaturalNumber1L(1));
        NaturalNumber nExpected = this.constructorRef(new NaturalNumber1L(1));
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNaturalNumberArgumentConsturctorMaxInteger() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this
                .constructorTest(new NaturalNumber1L(Integer.MAX_VALUE));
        NaturalNumber nExpected = this
                .constructorRef(new NaturalNumber1L(Integer.MAX_VALUE));
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNaturalNumberArgumentConsturctorLarge() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this
                .constructorTest(new NaturalNumber1L("2141353425124512451"));
        NaturalNumber nExpected = this
                .constructorRef(new NaturalNumber1L("2141353425124512451"));
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNaturalNumberArgumentConsturctorRoutine() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(new NaturalNumber1L("43"));
        NaturalNumber nExpected = this
                .constructorRef(new NaturalNumber1L("43"));
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test cases for multiplyBy10
     */
    @Test
    public final void testMultiplyBy10Zero() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10multiplicandZero() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(1);
        /*
         * Call method under test
         */
        n.multiplyBy10(1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10AddendZero() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(1);
        NaturalNumber nExpected = this.constructorRef(10);
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10NonZero() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(1);
        NaturalNumber nExpected = this.constructorRef(11);
        /*
         * Call method under test
         */
        n.multiplyBy10(1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10MaxInteger() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE + "0");
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10Routine() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(121);
        NaturalNumber nExpected = this.constructorRef(1214);
        /*
         * Call method under test
         */
        n.multiplyBy10(4);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test cases for divideBy10
     */
    @Test
    public final void divideBy10DivisorZero() {
        /*
         * Set up variables.
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Call method under test.
         */
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(0, remainder);
    }

    @Test
    public final void s() {
        /*
         * Set up variables.
         */
        NaturalNumber n = this.constructorTest(32);
        NaturalNumber nExpected = this.constructorRef(32);
        /*
         * Call method under test.
         */
        String s = n.toString();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals("32", s);
    }

    @Test
    public final void s1() {
        /*
         * Set up variables.
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Call method under test.
         */
        String s = n.toString();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals("0", s);
    }

    @Test
    public final void s2() {
        /*
         * Set up variables.
         */
        NaturalNumber n = this.constructorTest(10);
        NaturalNumber nExpected = this.constructorRef(10);
        /*
         * Call method under test.
         */
        String s = n.toString();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals("10", s);
    }

    @Test
    public final void divideBy10QuotientZero() {
        /*
         * Set up variables.
         */
        NaturalNumber n = this.constructorTest(1);
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Call method under test.
         */
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(1, remainder);
    }

    @Test
    public final void divideBy10RemainderZero() {
        /*
         * Set up variables.
         */
        NaturalNumber n = this.constructorTest(10);
        NaturalNumber nExpected = this.constructorRef(1);
        /*
         * Call method under test.
         */
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(0, remainder);
    }

    @Test
    public final void divideBy10NonZero() {
        /*
         * Set up variables.
         */
        NaturalNumber n = this.constructorTest(11);
        NaturalNumber nExpected = this.constructorRef(1);
        /*
         * Call method under test.
         */
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(1, remainder);
    }

    @Test
    public final void divideBy10MaxInteger() {
        /*
         * Set up variables.
         */
        NaturalNumber n = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber nExpected = this.constructorRef(Integer.MAX_VALUE / 10);
        /*
         * Call method under test.
         */
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(Integer.MAX_VALUE % 10, remainder);
    }

    @Test
    public final void divideBy10Routine() {
        /*
         * Set up variables.
         */
        NaturalNumber n = this.constructorTest(32);
        NaturalNumber nExpected = this.constructorRef(3);
        /*
         * Call method under test.
         */
        int remainder = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(2, remainder);
    }
    /*
     * Test cases for isZero
     */

    @Test
    public final void isZeroTrue() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(0);
        /*
         * Call method under test
         */
        boolean isZero = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(isZero, true);
    }

    @Test
    public final void isZeroFalseOne() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(1);
        /*
         * Call method under test
         */
        boolean isZero = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(isZero, false);
    }

    @Test
    public final void isZeroFalseRoutine() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(987);
        /*
         * Call method under test
         */
        boolean isZero = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(isZero, false);
    }

    @Test
    public final void To() {
        /*
         * Set up variables
         */
        NaturalNumber n = this.constructorTest(987);
        /*
         * Call method under test
         */
        boolean isZero = n.isZero();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(isZero, false);
    }
}
