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
        quickSort(array, low, partitionIndex - 1, order);
        quickSort(array, partitionIndex + 1, high, order);
    }

    // Lomuto Partition with pivot being the middle element (safer choice for better performance)
    private static <T extends Comparable<T>> int partition(T[] arr, int low, int high, Sort.Order order) {
        int middle = (low + high) / 2;
        Sort.swap(arr, middle, high);

        T pivot = arr[high];

        // 'i' will be the final partition index. All elements smaller than the pivot
        // will be moved to the left of 'i'.
        int i = low;
        for (int j = low; j < high; j++) {
            // If the current element is smaller than the pivot...
            if (Sort.compare(arr[j], pivot, order) < 0) {
                // ...move it to the "smaller" partition.
                Sort.swap(arr, i, j);
                i++;
            }
        }

        // Place the pivot in its final, correct position.
        Sort.swap(arr, i, high);

        return i;
    }
}
