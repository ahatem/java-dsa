package algorithms.searching;

public final class BinarySearch {
    // Space: O(1)
    // Time :Best O(1), Worst/Avg O(log n)
    public static <T extends Comparable<T>> int search(T[] array, T target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = array[mid].compareTo(target);

            // array[mid] equals the target
            if (comparison == 0) return mid;
            // array[mid] greater than the target so
            if (comparison > 0) right = mid - 1;
            // array[mid] less than the target 
            else left = mid + 1;
        }

        return -(left + 1); // As Arrays.binarySearch(...) dose
    }
}
