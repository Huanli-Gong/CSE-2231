import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Create an HTML page containing a Tag Cloud for the given file.
 *
 * @author Huanli Gong, Ziyang Lin
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
     * Compare {@code Map.Pair} in decreasing order of value.
     */
    private static class IntegerComparator
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
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
     * @throws IOException
     * @updates wordCount
     * @requires file.is_open
     * @ensures {@code wordCount} contains the word count of {@code file},
     *          ignoring all the separators in it
     */
    private static void loadText(BufferedReader file,
            Map<String, Integer> wordCount) throws IOException {
        String line = file.readLine();
        while (line != null) {
            int position = 0;
            while (position < line.length()) {

                String token = nextWordOrSeparator(line, position);
                token = token.toLowerCase();
                if (SEPARATORS.indexOf(token.charAt(0)) < 0) {
                    if (wordCount.containsKey(token)) {
                        wordCount.put(token, wordCount.remove(token) + 1);
                    } else {
                        wordCount.put(token, 1);
                    }
                }
                position += token.length();
            }
            line = file.readLine();
        }
    }

    /**
     * Calculates the font size of the word.
     *
     * @param frequency
     *            the frequency of a word
     * @param maxF
     *            Max number of frequency of a word.
     * @param minF
     *            Min number of frequency of a word.
     * @requires frequency >= 0
     * @return fontSize
     * @ensures the correct fontSize pertaining to the word frequency
     *
     */
    private static int fontSize(int frequency, int maxF, int minF) {

        final int max = 48;
        final int min = 11;

        int difference = max - min;
        int fontSize = max;
        if (minF != maxF) {
            fontSize = (difference * (frequency - minF)) / (maxF - minF) + min;
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
     * @throws IOException
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
            BufferedWriter out, String file) throws IOException {
        ArrayList<Map.Entry<String, Integer>> sortWordCount = new ArrayList<>();
        for (Map.Entry<String, Integer> pair : wordCount.entrySet()) {
            sortWordCount.add(new java.util.AbstractMap.SimpleEntry<>(pair));
        }
        sortWordCount.sort(new IntegerComparator());
        Map<String, Integer> topWords = new HashMap<>();
        for (int i = 0; i < num; i++) {
            Map.Entry<String, Integer> tmp = sortWordCount.get(i);
            topWords.put(tmp.getKey(), tmp.getValue());
        }
        SortedMap<String, Integer> sortedWords = new TreeMap<String, Integer>(
                topWords);
        out.write("<html>");
        out.write("<head>");
        out.write("<title>");
        out.write("Top " + num + " words in " + file);
        out.write("</title>");
        out.write(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-"
                        + "sw2/assignments/projects/tag-cloud-generator/data"
                        + "/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.write("</head>");
        out.write("<body>");
        out.write("<h2>" + "Top " + num + " words in " + file + "</h2>");
        out.write("<hr>");
        out.write("<div class=\"cdiv\">");
        out.write("<p class=\"cbox\">");

        for (Entry<String, Integer> entry : sortedWords.entrySet()) {
            out.write("<span style=\"cursor:default\"" + " class=\"f"
                    + fontSize(entry.getValue(),
                            sortWordCount.get(0).getValue(),
                            sortWordCount.get(num - 1).getValue())
                    + "\" title=\"count:" + entry.getValue() + "\">");
            out.write(entry.getKey());
            out.write("</span>\n");
        }

        out.write("</p>");
        out.write("</div>");
        out.write("</body>");
        out.write("</html>");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // import file
        System.out.print("Please enter the path name of the input file: ");
        String inputfile = in.nextLine();
        BufferedReader fileReader;
        try {
            fileReader = new BufferedReader(new FileReader(inputfile));
        } catch (IOException e) {
            System.err.println("Error opening input file");
            in.close();
            return;
        }
        System.out.println("imported!");

        // create the map
        Map<String, Integer> wordCount = new HashMap<>();

        // add each valid word into the map
        try {
            loadText(fileReader, wordCount);
        } catch (IOException e1) {
            System.err.println("Error reading from input file");
        }
        try {
            fileReader.close();
        } catch (IOException e) {
            System.err.println("Error closing input file");
        }
        int size = wordCount.size();
        System.out.println("there are " + size + " words.");
        System.out.print("Please enter the path name of the output file: ");

        String outputFile = in.nextLine();
        //set the rang of the output
        System.out
                .println("Please enter the number of the output between 0 and "
                        + wordCount.size());
        int temp = in.nextInt();
        while (temp > wordCount.size()) {
            System.out.println(
                    "This is not a vaild number,please enter the num of "
                            + "the output between 0 and  " + wordCount.size());
            temp = Integer.parseInt(in.nextLine());
        }

        //create the output HTML
        BufferedWriter fileWriter;
        try {
            fileWriter = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            System.err.println("Error creating output file");
            in.close();
            return;
        }
        try {
            createHTML(wordCount, temp, fileWriter, inputfile);
        } catch (IOException e1) {
            System.err.println("Error writing to output file");
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Error closing output file");
        }
        System.out.println("Congratulation! you got it!");
        in.close();

    }

}
