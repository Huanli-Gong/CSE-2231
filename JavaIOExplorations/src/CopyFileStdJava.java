import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Program to copy a text file into another file.
 *
 * @author Put your name here
 *
 */
public final class CopyFileStdJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CopyFileStdJava() {
    }

    /**
     * Encrypts upper- and lowercase characters by shifting them according to a
     * key.
     *
     * @param ch
     *            the letter to be encrypted
     * @param key
     *            the encryption key
     * @return the encrypted letter
     */
    public static char encrypt(char ch, int key) {
        int base = 0;
        if ('A' <= ch && ch <= 'Z') {
            base = 'A';
        } else if ('a' <= ch && ch <= 'z') {
            base = 'a';
        } else {
            return ch;
        } // Not a letter
        int offset = ch - base + key;
        final int LETTERS = 26; // Number of letters in the Roman alphabet
        if (offset > LETTERS) {
            offset = offset - LETTERS;
        } else if (offset < 0) {
            offset = offset + LETTERS;
        }
        return (char) (base + offset);
    }

    /**
     * Prints a message describing proper usage.
     */
    public static void usage() {
        System.out.println("Usage: java CaesarCipher [-d] infile outfile");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments: input-file-name output-file-name
     * @throws FileNotFoundException
     */
    public static void main(String[] args) {

        final int DEFAULT_KEY = 3;
        int key = DEFAULT_KEY;
        String inFile = "";
        String outFile = "";
        int files = 0; // Number of command line arguments that are files

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.charAt(0) == '-') {
                // It is a command line option

                char option = arg.charAt(1);
                if (option == 'd') {
                    key = -key;
                } else {
                    System.out.println(
                            "Usage: java CaesarCipher [-d] infile outfile");
                    return;
                }
            } else {
                // It is a file name

                files++;
                if (files == 1) {
                    inFile = arg;
                } else if (files == 2) {
                    outFile = arg;
                }
            }
        }
        if (files != 2) {
            System.out.println("Usage: java CaesarCipher [-d] infile outfile");
            return;
        }
        try {
            Scanner in = new Scanner(new File(inFile));
            in.useDelimiter(""); // Process individual characters
            PrintWriter out = new PrintWriter(outFile);

            while (in.hasNext()) {
                String line = in.nextLine();
                out.println(line);
            }

            in.close();
            out.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
