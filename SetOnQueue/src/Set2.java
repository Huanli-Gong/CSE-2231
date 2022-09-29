import java.util.Iterator;
import java.util.NoSuchElementException;

import components.queue.Queue;
import components.sequence.Sequence;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code Queue} of elements with implementations
 * of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @convention |$this.elements| = |entries($this.elements)|
 * @correspondence this = entries($this.elements)
 */
public class Set2<T> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */

    private Sequence<T> elements;

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

    private Node start;
    private int size;

    /**
     * Finds {@code x} in {@code q} and, if such exists, moves it to the front
     * of {@code q}.
     *
     * @param <T>
     *            type of {@code Queue} entries
     * @param q
     *            the {@code Queue} to be searched
     * @param x
     *            the entry to be searched for
     * @updates q
     * @ensures <pre>
     * perms(q, #q)  and
     * if <x> is substring of q
     *  then <x> is prefix of q
     * </pre>
     */
    private static <T> void moveToFront(Queue<T> q, T x) {
        assert q != null : "Violation of: q is not null";
        if (q.length() > 1 && !q.front().equals(x)) {
            T element = q.dequeue();
            moveToFront(q, x);
            q.enqueue(element);
        }
    }

    private static <T> int indexOf(Sequence<T> s, T x) {
        int index = -1;
        int i = 0;
        while (index == -1 && i < s.length()) {
            if (s.entry(i).equals(x)) {
                index = i;
            }
            i++;
        }
        return index;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
//        this.elements = new Sequence1L<T>();
        this.start = new Node();
        this.start.next = null;

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set2() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
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
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set2<?> : ""
                + "Violation of: source is of dynamic type Set2<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set2<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set2<T> localSource = (Set2<T>) source;
        this.elements = localSource.elements;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";
//        this.elements.add(0, x);
        if (this.size == 0) {
            this.start.data = x;
        } else {
            Node p = new Node();
            p.data = x;
            p.next = this.start.next;
            this.start.next = p;
        }
        this.size++;

    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
//        assert this.contains(x) : "Violation of: x is in this";

        T result = x;
        Node q = this.start;
        if (!this.start.data.equals(x)) {
            while (!q.next.data.equals(x)) {
                q = q.next;
            }
            result = q.next.data;
            q.next = q.next.next;
        } else {
            result = this.start.data;
            this.start = this.start.next;
        }

        this.size--;
        return result;
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: |this| > 0";
        T result = this.start.data;
        this.start = this.start.next;
        this.size--;
        return result;
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";
        return false;
    }

    @Override
    public final int size() {
        return this.size;
    }

//    @Override
//    public final Iterator<T> iterator() {
//        return this.elements.iterator();
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
            this.current = Set2.this.start;
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
}
