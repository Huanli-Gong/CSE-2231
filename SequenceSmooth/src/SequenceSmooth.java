import components.sequence.Sequence;

/**
 * Implements method to smooth a {@code Sequence<Integer>}.
 *
 * @author Put your name here
 *
 */
public final class SequenceSmooth {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private SequenceSmooth() {
    }

    /**
     * Returns the integer average of two given {@code int}s.
     *
     * @param j
     *            the first of two integers to average
     * @param k
     *            the second of two integers to average
     * @return the integer average of j and k
     * @ensures average = (j+k)/2
     */
    public static int average(int j, int k) {
        int average;
        if (j < 0 == k < 0) {
            average = j / 2 + k / 2 + (j % 2 + k % 2) / 2;
        } else {
            average = (j + k) / 2;
        }
        return average;
    }

    /**
     * Smooths a given {@code Sequence<Integer>}.
     *
     * @param s1
     *            the sequence to smooth
     * @param s2
     *            the resulting sequence
     * @replaces s2
     * @requires |s1| >= 1
     * @ensures <pre>
     * |s2| = |s1| - 1  and
     *  for all i, j: integer, a, b: string of integer
     *      where (s1 = a * <i> * <j> * b)
     *    (there exists c, d: string of integer
     *       (|c| = |a|  and
     *        s2 = c * <(i+j)/2> * d))
     * </pre>
     */
    public static void smooth(Sequence<Integer> s1, Sequence<Integer> s2) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s2 != null : "Violation of: s2 is not null";
        assert s1 != s2 : "Violation of: s1 is not s2";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";
        s2.clear();
        for (int i = 0; i < s1.length() - 1; i++) {
            if (s1.entry(i) < 0 == s1.entry(i + 1) < 0) {
                s2.add(i, s1.entry(i) / 2 + s1.entry(i + 1) / 2
                        + (s1.entry(i) % 2 + s1.entry(i + 1) % 2) / 2);
            } else {
                s2.add(i, (s1.entry(i) + s1.entry(i + 1)) / 2);
            }
        }
    }

//    /**
//     * Smooths a given {@code Sequence<Integer>}.
//     *
//     * @param s1
//     *            the sequence to smooth
//     * @param s2
//     *            the resulting sequence
//     * @replaces s2
//     * @requires |s1| >= 1
//     * @ensures <pre>
//     * |s2| = |s1| - 1  and
//     *  for all i, j: integer, a, b: string of integer
//     *      where (s1 = a * <i> * <j> * b)
//     *    (there exists c, d: string of integer
//     *       (|c| = |a|  and
//     *        s2 = c * <(i+j)/2> * d))
//     * </pre>
//     */
//    public static void smooth(Sequence<Integer> s1, Sequence<Integer> s2) {
//        assert s1 != null : "Violation of: s1 is not null";
//        assert s2 != null : "Violation of: s2 is not null";
//        assert s1 != s2 : "Violation of: s1 is not s2";
//        assert s1.length() >= 1 : "Violation of: |s1| >= 1";
//        s2.clear();
//        int i = s1.remove(0);
//        if (s1.length() > 0) {
//            smooth(s1, s2);
//            int j = s1.remove(0);
//            if (i < 0 == j < 0) {
//                s2.add(0, i / 2 + j / 2 + (i % 2 + j % 2) / 2);
//            } else {
//                s2.add(0, (i + j) / 2);
//            }
//            s1.add(0, j);
//        }
//        s1.add(0, i);
//    }

//    /**
//     * Smooths a given {@code Sequence<Integer>}.
//     *
//     * @param s1
//     *            the sequence to smooth
//     * @requires |s1| >= 1
//     * @return the resulting sequence
//     * @ensures <pre>
//     * |smooth| = |s1| - 1  and
//     *  for all i, j: integer, a, b: string of integer
//     *      where (s1 = a * <i> * <j> * b)
//     *    (there exists c, d: string of integer
//     *       (|c| = |a|  and
//     *        smooth = c * <(i+j)/2> * d))
//     * </pre>
//     */
//    public static Sequence<Integer> smooth(Sequence<Integer> s1) {
//        assert s1 != null : "Violation of: s1 is not null";
//        assert s1.length() >= 1 : "Violation of: |s1| >= 1";
//        Sequence<Integer> s2 = s1.newInstance();
//        for (int i = 0; i < s1.length() - 1; i++) {
//            if (s1.entry(i) < 0 == s1.entry(i + 1) < 0) {
//                s2.add(i, s1.entry(i) / 2 + s1.entry(i + 1) / 2
//                        + (s1.entry(i) % 2 + s1.entry(i + 1) % 2) / 2);
//            } else {
//                s2.add(i, (s1.entry(i) + s1.entry(i + 1)) / 2);
//            }
//        }
//        return s2;
//    }

//    /**
//     * Smooths a given {@code Sequence<Integer>}.
//     *
//     * @param s1
//     *            the sequence to smooth
//     * @requires |s1| >= 1
//     * @return the resulting sequence
//     * @ensures <pre>
//     * |smooth| = |s1| - 1  and
//     *  for all i, j: integer, a, b: string of integer
//     *      where (s1 = a * <i> * <j> * b)
//     *    (there exists c, d: string of integer
//     *       (|c| = |a|  and
//     *        smooth = c * <(i+j)/2> * d))
//     * </pre>
//     */
    public static Sequence<Integer> smooth(Sequence<Integer> s1) {
        assert s1 != null : "Violation of: s1 is not null";
        assert s1.length() >= 1 : "Violation of: |s1| >= 1";
        Sequence<Integer> s2 = s1.newInstance();
        int i = s1.remove(0);
        if (s1.length() > 0) {
            smooth(s1, s2);
            int j = s1.remove(0);
            s2.add(0, average(i, j));
            s1.add(0, j);
        }
        s1.add(0, i);
        return s2;
    }
}
