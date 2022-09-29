package components.waitingline;

/**
 * {@code WaitingLineKernel} enhanced with secondary methods.
 *
 * @param <T>
 *            type of {@code WaitingLine} entries
 * @mathdefinitions <pre>
 * IS_TOTAL_PREORDER (
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y, z: T
 *   ((r(x, y) or r(y, x))  and
 *    (if (r(x, y) and r(y, z)) then r(x, z)))
 *
 * IS_SORTED (
 *   s: string of T,
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y: T where (<x, y> is substring of s) (r(x, y))
 * </pre>
 */
public interface WaitingLine<T> extends WaitingLineKernel<T> {

    /**
     * Reports the front of {@code this}.
     *
     * @return the front entry of {@code this}
     * @aliases reference returned by {@code front}
     * @requires this /= <>
     * @ensures <front> is prefix of this
     */
    T front();

    /**
     * Replaces the front of {@code this} with {@code x}, and returns the old
     * front.
     *
     * @param x
     *            the new front entry
     * @return the old front entry
     * @aliases reference {@code x}
     * @updates this
     * @requires this /= <>
     * @ensures <pre>
     * <replaceFront> is prefix of #this  and
     * this = <x> * #this[1, |#this|)
     * </pre>
     */
    T replaceFront(T x);

    /**
     * Replaces the entry at position {@code pos} of {@code this} with the entry
     * {@code x} and returns the old entry at that position.
     *
     * @param pos
     *            the position at which to replace an entry
     * @param x
     *            the entry replacing the old one
     * @return the old entry at that position
     * @aliases reference {@code x}
     * @updates this
     * @requires 0 <= pos and pos < |this|
     * @ensures <pre>
     * <replaceEntry> = #this[pos, pos+1)  and
     * this = #this[0, pos) * <x> * #this[pos+1, |#this|)
     * </pre>
     */
    T replaceEntry(int pos, T x);

    /**
     * Concatenates ("appends") {@code l} to the end of {@code this}.
     *
     * @param l
     *            the {@code WaitingLine} to be appended to the end of
     *            {@code this}
     * @updates this
     * @clears q
     * @ensures this = #this * #q
     */
    void append(WaitingLine<T> l);

    /**
     * Removes {@code x} from this, and returns it.
     *
     * @param x
     *            the element to be removed
     * @return the element removed
     * @updates this
     * @requires x is in this
     * @ensures <pre>
     * this = #this \ {x}  and
     * remove = x
     * </pre>
     */
    T remove(T x);
}
