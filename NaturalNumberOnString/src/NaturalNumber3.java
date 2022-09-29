import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * {@code NaturalNumber} digitsresented as a {@code String} with implementations
 * of primary methods.
 *
 * @convention <pre>
 * [all characters of $this.digits are '0' through '9']  and
 * [$this.digits does not start with '0']
 * </pre>
 * @correspondence <pre>
 * this = [if $this.digits = "" then 0
 *         else the decimal number whose ordinary depiction is $this.digits]
 * </pre>
 *
 * @author Huanli Gong, Ziyang Lin
 *
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * digitsresentation of {@code this}.
     */
    private String digits;

    /**
     * Creator of initial digitsresentation.
     */
    private void createNewdigits() {
        this.digits = "";
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public NaturalNumber3() {
        this.createNewdigits();
    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber3(int i) {
        assert i >= 0 : "Violation of: i >= 0";
        this.createNewdigits();
        if (i != 0) {
            this.digits = "" + i;
        }
    }

    /**
     * Constructor from {@code String}.
     *
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber3(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*") : ""
                + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";
        this.createNewdigits();
        if (!s.equals("0")) {
            this.digits = s;
        }
    }

    /**
     * Constructor from {@code NaturalNumber}.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     */
    public NaturalNumber3(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";
        this.createNewdigits();
        if (!n.isZero()) {
            this.digits = n.toString();
        }
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewdigits();
    }

    @Override
    public final void transferFrom(NaturalNumber source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NaturalNumber3 : ""
                + "Violation of: source is of dynamic type NaturalNumberExample";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber3 localSource = (NaturalNumber3) source;
        this.digits = localSource.digits;
        localSource.createNewdigits();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < RADIX : "Violation of: k < 10";
        this.digits = this.digits + k;
        if (this.digits.equals("0")) {
            this.digits = "";
        }
    }

    @Override
    public final int divideBy10() {
        int k = 0;
        if (this.digits.length() > 0) {
            k = Character.getNumericValue(
                    this.digits.charAt(this.digits.length() - 1));
            this.digits = this.digits.substring(0, this.digits.length() - 1);
        }
        return k;
    }

    @Override
    public final boolean isZero() {
        boolean isZero = true;
        int i = 0;
        while (i < this.digits.length() && isZero) {
            if (this.digits.charAt(i) != '0') {
                isZero = false;
            }
        }
        return isZero;
    }

    @Override
    public String toString() {
        String result = "0";
        if (!this.isZero()) {
            int bit = this.divideBy10();
            if (!this.toString().equals("0")) {
                result = this.toString() + bit;
            } else {
                result = "" + bit;
            }
            this.multiplyBy10(bit);
        }
        return result;
    }

}
