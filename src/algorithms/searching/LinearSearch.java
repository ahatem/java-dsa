package algorithms.searching;

public final class LinearSearch {
    // Space: O(1)
    // Time : Best O(1), Avg, Worst O(n)
    public static <T> int search(T[] array, T target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null && target == null)
                return i;
                
            if (array[i] != null && array[i].equals(target))
                return i;
        }
        return -1;
    }
}
