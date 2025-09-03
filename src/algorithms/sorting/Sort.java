package algorithms.sorting;

// Utility class for shared sorting methods
public final class Sort {
    public static enum Order {
        DESCENDING, ASCENDING
    }

    public static <T> void swap(T[] array, int firstIndex, int secondIndex) {
        T temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    /**
     * Checks if two items are in the wrong relative order according to the specified sort order.
     * For example, in ASCENDING order, this returns true if the first item is greater than the second.
     * @return true if the elements are out of order, false otherwise.
     */
    public static <T extends Comparable<T>> boolean isOutOfOrder(T firstItem, T secondItem, Order sortOrder) {
        return switch (sortOrder) {
            // In ASC order, they're out of order if the first is greater than the second.
            case ASCENDING -> firstItem.compareTo(secondItem) > 0;
            // In DESC order, they're out of order if the first is less than the second.
            case DESCENDING -> firstItem.compareTo(secondItem) < 0;
        };
    }

    public static <T extends Comparable<T>> int compare(T first, T second, Order order) {
        return switch (order) {
            case ASCENDING -> first.compareTo(second);
            case DESCENDING -> second.compareTo(first);
        };
    }

}
