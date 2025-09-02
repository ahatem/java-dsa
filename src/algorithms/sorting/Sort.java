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

    public static <T extends Comparable<T>> boolean shouldSwap(T firstItem, T secondItem, Order sortOrder) {
        return switch (sortOrder) {
            // swap if the first item is greater than the second
            case ASCENDING -> firstItem.compareTo(secondItem) > 0;
            // swap if the first item is less than the second
            case DESCENDING -> firstItem.compareTo(secondItem) < 0;
        };
    }
}
