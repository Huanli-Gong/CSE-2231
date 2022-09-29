import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Create an HTML page containing a Tag Cloud for the given file.
 *
 * @author Ziyang Lin, Hengli Gong
 */
public final class TagCloudGenerator {

    /**
     * Set all the separator in the file.
     */
    private static final String SEPARATORS = " ,.-/!@#$%^&*()_+=|[]{}~`<>?'\";:\\";

    /**
     * private the construction.
     */
    private TagCloudGenerator() {

    }

    /**
     * Compare {@code Map.Pair} in alphabetical order.
     */
    private static class Stringcomparator
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            return o1.key().compareToIgnoreCase(o2.key());
        }
    }

    /**
     * Compare {@code Map.Pair} in decreasing order of value.
     */
    private static class Integercomparator
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            return o2.value().compareTo(o1.value());
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code SEPARATORS}) or "separator string" (maximal length string of
     * characters in {@code SEPARATORS}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection entries(SEPARATORS) = {}
     * then
     *   entries(nextWordOrSeparator) intersection entries(SEPARATORS) = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection entries(SEPARATORS) /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of entries(SEPARATORS)  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of entries(SEPARATORS))
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position) {
        assert text != null : "Violation of: text is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";
        int endIndex = position;
        while (endIndex < text.length()
                && SEPARATORS.indexOf(text.charAt(position)) < 0 == SEPARATORS
                        .indexOf(text.charAt(endIndex)) < 0) {
            endIndex++;
        }
        return text.substring(position, endIndex);
    }

    /**
     * Count the frequency of a word in a text file .
     *
     * @param file
     *            the input file
     * @param wordCount
     *            map of words and their counts
     * @updates wordCount
     * @requires file.is_open
     * @ensures {@code wordCount} contains the word count of {@code file},
     *          ignoring all the separators in it
     */
    private static void loadText(SimpleReader file,
            Map<String, Integer> wordCount) {

        while (!file.atEOS()) {

            String line = file.nextLine();
            int position = 0;
            while (position < line.length()) {

                String token = nextWordOrSeparator(line, position);
                token = token.toLowerCase();
                if (SEPARATORS.indexOf(token.charAt(0)) < 0) {
                    if (wordCount.hasKey(token)) {
                        wordCount.replaceValue(token,
                                wordCount.value(token) + 1);
                    } else {
                        wordCount.add(token, 1);
                    }
                }
                position += token.length();
            }
        }
    }

    /**
     * Calculates the font size of the word.
     *
     * @param frequency
     *            the frequency of a word
     * @requires frequency >= 0
     * @return fontSize
     * @ensures the correct fontSize pertaining to the word frequency
     *
     */
    private static int fontSize(int frequency, int maxF, int minF) {

        final int max = 48;
        final int min = 11;

        int difference = max - min;
        int fontSize;
        if (minF != maxF) {
            fontSize = (difference * (frequency - minF)) / (maxF - minF) + min;
        } else {
            fontSize = (difference * (frequency - minF)) + min;
        }

        return fontSize;
    }

    /**
     * Create the HTML page.
     *
     * @param wordCount
     *            map of words and their counts
     * @param num
     *            the number of the word
     * @param out
     *            the output stream
     * @param file
     *            string of input file
     *
     * @updates out.content
     * @requires out.is_open & sortedWords should be in alphabetical order &
     *           sortedWords.isInExtractionMode
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML page containing word counts]
     * </pre>
     */
    private static void createHTML(Map<String, Integer> wordCount, int num,
            SimpleWriter out, String file) {
        final int max = 48;
        final int min = 11;
        Comparator<Pair<String, Integer>> cp = new Integercomparator();
        SortingMachine<Pair<String, Integer>> sortWordCount = new SortingMachine1L<>(
                cp);

        for (Map.Pair<String, Integer> word : wordCount) {
            sortWordCount.add(word);
        }
        sortWordCount.changeToExtractionMode();

        Comparator<Pair<String, Integer>> cs = new Stringcomparator();
        SortingMachine<Pair<String, Integer>> sortedWords = new SortingMachine1L<>(
                cs);
        int maxF = 0;
        int minF = 0;
        for (int i = 0; i < num; i++) {
            Map.Pair<String, Integer> temp = sortWordCount.removeFirst();

            if (i == 0) {
                maxF = temp.value();
            } else if (i == num - 1) {
                minF = temp.value();
            }
            sortedWords.add(temp);
        }
        sortedWords.changeToExtractionMode();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>");
        out.println("Top " + sortedWords.size() + " words in " + file);
        out.println("</title>");
        out.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-"
                        + "sw2/assignments/projects/tag-cloud-generator/data"
                        + "/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>" + "Top " + sortedWords.size() + " words in " + file
                + "</h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");

        while (sortedWords.size() > 0) {
            Pair<String, Integer> temp = sortedWords.removeFirst();
            out.println("<span style=\"cursor:default\"" + " class=\"f"
                    + fontSize(temp.value(), maxF, minF) + "\" title=\"count:"
                    + temp.value() + "\">");
            out.println(temp.key());
            out.println("</span>");
        }

        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        // import file
        out.print("Please enter the path name of the input file: ");
        String inputfile = in.nextLine();
        SimpleReader file = new SimpleReader1L(inputfile);
        out.println("imported!");

        // create the map
        Map<String, Integer> wordCount = new Map1L<>();

        // add each valid word into the map
        loadText(file, wordCount);

        int size = wordCount.size();
        out.println("there are " + size + " words.");
        out.print("Please enter the path name of the output file: ");
        String outputFile = in.nextLine();
        //set the rang of the output
        out.println("Please enter the number of the output between 0 and "
                + wordCount.size());
        int temp = Integer.parseInt(in.nextLine());
        while (temp > wordCount.size()) {
            out.println("This is not a vaild number,please enter the num of "
                    + "the output between 0 and  " + wordCount.size());
            temp = Integer.parseInt(in.nextLine());
        }

        //create the output HTML
        SimpleWriter output = new SimpleWriter1L(outputFile);
        createHTML(wordCount, temp, output, inputfile);

        out.println("Congratulation! you got it!");

        in.close();
        out.close();
        file.close();
        output.close();
    }

}
