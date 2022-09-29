import components.queue.Queue;
import components.queue.Queue1L;
import components.sequence.Sequence;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.tree.Tree;
import components.tree.Tree1;

/**
 * This program calculates the value of a Boolean expression consisting of
 * Boolean operators NOT, AND, and OR, operands T and F, and parentheses.
 *
 * @author Put your name here
 *
 */
public final class BooleanExpressionEvaluator {

    /**
     * Private no-argument constructor so this utility class cannot be
     * instantiated.
     */
    private BooleanExpressionEvaluator() {
    }

    /**
     * The length of the two longest tokens in the bool-exp language.
     */
    private static final int LENGTH_OF_LONGEST_TOKENS = 3;

    /**
     * Tokenizes the entire input getting rid of all whitespace separators and
     * returning the non-separator tokens in a {@code Queue<String>}. Note the
     * requires clause that the input be well-formed. This implementation
     * assumes the input does not contain any invalid characters or tokens and
     * does not do any error checking.
     *
     * @param source
     *            the input
     * @return the queue of tokens
     * @requires <pre>
     * [source only contains valid tokens for the Boolean expression
     *  grammar and white space to separate them]
     * </pre>
     * @ensures tokens = [the non-whitespace tokens in source]
     */
    private static Queue<String> tokens(String source) {
        Queue<String> tokens = new Queue1L<String>();
        int pos = 0;
        while (pos < source.length()) {
            switch (source.charAt(pos)) {
                case 'T': {
                    tokens.enqueue("T");
                    pos++;
                    break;
                }
                case 'F': {
                    tokens.enqueue("F");
                    pos++;
                    break;
                }
                case '(': {
                    tokens.enqueue("(");
                    pos++;
                    break;
                }
                case ')': {
                    tokens.enqueue(")");
                    pos++;
                    break;
                }
                case 'N': {
                    tokens.enqueue("NOT");
                    pos += LENGTH_OF_LONGEST_TOKENS;
                    break;
                }
                case 'A': {
                    tokens.enqueue("AND");
                    pos += LENGTH_OF_LONGEST_TOKENS;
                    break;
                }
                case 'O': {
                    tokens.enqueue("OR");
                    pos += 2;
                    break;
                }
                default: {
                    pos++;
                    break;
                }
            }
        }
        return tokens;
    }

//    /**
//     * Evaluates a Boolean expression and returns its value.
//     *
//     * @param tokens
//     *            the {@code Queue<String>} that starts with a bool-expr string
//     * @return value of the expression
//     * @updates tokens
//     * @requires [a bool-expr string is a prefix of tokens]
//     * @ensures <pre>
//     * valueOfBoolExpr =
//     *   [value of longest bool-expr string at start of #tokens]  and
//     * #tokens = [longest bool-expr string at start of #tokens] * tokens
//     * </pre>
//     */
//    public static boolean valueOfBool(Queue<String> tokens) {
//        assert tokens != null : "Violation of: tokens is not null";
//
//        boolean value;
//        if (tokens.front().equals("NOT")) {
//            tokens.dequeue();
//            tokens.dequeue();
//            value = !valueOfBoolExpr(tokens);
//            tokens.dequeue();
//        } else if (tokens.front().equals("(")) {
//            tokens.dequeue();
//            value = valueOfBoolExpr(tokens);
//            String op = tokens.dequeue();
//            boolean nextTerm = valueOfBoolExpr(tokens);
//            tokens.dequeue();
//            if (op.equals("AND")) {
//                value = value && nextTerm;
//            } else {
//                value = value || nextTerm;
//            }
//        } else {
//            value = tokens.dequeue().equals("T");
//        }
//
//        return value;
//    }

    public static boolean valueOfBoolexpr(Queue<String> tokens) {
        boolean value;
        String front = tokens.dequeue();
        if (front.equals("NOT")) {
            value = !valueOfBoolexpr(tokens);
        } else if (front.equals("AND")) {
            value = valueOfBoolexpr(tokens) && valueOfBoolexpr(tokens);
        } else if (front.equals("OR")) {
            value = valueOfBoolexpr(tokens) || valueOfBoolexpr(tokens);
        } else {
            value = front.equals("T");
        }
        return value;
    }

    public static Tree<Integer> valueOfTree(Queue<String> tokens) {
        Tree<Integer> tree = new Tree1<>();
        if (tokens.front().equals("(")) {
            tokens.dequeue();
            tokens.dequeue();
        } else {
            int root = Integer.parseInt(tokens.dequeue());
            Sequence<Tree<Integer>> forset = tree.newSequenceOfTree();
            tokens.dequeue();
            while (!tokens.front().equals(")")) {
                forset.add(forset.length(), valueOfTree(tokens));
            }
            tree.assemble(root, forset);
            tokens.dequeue();
        }
        return tree;
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

        Queue<String> q1 = createFromArgsTest("(", ")", "42", "(", "(", ")",
                "8", "(", ")", ")", "0", "(", ")");
        Queue<String> q2 = createFromArgsTest("0", "(", ")");
        Queue<String> q3 = createFromArgsTest("42", "(", "(", ")", "8", "(",
                ")", ")", "0", "(", ")");
        out.println(valueOfTree(q1));
        out.println(q1);
        out.println(valueOfTree(q2));
        out.println(q2);
        out.println(valueOfTree(q3));
        out.println(q3);
        out.print("Enter a valid Boolean expression: ");
        String source = in.nextLine();
        while (source.length() > 0) {
            boolean value = valueOfBoolexpr(tokens(source));
            out.println(source + " = " + value);
            out.print("Enter a valid Boolean expression: ");
            source = in.nextLine();
        }
        in.close();
        out.close();
    }

}
