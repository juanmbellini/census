package ar.edu.itba.pod.census.api.util;

/**
 * Class representing a pair of objects.
 *
 * @param <L> The left side of the pair concrete type.
 * @param <R> The right side of the pair concrete type.
 */
public class Pair<L, R> {

    /**
     * The left element of the pair.
     */
    private final L left;

    /**
     * The right element of the pair.
     */
    private final R right;

    /**
     * @param left  The left element of the pair.
     * @param right The right element of the pair.
     */
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * @return The left element of the pair.
     */
    public L getLeft() {
        return left;
    }

    /**
     * @return The right element of the pair.
     */
    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        final Pair other = (Pair) o;
        return this.left.equals(other.getLeft()) && this.right.equals(other.getRight());
    }

    @Override
    public String toString() {
        return "(" + left.toString() + ", " + right.toString() + ")";
    }
}
