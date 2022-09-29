import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * JUnit test fixture for {@code BinarySearchTreeMethods}'s static methods
 * isInTree (and removeSmallest).
 */
public final class BinarySearchTreeMethodsTest {

    /**
     * Constructs and return a BST created by inserting the given {@code args}
     * into an empty tree in the order in which they are provided.
     *
     * @param args
     *            the {@code String}s to be inserted in the tree
     * @return the BST with the given {@code String}s
     * @requires [the Strings in args are all distinct]
     * @ensures createBSTFromArgs = [the BST with the given Strings]
     */
    private static BinaryTree<String> createBSTFromArgs(String... args) {
        BinaryTree<String> t = new BinaryTree1<String>();
        for (String s : args) {
            BinaryTreeUtility.insertInTree(t, s);
        }
        return t;
    }

    @Test
    public void sampleTest() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    // TODO: add here other test cases for BinarySearchTreeMethods.isInTree
    @Test
    public void testIsInTreeEmpty() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs();
        BinaryTree<String> t2 = createBSTFromArgs();
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void testIsInTreeFalse() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "d");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(false, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void testIsInTreeRoot() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "b");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void testIsInTreeLeftLeaf() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void testIsInTreeRightLeaf() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "a", "c");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "c");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void testIsInTreeMiddle() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("c", "a", "b");
        BinaryTree<String> t2 = createBSTFromArgs("c", "a", "b");
        /*
         * Call method under test
         */
        boolean inTree = BinarySearchTreeMethods.isInTree(t1, "a");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(true, inTree);
        assertEquals(t2, t1);
    }

    @Test
    public void testRemoveSmallestRoot() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("a", "b", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "c");
        /*
         * Call method under test
         */
        String smallest = BinarySearchTreeMethods.removeSmallest(t1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("a", smallest);
        assertEquals(t2, t1);
    }

    @Test
    public void testRemoveSmallestLeavingEmpty() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b");
        BinaryTree<String> t2 = createBSTFromArgs();
        /*
         * Call method under test
         */
        String smallest = BinarySearchTreeMethods.removeSmallest(t1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("b", smallest);
        assertEquals(t2, t1);
    }

    @Test
    public void testRemoveSmallestLeftLeaf() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("b", "a", "c");
        BinaryTree<String> t2 = createBSTFromArgs("b", "c");
        /*
         * Call method under test
         */
        String smallest = BinarySearchTreeMethods.removeSmallest(t1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("a", smallest);
        assertEquals(t2, t1);
    }

    @Test
    public void testRemoveSmallestMiddle() {
        /*
         * Set up variables
         */
        BinaryTree<String> t1 = createBSTFromArgs("c", "a", "b");
        BinaryTree<String> t2 = createBSTFromArgs("c", "b");
        /*
         * Call method under test
         */
        String smallest = BinarySearchTreeMethods.removeSmallest(t1);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals("a", smallest);
        assertEquals(t2, t1);
    }

    @Test
    public void test() {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Input tree labels and construct BST.
         */
        out.println("Input the distinct labels for a binary search tree "
                + "in the order in which you want them inserted.");
        out.println("Press Enter on an empty line to terminate your input.");
        out.println();
        out.print("Next label: ");
        String str = in.nextLine();
        BinaryTree<String> t1 = new BinaryTree1<String>();
        BinaryTree<String> t2 = new BinaryTree1<String>();
        while (str.length() > 0) {
            BinaryTreeUtility.insertInTree(t1, str);
            BinaryTreeUtility.insertInTree(t2, str);
            out.println();
            out.println("t = " + BinaryTreeUtility.treeToString(t1));
            out.println();
            out.print("Next label: ");
            str = in.nextLine();
        }

        out.println("Largest:" + BinarySearchTreeMethods.largest(t1));
        assertEquals(t2, t1);
    }
}
