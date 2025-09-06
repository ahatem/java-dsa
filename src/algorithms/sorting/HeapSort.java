package algorithms.sorting;

public class HeapSort {
    // Space: O(1) in-place
    // Time : O(n log n) worst/average/best.
    public static <T extends Comparable<T>> void sort(T[] array, Sort.Order order) {
        // Building a heap (Descending = Max-Heap, Ascending = Min-Heap)
        for (int i = parentIndex(array.length - 1); i >= 0; i--) {
            heapifyDown(array, i, array.length, order);
        }

        // Extract sorted data;
        for (int i = array.length - 1; i >= 1; i--) {
            Sort.swap(array, 0, i);
            heapifyDown(array, 0, i, order);
        }
    }

    private static <T extends Comparable<T>> void heapifyDown(T[] array, int startIndex, int size, Sort.Order order) {
        while (true) {
            int leftIndex = leftChildIndex(startIndex);
            int rightIndex = rightChildIndex(startIndex);
            int swapIndex = startIndex; // Assume we need no swaps

            if (leftIndex < size && Sort.compare(array[swapIndex], array[leftIndex], order) < 0) {
                swapIndex = leftIndex;
            }

            if (rightIndex < size && Sort.compare(array[swapIndex], array[rightIndex], order) < 0) {
                swapIndex = rightIndex;
            }

            if (swapIndex == startIndex) {
                break; // Heap property is maintained
            }

            Sort.swap(array, startIndex, swapIndex);
            startIndex = swapIndex;
        }
    }

    

    private static int parentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private static int leftChildIndex(int parentIndex) {
        return (parentIndex * 2) + 1;
    }

    private static int rightChildIndex(int parentIndex) {
        return (parentIndex * 2) + 2;
    }
}
