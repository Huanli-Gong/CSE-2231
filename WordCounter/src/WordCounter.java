import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Counts word occurrences in a given input file and outputs an HTML document
 * with a table of the words and counts listed in alphabetical order.
 *
 * @author Huanli Gong
 */
public final class WordCounter {
    /**
     * Default constructor--private to prevent instantiation.
     */
    private WordCounter() {
    }

    /**
     * Compare {@code String}s in alphabetical order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";
        charSet.clear(); //charSet is a replaces-mode parameter
        for (int i = 0; i < str.length(); i++) {
            if (!charSet.contains(str.charAt(i))) {
                //no requires clause to rule out repeated characters in str
                charSet.add(str.charAt(i));
            }
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";
        int endIndex = position;
        while (endIndex < text.length()
                && separators.contains(text.charAt(position)) == separators
                        .contains(text.charAt(endIndex))) {
            //Until meet a different type of char
            endIndex++;
        }
        return text.substring(position, endIndex);
    }

    /**
     * Load the {@code inputFile}, then store the connection between words and
     * counts into {@code wordsCounts} and store {@code words} into a queue in
     * alphabetical order.
     *
     * @param inputFile
     *            the {@code String} of input file name
     * @param words
     *            the {@code Queue} to store words in alphabetical order.
     * @param wordsCounts
     *            the {@code Map} to store the connection between words and
     *            counts
     * @param separatorSet
     *            the {@code Set} of separator characters
     * @replaces words,wordsCounts
     * @ensures words store words from input file in alphabetical order.
     *          wordsCounts store the connection between words and counts from
     *          input file.
     */
    private static void loadText(String inputFile, Queue<String> words,
            Map<String, Integer> wordsCounts, Set<Character> separatorSet) {
        words.clear();
        wordsCounts.clear();
        SimpleReader fileIn = new SimpleReader1L(inputFile);
        while (!fileIn.atEOS()) { //take every line
            String line = fileIn.nextLine();
            int position = 0;
            while (position < line.length()) { //take every word or separator
                String word = nextWordOrSeparator(line, position, separatorSet);
                position += word.length();
                if (!separatorSet.contains(word.charAt(0))) { //if it is a word
                    if (wordsCounts.hasKey(word)) {
                        //word's count +1
                        int count = wordsCounts.remove(word).value() + 1;
                        wordsCounts.add(word, count);
                    } else {
                        //add this word to map and queue
                        wordsCounts.add(word, 1);
                        words.enqueue(word);
                    }
                }
            }
        }
        Comparator<String> order = new StringLT();
        words.sort(order); //sort the queue in alphabetical order.
        fileIn.close();
    }

    /**
     * output a single well-formed HTML file displaying the name of the
     * {@code inputFile} in a heading followed by a table listing the words and
     * their corresponding counts. The {@code words} appear in the order in the
     * queue.
     *
     * @param outputFile
     *            the {@code String} of output file name
     * @param inputFile
     *            the {@code String} of input file name
     * @param words
     *            the {@code Queue} to store words in order.
     * @param wordsCounts
     *            the {@code Map} to store the connection between words and
     *            counts
     * @requires all words in the queue are keys in the map.
     * @ensures a single well-formed HTML file displaying the name of the input
     *          file in a heading followed by a table listing the words and
     *          their corresponding counts in the order in the queue is
     *          generated.
     */
    private static void generateHTML(String outputFile, String inputFile,
            Queue<String> words, Map<String, Integer> wordsCounts) {
        SimpleWriter outPage = new SimpleWriter1L(outputFile);
        /*
         * output header
         */
        outPage.println("<html>");
        outPage.println("<head>");
        outPage.println("<title>Words Counted in" + inputFile + "</title>");
        outPage.println("</head>");
        outPage.println("<body>");
        outPage.println("<h2>Words Counted in" + inputFile + "</h2>");
        outPage.println("<hr />");
        outPage.println("<table border=\"1\">");
        outPage.println("<tr><th>Words</th><th>Counts</th></tr>");
        for (String s : words) {
            //a table listing the words and their corresponding counts.
            outPage.println("<tr><td>" + s + "</td><td>" + wordsCounts.value(s)
                    + "</tr>");
        }
        /*
         * output footer
         */
        outPage.println("</table>");
        outPage.println("</body>");
        outPage.println("</html>");
        outPage.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        /*
         * Define separator characters
         */
        final String separatorStr = " \t,-./*-_\"\'?!~[]{}()<>#$%^&:;+=";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separatorStr, separatorSet);
        /*
         * Open input and output streams
         */
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        /*
         * Prompt the user
         */
        out.print("Enter the name of the input file: ");
        String inputFile = in.nextLine();
        out.print("Enter the name of the output file: ");
        String outputFile = in.nextLine();
        /*
         * Process files
         */
        Queue<String> words = new Queue1L<>();
        Map<String, Integer> wordsCounts = new Map1L<>();
        loadText(inputFile, words, wordsCounts, separatorSet);
        generateHTML(outputFile, inputFile, words, wordsCounts);
        out.println(outputFile + " has been generated.");
        /*
         * Close input and output streams
         */
        out.close();
        in.close();
    }
}
