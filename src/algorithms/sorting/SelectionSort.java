package algorithms.sorting;

public final class SelectionSort {
    // Space: O(1) in-place
    // Time : O(n^2) worst/average/best case
    public static <T extends Comparable<T>> void sort(T[] array, Sort.Order order) {
        for (int i = 0; i < array.length - 1; i++) {
            // Assume the first element of the unsorted part is the extreme one (min or max)
            int extremeElementIndex = i; 

            // Find the true extreme element in the rest of the array
            for (int j = i + 1; j < array.length; j++) {
                // If the current element is more "extreme", we have a new candidate.
                if (Sort.isOutOfOrder(array[extremeElementIndex], array[j], order)) {
                    extremeElementIndex = j;
                }
            }

            // If the extreme element is not already in its correct place, swap them.
            if (extremeElementIndex != i) {
                Sort.swap(array, extremeElementIndex, i);
            }
        }
    }
}
