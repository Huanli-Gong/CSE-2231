package components.waitingline;

import components.standard.Standard;

/**
 * First-in-first-out (FIFO) queue kernel component with primary methods. (Note:
 * by package-wide convention, all references are non-null.)
 *
 * @param <T>
 *            type of {@code QueueKernel} entries
 * @mathmodel type QueueKernel is modeled by string of T
 * @initially <pre>
 * default:
 *  ensures
 *   this = <>
 * </pre>
 * @iterator ~this.seen * ~this.unseen = this
 */
public interface WaitingLineKernel<T>
        extends Standard<WaitingLine<T>>, Iterable<T> {

    /**
     * Adds {@code x} to the end of {@code this}.
     *
     * @param x
     *            the entry to be added
     * @aliases reference {@code x}
     * @updates this
     * @ensures this = #this * <x>
     */
    void add(T x);

    /**
     * Removes and returns the front element from {@code this}.
     *
     * @return the element removed from {@code this}
     * @updates this
     * @requires |this| > 0
     * @ensures <pre>
     * removeFront is in #this and
     * this = #this \ {removeFront}
     * </pre>
     */
    T removeFront();

    /**
     * Reports length of {@code this}.
     *
     * @return the length of {@code this}
     * @ensures length = |this|
     */
    int length();

    /**
     * Reports position of {@code x} in {@code this}.
     *
     * @param x
     *            the element to be checked
     * @return the position of {@code x}, or -1 if the object does not occur.
     */
    int position(T x);

}
