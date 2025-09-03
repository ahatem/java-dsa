package algorithms.sorting;

import java.util.Arrays;

public final class MergeSort {
    // Space: O(n)
    // Time: O(n log n) => n for merging and log n for splitting
    public static <T extends Comparable<T>> void sort(T[] array, Sort.Order order) {
        if (array.length <= 1) {
            return;
        }

        T[] sortedArray = mergeSort(array, order);
        // Copy the contents of the new sorted array back into the original
        // array to meet the "in-place" contract of the void sort method.
        System.arraycopy(sortedArray, 0, array, 0, array.length);
    }

    private static <T extends Comparable<T>> T[] mergeSort(T[] array, Sort.Order order) {
        // Base Case: An array with 0 or 1 elements is already sorted.
        if (array.length <= 1) {
            return array;
        }

        // Split the array into two halves.
        int mid = array.length / 2;
        T[] leftArray = Arrays.copyOfRange(array, 0, mid);
        T[] rightArray = Arrays.copyOfRange(array, mid, array.length);

        // Recursively sort both halves.
        T[] sortedLeft = mergeSort(leftArray, order);
        T[] sortedRight = mergeSort(rightArray, order);

        // Merge the sorted halves and return the result.
        return merge(sortedLeft, sortedRight, order);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> T[] merge(T[] leftArray, T[] rightArray, Sort.Order order) {
        T[] mergedArray = (T[]) new Comparable[leftArray.length + rightArray.length];

        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = 0;

        // Compare elements from both arrays until one array is exhausted.
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            // The condition "<= 0" means "if the left element comes before or is equal to the right one".
            if (Sort.compare(leftArray[leftIndex], rightArray[rightIndex], order) <= 0) {
                mergedArray[mergedIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                mergedArray[mergedIndex] = rightArray[rightIndex];
                rightIndex++;
            }
            mergedIndex++;
        }

        // Copy any remaining elements from the left array.
        while (leftIndex < leftArray.length) {
            mergedArray[mergedIndex] = leftArray[leftIndex];
            mergedIndex++;
            leftIndex++;
        }

        // Copy any remaining elements from the right array.
        while (rightIndex < rightArray.length) {
            mergedArray[mergedIndex] = rightArray[rightIndex];
            mergedIndex++;
            rightIndex++;
        }

        return mergedArray;
    }
}