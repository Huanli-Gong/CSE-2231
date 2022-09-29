import components.queue.Queue;
import components.queue.Queue1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program calculates the value of an expression consisting of numbers,
 * arithmetic operators, and parentheses.
 *
 * @author Put your name here
 *
 */
public final class ExpressionEvaluator {

    /**
     * Base used in number representation.
     */
    private static final int RADIX = 10;

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ExpressionEvaluator() {
    }

    /**
     * Evaluates a digit and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a digit
     * @return value of the digit
     * @updates source
     * @requires 1 < |source| and [the first character of source is a digit]
     * @ensures <pre>
     * valueOfDigit = [value of the digit at the start of #source]  and
     * #source = [digit string at start of #source] * source
     * </pre>
     */
    private static int valueOfDigit(StringBuilder source) {
        assert source != null : "Violation of: source is not null";
        int value = Character.digit(source.charAt(0), RADIX);
        source.deleteCharAt(0);
        return value;
    }

    /**
     * Evaluates a digit sequence and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a digit-seq string
     * @return value of the digit sequence
     * @updates source
     * @requires <pre>
     * [a digit-seq string is a proper prefix of source, which
     * contains a character that is not a digit]
     * </pre>
     * @ensures <pre>
     * valueOfDigitSeq =
     *   [value of longest digit-seq string at start of #source]  and
     * #source = [longest digit-seq string at start of #source] * source
     * </pre>
     */
    private static int valueOfDigitSeq(StringBuilder source) {
        assert source != null : "Violation of: source is not null";
        int value = valueOfDigit(source);
        while (Character.isDigit(source.charAt(0))) {
            value = value * RADIX + valueOfDigit(source);
        }
        return value;
    }

    /**
     * Evaluates a factor and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a factor string
     * @return value of the factor
     * @updates source
     * @requires <pre>
     * [a factor string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any factor string]
     * </pre>
     * @ensures <pre>
     * valueOfFactor =
     *   [value of longest factor string at start of #source]  and
     * #source = [longest factor string at start of #source] * source
     * </pre>
     */
    private static int valueOfFactor(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value;
        if (source.charAt(0) == '(') {
            source.deleteCharAt(0);
            value = valueOfExpr(source);
            source.deleteCharAt(0);
        } else {
            value = valueOfDigitSeq(source);
        }
        return value;
    }

    /**
     * Evaluates a term and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with a term string
     * @return value of the term
     * @updates source
     * @requires <pre>
     * [a term string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any term string]
     * </pre>
     * @ensures <pre>
     * valueOfTerm =
     *   [value of longest term string at start of #source]  and
     * #source = [longest term string at start of #source] * source
     * </pre>
     */
    private static int valueOfTerm(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value = valueOfFactor(source);
        while (source.charAt(0) == '*' || source.charAt(0) == '/') {
            if (source.charAt(0) == '*') {
                value = value * valueOfFactor(source.deleteCharAt(0));
            } else /* "/" */ {
                value = value / valueOfFactor(source.deleteCharAt(0));
            }
        }
        return value;
    }

    /**
     * Evaluates an expression and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with an expr string
     * @return value of the expression
     * @updates source
     * @requires <pre>
     * [an expr string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any expr string]
     * </pre>
     * @ensures <pre>
     * valueOfExpr =
     *   [value of longest expr string at start of #source]  and
     * #source = [longest expr string at start of #source] * source
     * </pre>
     */
    public static int valueOfExpr(StringBuilder source) {
        assert source != null : "Violation of: source is not null";

        int value = valueOfTerm(source);
        while (source.charAt(0) == '+' || source.charAt(0) == '-') {
            if (source.charAt(0) == '+') {
                value = value + valueOfTerm(source.deleteCharAt(0));
            } else /* "-" */ {
                value = value - valueOfTerm(source.deleteCharAt(0));
            }
        }
        return value;
    }

    public static int valueOfExpr(Queue<String> tokens) {
        int value;
        if (tokens.front().equals("(")) {
            tokens.dequeue();
            String op = tokens.dequeue();
            value = valueOfExpr(tokens);
            while (!tokens.front().equals(")")) {
                int next = valueOfExpr(tokens);
                if (op.equals("+")) {
                    value = value + next;
                } else if (op.equals("-")) {
                    value = value - next;
                } else if (op.equals("*")) {
                    value = value * next;
                } else {
                    value = value / next;
                }
            }
            tokens.dequeue();
        } else {
            value = Integer.parseInt(tokens.dequeue());
        }
        return value;
    }

    /**
     *
     * Creates and returns a {@code Queue<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the queue
     * @return the constructed queue
     * @ensures createFromArgsTest = [entries in args]
     */
    private static Queue<String> createFromArgsTest(String... args) {
        Queue<String> queue = new Queue1L<>();
        for (String s : args) {
            queue.enqueue(s);
        }
        return queue;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

//        Queue<String> q = createFromArgsTest("(", "-", "51", "10", "10", "10",
//                ")", "9");
//        out.println(valueOfExpr(q));

        out.print("Enter an expression followed by !: ");
        String source = in.nextLine();
        while (source.length() > 0) {
            /*
             * Parse and evaluate the expression after removing all white space
             * (spaces and tabs) from the user input.
             */
            int value = valueOfExpr(
                    new StringBuilder(source.replaceAll("[ \t]", "")));
            out.println(
                    source.substring(0, source.length() - 1) + " = " + value);
            out.print("Enter an expression followed by !: ");
            source = in.nextLine();
        }
        in.close();
        out.close();
    }

}
