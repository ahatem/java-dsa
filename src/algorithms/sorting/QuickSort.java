package algorithms.sorting;

public final class QuickSort {
    // Space: O(log n) average, O(n) worst (recursion stack depth)
    // Time : O(n log n) average/best case, O(n^2) worst
    public static <T extends Comparable<T>> void sort(T[] array, Sort.Order order) {
        quickSort(array, 0, array.length - 1, order);
    }

    private static <T extends Comparable<T>> void quickSort(T[] array, int low, int high, Sort.Order order) {
        if (low >= high) {
            return;
        }

        int partitionIndex = partition(array, low, high, order);
        quickSort(array, low, partitionIndex, order);
        quickSort(array, partitionIndex + 1, high, order);
    }

    // Hoare partition with pivot being the first element
    private static <T extends Comparable<T>> int partition(T[] arr, int low, int high, Sort.Order order) {
        // Choose the first element as pivot
        T pivot = arr[low];
        int i = low - 1;  // Left pointer
        int j = high + 1; // Right pointer

        while (true) {
            // Move i forward until we find an element >= pivot
            do {
                i++;
            } while (Sort.compare(arr[i], pivot, order) < 0);

            // Move j backward until we find an element <= pivot
            do {
                j--;
            } while (Sort.compare(arr[j], pivot,order) > 0);

            // If pointers cross, return the partition point
            if (i >= j) {
                return j;
            }

            // Swap elements at i and j
            Sort.swap(arr, i, j);
        }
    }
}
