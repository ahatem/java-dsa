package algorithms.sorting;

public final class BubbleSort {
    // Space: O(1) in-place
    // Time : O(n^2) worst/average, O(n) best case
    public static <T extends Comparable<T>> void sort(T[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < array.length - 1 - i; j++) {
                // Compare adjacent elements, bubble largest to end            
                if (array[j].compareTo(array[j + 1]) > 0) {
                    Sort.swap(array, j, j + 1);
                    swapped = true;
                }
            }
            // No swaps means every adjacent pair is correctly ordered
            if (!swapped) {
                break;
            }
        }
    }
}
