import java.util.Iterator;
import java.util.NoSuchElementException;

import components.sequence.Sequence;
import components.sequence.SequenceSecondary;
import components.stack.Stack;

/**
 * {@code Sequence} represented as a pair of {@code Stack}s with implementations
 * of primary methods.
 *
 * @param <T>
 *            type of {@code Sequence} entries
 * @correspondence this = rev($this.left) * $this.right
 */
public class Sequence3<T> extends SequenceSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Left stack.
     */
    private Stack<T> left;

    /**
     * Right stack.
     */
    private Stack<T> right;

    private final class Node {

        /**
         * Data in node, or, if this is a "smart" Node, irrelevant.
         */
        private T data;

        /**
         * Next node in doubly linked list, or, if this is a trailing "smart"
         * Node, irrelevant.
         */
        private Node next;

        /**
         * Previous node in doubly linked list, or, if this is a leading "smart"
         * Node, irrelevant.
         */
        private Node previous;

    }

    /**
     * "Smart node" before start node of doubly linked list.
     */
    private Node preStart;

    /**
     * Last node of doubly linked list in this.left.
     */
    private Node lastLeft;

    /**
     * "Smart node" after finish node of linked list.
     */
    private Node postFinish;

    /**
     * Length of this.left.
     */
    private int leftLength;

    /**
     * Length of this.right.
     */
    private int rightLength;

    /**
     * Shifts entries between {@code leftStack} and {@code rightStack}, keeping
     * reverse of the former concatenated with the latter fixed, and resulting
     * in length of the former equal to {@code newLeftLength}.
     *
     * @param <T>
     *            type of {@code Stack} entries
     * @param leftStack
     *            the left {@code Stack}
     * @param rightStack
     *            the right {@code Stack}
     * @param newLeftLength
     *            desired new length of {@code leftStack}
     * @updates leftStack, rightStack
     * @requires <pre>
     * 0 <= newLeftLength  and
     * newLeftLength <= |leftStack| + |rightStack|
     * </pre>
     * @ensures <pre>
     * rev(leftStack) * rightStack = rev(#leftStack) * #rightStack  and
     * |leftStack| = newLeftLength}
     * </pre>
     */
    private static <T> void setLengthOfLeftStack(Stack<T> leftStack,
            Stack<T> rightStack, int newLeftLength) {
        assert leftStack != null : "Violation of: rightStack is not null";
        assert leftStack != null : "Violation of: rightStack is not null";
        assert 0 <= newLeftLength : "Violation of: 0 <= newLeftLength";
        assert newLeftLength <= leftStack.length() + rightStack.length() : ""
                + "Violation of: newLeftLength <= |leftStack| + |rightStack|";
        while (leftStack.length() > newLeftLength) {
            rightStack.push(leftStack.pop());
        }
        while (leftStack.length() < newLeftLength) {
            leftStack.push(rightStack.pop());
        }
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
//        this.left = new Stack1L<T>();
//        this.right = new Stack1L<T>();

        this.preStart = new Node();
        this.lastLeft = this.preStart;
        this.postFinish = new Node();
        this.leftLength = 0;
        this.rightLength = 0;
        this.preStart.next = this.postFinish;
        this.postFinish.previous = this.preStart;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Sequence3() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Sequence<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Sequence<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Sequence3<?> : ""
                + "Violation of: source is of dynamic type Sequence3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Sequence3<?>,
         * and the ? must be T or the call would not have compiled.
         */
        Sequence3<T> localSource = (Sequence3<T>) source;
        this.left = localSource.left;
        this.right = localSource.right;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(int pos, T x) {
        assert x != null : "Violation of: x is not null";
        assert 0 <= pos : "Violation of: 0 <= pos";
        assert pos <= this.length() : "Violation of: pos <= |this|";
//        setLengthOfLeftStack(this.left, this.right, pos);
//        this.left.push(x);
        Node p = new Node();
        p.data = x;
        Node q = this.preStart;
        for (int i = 0; i < pos; i++) {
            q = q.next;
        }
        p.next = q.next;
        q.next = p;

    }

    @Override
    public final T remove(int pos) {
        assert 0 <= pos : "Violation of: 0 <= pos";
        assert pos < this.length() : "Violation of: pos < |this|";
//        setLengthOfLeftStack(this.left, this.right, pos);
//        return this.right.pop();
        Node q = this.preStart;
        for (int i = 0; i < pos; i++) {
            q = q.next;
        }
        T result = q.next.data;
        q.next = q.next.next;
        return result;
    }

    @Override
    public final int length() {
//        return this.left.length() + this.right.length();
        int result = 0;

        Node q = this.preStart.next;
        while (q.next != null) {
            q = q.next;
            result++;
        }
        return result;
    }

//    @Override
//    public final Iterator<T> iterator() {
//        setLengthOfLeftStack(this.left, this.right, 0);
//        return this.right.iterator();
//    }
    @Override
    public final Iterator<T> iterator() {
        return new List2Iterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code List2}.
     */
    private final class List2Iterator implements Iterator<T> {

        /**
         * Current node in the linked list.
         */
        private Node current;

        /**
         * No-argument constructor.
         */
        private List2Iterator() {
            this.current = Sequence3.this.preStart.next;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public T next() {
            assert this.hasNext() : "Violation of: ~this.unseen /= <>";
            if (!this.hasNext()) {
                /*
                 * Exception is supposed to be thrown in this case, but with
                 * assertion-checking enabled it cannot happen because of assert
                 * above.
                 */
                throw new NoSuchElementException();
            }
            T x = this.current.data;
            this.current = this.current.next;
            return x;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }

    }
    /*
     * Other methods (overridden for performance reasons) ---------------------
     */

    @Override
    public final void flip() {
        Stack<T> temp = this.left.newInstance();
        temp.transferFrom(this.left);
        this.left.transferFrom(this.right);
        this.right.transferFrom(temp);
    }
    /*
     * Other methods (overridden for performance reasons) ---------------------
     */

    @Override
    public final T entry(int pos) {
        assert 0 <= pos : "Violation of: 0 <= pos";
        assert pos < this.length() : "Violation of: pos < |this|";
//        setLengthOfLeftStack(this.left, this.right, pos);
//        T entry = this.right.pop();
//        this.right.push(entry);
//        return entry;
        Node q = this.preStart;
        for (int i = 0; i < pos; i++) {
            q = q.next;
        }
//        T result=q.next.data;
//        q.next=q.next.next;
        return q.next.data;
    }

    public int indexOf(T x) {
        assert x != null : "Violation of: x is not null";
        int index = 0;
        while (!this.entry(index).equals(x) && index < this.length()) {
            index++;
        }
        if (index == this.length()) {
            index = -1;
        }
        return index;
    }
}
