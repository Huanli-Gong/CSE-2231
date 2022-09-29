import java.util.Iterator;
import java.util.NoSuchElementException;

import components.stack.Stack;
import components.stack.StackSecondary;

/**
 * {@code Stack} represented as a singly linked list, done "bare-handed", with
 * implementations of primary methods.
 *
 * <p>
 * Execution-time performance of all methods implemented in this class is O(1).
 *
 * @param <T>
 *            type of Stack entries
 * @convention <pre>
 * $this.size >= 0  and
 * if $this.size == 0 then
 *   [$this.start is null]
 * else
 *   [$this.start is not null]  and
 *   [$this.start points to the first node of a singly linked list
 *    containing $this.size nodes]  and
 *   [next in the last node of that list is null]
 * </pre>
 * @correspondence this = [data in $this.size nodes starting at $this.start]
 */
public class Stack2<T> extends StackSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Node class for singly linked list nodes.
     */
    private final class Node {

        /**
         * Data in node.
         */
        private T data;

        /**
         * Next node in singly linked list, or null.
         */
        private Node next;

    }

    /**
     * start node of singly linked list.
     */
    private Node start;

    /**
     * Number of nodes in singly linked list, i.e., size = |this|.
     */
    private int size;

    /**
     * Checks that the part of the convention repeated below holds for the
     * current representation.
     *
     * @return true if the convention holds (or if assertion checking is off);
     *         otherwise reports a violated assertion
     * @convention <pre>
     * $this.size >= 0  and
     * if $this.size == 0 then
     *   [$this.start is null]
     * else
     *   [$this.start is not null]  and
     *   [$this.start points to the first node of a singly linked list
     *    containing $this.size nodes]  and
     *   [next in the last node of that list is null]
     * </pre>
     */
    private boolean conventionHolds() {
        assert this.size >= 0 : "Violation of: $this.size >= 0";
        if (this.size == 0) {
            assert this.start == null : ""
                    + "Violation of: if $this.size == 0 then $this.start is null";
        } else {
            assert this.start != null : ""
                    + "Violation of: if $this.size > 0 then $this.start is not null";
            int count = 0;
            Node tmp = this.start;
            while ((tmp != null) && (count < this.size)) {
                count++;
                tmp = tmp.next;
            }
            assert this.size == count : "Violation of: if $this.size > 0 then "
                    + "[$this.start points to the first node of a singly "
                    + "linked list containing $this.size nodes]";
            assert tmp == null : "Violation of: if $this.size > 0 then "
                    + "[$this.start points to the first node of a singly "
                    + "linked list containing $this.size nodes] and "
                    + "[next in the last node of that list is null]";
        }
        return true;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.start = new Node();
        this.start.next = null;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Stack2() {
        this.createNewRep();
//        assert this.conventionHolds();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Stack<T> newInstance() {
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
        assert this.conventionHolds();
    }

    @Override
    public final void transferFrom(Stack<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Stack2<?> : ""
                + "Violation of: source is of dynamic type Stack2<?>";
        /*
         * This cast cannot fail since the assert above would have sstartped
         * execution in that case: source must be of dynamic type Stack2<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Stack2<T> localSource = (Stack2<T>) source;
        this.start = localSource.start;
        this.size = localSource.size;
        localSource.createNewRep();
//        assert this.conventionHolds();
//        assert localSource.conventionHolds();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void push(T x) {
        assert x != null : "Violation of: x is not null";
        Node p = new Node();
        p.data = x;
        p.next = this.start.next;
        this.start.next = p;

//        assert this.conventionHolds();
    }

    @Override
    public final T pop() {
//        assert this.size() > 0 : "Violation of: this /= <>";
        T result = this.start.data;
        this.start = this.start.next;
        this.size--;
        return result;
//        assert this.conventionHolds();
        // Fix this line to return the result after checking the convention.

    }

    @Override
    public final int length() {

//        assert this.conventionHolds();
        // Fix this line to return the result after checking the convention.
        int result = 0;
        Node q = this.start.next;
        while (q != null) {
            q = q.next;
            result++;
        }
        return result;
//        return this.size;
    }

    @Override
    public final Iterator<T> iterator() {
        return new Stack2Iterator();
    }

    /**
     * Implementation of {@code Iterator} interface for {@code Stack2}.
     */
    private final class Stack2Iterator implements Iterator<T> {

        /**
         * Current node in the linked list.
         */
        private Node current;

        /**
         * No-argument constructor.
         */
        private Stack2Iterator() {
            this.current = Stack2.this.start.next;
//            assert Stack2.this.conventionHolds();
        }

        @Override
        public boolean hasNext() {
//            assert Stack2.this.conventionHolds();
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
//            assert Stack2.this.conventionHolds();
            return x;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "remove operation not supported");
        }

    }

    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        T result = x;
        if (this.start.data.equals(x)) {
            result = this.start.data;
            this.start = this.start.next;
        } else {
            Node q = this.start;
            while (!q.next.data.equals(x)) {
                q = q.next;
            }
            result = q.next.data;
            q.next = q.next.next;
        }
        this.size--;
        return result;
    }
    /*
     * Other methods (overridden for performance reasons) ---------------------
     */
//
//    @Override
//    public final T start() {
//        assert this.size() > 0 : "Violation of: this /= <>";
//
//        // TODO - fill in body
//
//        assert this.conventionHolds();
//        // Fix this line to return the result after checking the convention.
//        return this.start.data;
//    }
//
//    @Override
//    public final T replacestart(T x) {
//        assert this.size() > 0 : "Violation of: this /= <>";
//        T start = this.start.data;
//        this.start.data = x;
//
//        assert this.conventionHolds();
//        // Fix this line to return the result after checking the convention.
//        return start;
//    }

//    public final T dequeue() {
//        Node p = this.preFront;
//        Node q = p.next;
//        T result = q.data;
//        this.preFront = q;
//        this.size--;
//        return result;
//    }
//
//    public final void enqueue(T x) {
//        Node p = new Node();
//        Node q = this.rear;
//        p.data = x;
//        p.next = null;
//        q.next = p;
//        this.rear = p;
//        this.size++;
//    }
}
