import components.binarytree.BinaryTree;
import components.sequence.Sequence;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.tree.Tree;

/**
 * Utility class with implementation of {@code BinaryTree} static, generic
 * methods height and isInTree.
 *
 * @author Put your name here
 *
 */
public final class BinaryTreeMethods {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private BinaryTreeMethods() {
    }

    /**
     * Returns the height of the given {@code BinaryTree<T>}.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} whose height to return
     * @return the height of the given {@code BinaryTree}
     * @ensures height = ht(t)
     */
    public static <T> int height(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        int height = 0;
        if (t.size() > 0) {
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);
            height = 1 + Math.max(height(left), height(right));
            t.assemble(root, left, right);
        }
        return height;
    }

    /**
     * Returns the height of the given {@code Tree<T>}.
     *
     * @param <T>
     *            the type of the {@code Tree} node labels
     * @param t
     *            the {@code Tree} whose height to return
     * @return the height of the given {@code Tree}
     * @ensures height = ht(t)
     */
    public static <T> int height(Tree<T> t) {
        int height = 0;
        if (t.size() > 0) {
            Sequence<Tree<T>> children = t.newSequenceOfTree();
            T root = t.disassemble(children);
            for (int i = 0; i < children.length(); i++) {
                if (height < height(children.entry(i))) {
                    height = height(children.entry(i));
                }
            }
            height++;
            t.assemble(root, children);
        }
        return height;
    }

    /**
     * Returns true if the given {@code T} is in the given {@code BinaryTree<T>}
     * or false otherwise.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} to search
     * @param x
     *            the {@code T} to search for
     * @return true if the given {@code T} is in the given {@code BinaryTree},
     *         false otherwise
     * @ensures isInTree = [true if x is in t, false otherwise]
     */
    public static <T> boolean isInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        boolean isInTree = false;
        if (t.size() > 0) {
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);
            isInTree = root.equals(x) || isInTree(left, x)
                    || isInTree(right, x);
            t.assemble(root, left, right);
        }
        return isInTree;
    }

    /**
     * Returns true if the given {@code T} is in the given {@code BinaryTree<T>}
     * or false otherwise.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} to search
     * @param x
     *            the {@code T} to search for
     * @return true if the given {@code T} is in the given {@code BinaryTree},
     *         false otherwise
     * @ensures isInTree = [true if x is in t, false otherwise]
     */
    public static <T> boolean isInTree(Tree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        boolean isInTree = false;
        if (t.size() > 0) {
            Sequence<Tree<T>> children = t.newSequenceOfTree();
            T root = t.disassemble(children);
            isInTree = root.equals(x);
            for (int i = 0; i < children.length() && !isInTree; i++) {
                isInTree = isInTree(children.entry(i), x);
            }
            t.assemble(root, children);
        }
        return isInTree;
    }
//    public static <T> boolean isInTree(BinaryTree<T> t, T x) {
//        assert t != null : "Violation of: t is not null";
//        assert x != null : "Violation of: x is not null";
//        boolean isInTree = false;
//        for (T root : t) {
//            if (root.equals(x)) {
//                isInTree = true;
//            }
//        }
//        return isInTree;
//    }

    /**
     * Returns the size of the given {@code BinaryTree<T>}.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} whose size to return
     * @return the size of the given {@code BinaryTree}
     * @ensures size = |t|
     */
//    public static <T> int size(BinaryTree<T> t) {
//        int size = 0;
//        if (t.height() > 0) {
//            BinaryTree<T> left = t.newInstance();
//            BinaryTree<T> right = t.newInstance();
//            T root = t.disassemble(left, right);
//            size = 1 + size(left) + size(right);
//            t.assemble(root, left, right);
//        }
//        return size;
//    }
    public static <T> int size(BinaryTree<T> t) {
        int size = 0;
        for (T root : t) {
            size++;
        }
        return size;
    }

    /**
     * Returns the size of the given {@code Tree<T>}.
     *
     * @param <T>
     *            the type of the {@code Tree} node labels
     * @param t
     *            the {@code Tree} whose size to return
     * @return the size of the given {@code Tree}
     * @ensures size = |t|
     */
    public static <T> int size(Tree<T> t) {
        int size = 0;
        if (t.height() > 0) {
            size = 1;
            Sequence<Tree<T>> children = t.newSequenceOfTree();
            T root = t.disassemble(children);
            for (int i = 0; i < children.length(); i++) {
                size += size(children.entry(i));
            }
            t.assemble(root, children);
        }
        return size;
    }
