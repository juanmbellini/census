package ar.edu.itba.pod.census.api.hazelcast.querycollators;

import java.util.Comparator;

/**
 * Enum that holds sorting directions (i.e ascending and descending).
 */
public enum SortDirection {
    /**
     * Ascending order (i.e natural order).
     */
    ASC {
        @Override
        protected <T extends Comparable<? super T>> Comparator<T> getSortDirectionComparator() {
            return Comparator.naturalOrder();
        }
    },
    /**
     * Descending order (i.e reverse order)
     */
    DESC {
        @Override
        protected <T extends Comparable<? super T>> Comparator<T> getSortDirectionComparator() {
            return Comparator.reverseOrder();
        }
    };

    /**
     * Gets the {@link Comparator} belonging to the specific enum.
     *
     * @param <T> The type of element to be compared.
     * @return The {@link Comparator}.
     */
    protected abstract <T extends Comparable<? super T>> Comparator<T> getSortDirectionComparator();
}
