package algorithms.sorting;

public final class QuickSort {
    // Space: O(n)
    // Time : O(n log n) average/best case, O(n^2) worst
    public static <T extends Comparable<T>> void sort(T[] array, Sort.Order order) {
        quickSort(array, 0, array.length - 1, order);
    }

    private static <T extends Comparable<T>> void quickSort(T[] array, int low, int high, Sort.Order order) {
        if (low >= high) {
            return;
        }

        int partitionIndex = partition(array, low, high, order);
        quickSort(array, low, partitionIndex - 1, order);
        quickSort(array, partitionIndex + 1, high, order);
    }

    // Lomuto Partition with pivot being the first element
    public static <T extends Comparable<T>> int partition(T[] arr, int low, int high, Sort.Order order) {
        // Choose the first element as pivot
        T pivot = arr[low];

        int i = low; // Pointer for the next smaller element
        // Scan from low + 1 to high cuz the pivot at low so we need to start at index after it
        for (int j = low + 1; j <= high; j++) {
            // If current element is smaller than or equal to pivot
            if (Sort.compare(arr[j], pivot, order) <= 0) {
                i++;                  // Move the boundary of smaller elements
                Sort.swap(arr, i, j); // Swap arr[i] and arr[j]
            }
        }

        // Place pivot in its correct position
        Sort.swap(arr, low, i);

        return i; // Return the pivot's final position
    }
}
