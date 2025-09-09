package algorithms.searching;

public final class JumpSearch {
    // Space: O(1)
    // Time: Best O(1), Average/Worst O(√n)
    // NOTE: This implementation requires the input array to be sorted in ascending order.
    public static <T extends Comparable<T>> int search(T[] array, T target) {
        if (array == null || array.length == 0) {
            return -1;
        }

        int step = (int) Math.floor(Math.sqrt(array.length));
        int prev = 0;

        // Jump until we overshoot or reach the end of the array
        while (prev < array.length && array[prev].compareTo(target) < 0) {
            prev += step;
        }

        // Perform linear search in the identified block
        int start = Math.max(0, prev - step);
        int end = Math.min(prev, array.length);
        for (int i = start; i < end; i++) {
            if (array[i].compareTo(target) == 0) {
                return i;
            }
        }

        return -1;
    }
}