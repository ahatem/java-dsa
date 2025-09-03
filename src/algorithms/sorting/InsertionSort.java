package algorithms.sorting;

public final class InsertionSort {
    // Space: O(1) in-place
    // Time : O(n^2) worst/average, O(n) best case
    public static <T extends Comparable<T>> void sort(T[] array, Sort.Order order) {
        for (int i = 1; i < array.length; i++) {
            T current = array[i];

            // Find the insertion point by shifting preceding elements that are out of order with `current`.
            int j = i - 1;
            while (j >= 0 && Sort.isOutOfOrder(array[j], current, order)) {
                array[j + 1] = array[j];
                j--;
            }

            // Insert `current` into its correct position. Note that `j` is one position
            // before the insertion point due to the final `j--` in the loop
            array[j + 1] = current;
        }
    }
}