//    public static <T> int size(Tree<T> t) {
//        int size = 0;
//        for (T element : t) {
//            size++;
//        }
//        return size;
//    }

    /**
     * Returns a copy of the the given {@code BinaryTree}.
     *
     * @param t
     *            the {@code BinaryTree} to copy
     * @return a copy of the given {@code BinaryTree}
     * @ensures copy = t
     */
    public static BinaryTree<String> copy(BinaryTree<String> t) {
        BinaryTree<String> copy = t.newInstance();
        if (t.size() > 0) {
            BinaryTree<String> left = t.newInstance();
            BinaryTree<String> right = t.newInstance();
            String root = t.disassemble(left, right);
            copy.assemble(root, copy(left), copy(right));
            t.assemble(root, left, right);
        }
        return copy;
    }

    /**
     * Returns a copy of the the given {@code Tree<Integer>}.
     *
     * @param t
     *            the {@code Tree<Integer>} to copy
     * @return a copy of the given {@code Tree<Integer>}
     * @ensures copy = t
     */
    public static Tree<Integer> copy(Tree<Integer> t) {
        Tree<Integer> copy = t.newInstance();
        if (t.size() > 0) {
            copy.assemble(t.root(), t.newSequenceOfTree());
            for (int i = 0; i < t.numberOfSubtrees(); i++) {
                Tree<Integer> subtree = t.removeSubtree(i);
                copy.addSubtree(i, copy(subtree));
                t.addSubtree(i, subtree);
            }
        }
        return copy;
    }

    /**
     * Returns the {@code String} prefix representation of the given
     * {@code BinaryTree<T>}.
     *
     * @param <T>
     *            the type of the {@code BinaryTree} node labels
     * @param t
     *            the {@code BinaryTree} to convert to a {@code String}
     * @return the prefix representation of {@code t}
     * @ensures treeToString = [the String prefix representation of t]
     */
    public static <T> String treeToString(BinaryTree<T> t) {
        String s = "()";
        if (t.size() > 0) {
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);
            s = root.toString() + "(" + treeToString(left) + treeToString(right)
                    + ")";
            t.assemble(root, left, right);
        }
        return s;
    }

    /**
     * Returns the {@code String} prefix representation of the given
     * {@code Tree<T>}.
     *
     * @param <T>
     *            the type of the {@code Tree} node labels
     * @param t
     *            the {@code Tree} to convert to a {@code String}
     * @return the prefix representation of {@code t}
     * @ensures treeToString = [the String prefix representation of t]
     */
    public static <T> String treeToString(Tree<T> t) {
        String s = "()";
        if (t.size() > 0) {
            Sequence<Tree<T>> children = t.newSequenceOfTree();
            T root = t.disassemble(children);
            s = root + "(";
            for (int i = 0; i < children.length(); i++) {
                s += treeToString(children.entry(i));
            }
            s += ")";
            t.assemble(root, children);
        }
        return s;
    }

    static boolean isHeap(BinaryTree<String> t) {
        boolean result = true;
        if (t.size() > 0) {
            BinaryTree<String> left = t.newInstance();
            BinaryTree<String> right = t.newInstance();
            String root = t.disassemble(left, right);
            result = isHeap(left) && isHeap(right) && result;
            if (left.size() > 0) {
                result = root.compareTo(left.root()) <= 0 && result;
                if (right.size() > 0) {
                    result = root.compareTo(right.root()) <= 0 && result;
                }
            } else if (right.size() > 0) {
                result = false;
            }
            t.assemble(root, left, right);
        }
        return result;
    }

    /**
     * Checks if the given {@code BinaryTree<Integer>} satisfies the heap
     * ordering property according to the <= relation.
     *
     * @param t
     *            the binary tree
     * @return true if the given tree satisfies the heap ordering property;
     *         false otherwise
     * @ensures <pre>
     * satisfiesHeapOrdering = [t satisfies the heap ordering property]
     * </pre>
     */
    private static boolean satisfiesHeapOrdering(BinaryTree<Integer> t) {
        boolean result = true;
        if (t.size() > 0) {
            BinaryTree<Integer> left = t.newInstance();
            BinaryTree<Integer> right = t.newInstance();
            int root = t.disassemble(left, right);
            result = satisfiesHeapOrdering(left) && satisfiesHeapOrdering(right)
                    && result;
            if (left.size() > 0) {
                result = root <= left.root() && result;
            }
            if (right.size() > 0) {
                result = root <= right.root() && result;
            }
            t.assemble(root, left, right);
        }
        return result;
    }

    /**
     * Checks if the given {@code BinaryTree<T>} is complete.
     *
     * @param <T>
     *            type of {@code BinaryTree} entries
     * @param t
     *            the {@code BinaryTree}
     * @return true if the given tree is complete; false otherwise
     * @ensures isComplete = [t is complete]
     */
    private static <T> boolean isComplete(BinaryTree<T> t) {
        boolean result = true;
        if (t.size() > 0) {
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);
            result = (left.size() > 0 || right.size() == 0) && isComplete(left)
                    && isComplete(right) && result;
            t.assemble(root, left, right);
        }
        return result;
    }

    /**
     * Returns the largest integer in the given {@code Tree<Integer>}.
     *
     * @param t
     *            the {@code Tree<Integer>} whose largest integer to return
     * @return the largest integer in the given {@code Tree<Integer>}
     * @requires |t| > 0
     * @ensures <pre>
     * max is in labels(t)  and
     * for all i: integer where (i is in labels(t)) (i <= max)
     * </pre>
     */
    public static int max(Tree<Integer> t) {
        int max = t.root();
        Sequence<Tree<Integer>> children = t.newSequenceOfTree();
        int root = t.disassemble(children);
        for (int i = 0; i < children.length(); i++) {
            if (children.entry(i).size() > 0) {
                max = Math.max(max, max(children.entry(i)));
            }
        }
        t.assemble(root, children);
        return max;
    }

    public static int min(Tree<Integer> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";
        int min = t.root();
        Sequence<Tree<Integer>> children = t.newSequenceOfTree();
        int root = t.disassemble(children);
        for (int i = 0; i < children.length(); i++) {
            if (children.entry(i).size() > 0) {
                min = Math.min(min, min(children.entry(i)));
            }
        }
        t.assemble(root, children);
        return min;
    }

    /**
     * Returns an alias to the least label in t.
     *
     * @requires |t| > 0
     * @ensures least = [the least label in t (one that is less than or equal to
     *          (<=) all other labels in t)]
     */
    private static int least(BinaryTree<Integer> t) {
        assert t != null : "Violation of: t is not null";
        int least = t.root();
        BinaryTree<Integer> left = t.newInstance();
        BinaryTree<Integer> right = t.newInstance();
        int root = t.disassemble(left, right);
        if (left.size() > 0) {
            least = Math.min(least(left), least);
        }
        if (right.size() > 0) {
            least = Math.min(least(right), least);
        }
        t.assemble(root, left, right);
        return least;
    }

    /**
     * im trying pls ik these method contracts are whack reverses all the
     * elements of a tree
     *
     * @param <T>
     *            the type of elements in the tree
     * @param t
     *            the tree of be reversed
     * @updates t
     * @requires t is not null
     * @ensures reverses the order of the children of t and reverses each child
     */
    public static <T> void reverse(Tree<T> t) {
        if (t.size() > 0) {
            int numberOfSubtrees = t.numberOfSubtrees();
            for (int i = 0; i < numberOfSubtrees / 2; i++) {
                Tree<T> subtree = t.removeSubtree(i);
                reverse(subtree);
                t.addSubtree(numberOfSubtrees - i, subtree);
            }
        }
    }

    /*
     * reverses {@code this}
     *
     * @requires this is not null
     *
     * @ensures this = compose(root, right.reverse(), left.reverse())
     */
    public <T> void reverse(BinaryTree<T> t) {
        if (t.size() > 0) {
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);
            this.reverse(left);
            this.reverse(right);
            t.assemble(root, right, left);
        }

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

        out.print("Input a tree (or just press Enter to terminate): ");
        String str = in.nextLine();
        while (str.length() > 0) {
            BinaryTree<String> t = BinaryTreeUtility.treeFromString(str);
            out.println("Tree = " + BinaryTreeUtility.treeToString(t));
            out.println("Height = " + height(t));
            out.print("  Input a label to search "
                    + "(or just press Enter to input a new tree): ");
            String label = in.nextLine();
            while (label.length() > 0) {
                if (isInTree(t, label)) {
                    out.println("    \"" + label + "\" is in the tree");
                } else {
                    out.println("    \"" + label + "\" is not in the tree");
                }
                out.print("  Input a label to search "
                        + "(or just press Enter to input a new tree): ");
                label = in.nextLine();
            }
            out.println();
            out.print("Input a tree (or just press Enter to terminate): ");
            str = in.nextLine();
        }

        in.close();
        out.close();
    }

}
