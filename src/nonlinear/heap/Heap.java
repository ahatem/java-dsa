package nonlinear.heap;

public class Heap<T extends Comparable<T>> {

    private static enum Type {
        MAX, MIN
    }

    public static final Type MAX = Type.MAX;
    public static final Type MIN = Type.MIN;

    private static final int DEFAULT_CAPACITY = 4;

    private T[] data;
    private int size;
    private int capacity;
    private Type heapType;

    public static <T extends Comparable<T>> Heap<T> fromArray(Type heapType, T[] array) {
        return new Heap<>(heapType, array);
    }

    @SuppressWarnings("unchecked")
    public Heap(Type heapType, T[] array) {
        this.capacity = array.length + 1;
        this.size = array.length;
        this.heapType = heapType;
        this.data = (T[]) new Comparable[this.capacity];

        for (int i = 0; i < array.length; i++) {
            this.data[i] = array[i];
        }

        for (int i = parentIndex(this.size - 1); i >= 0; i--) {
            heapifyDown(i);
        }
    }

    @SuppressWarnings("unchecked")
    public Heap(Type heapType) {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.heapType = heapType;
        this.data = (T[]) new Comparable[this.capacity];
    }

    public void insert(T data) {
        if (data == null)
            return;

        resizeIfNeeded();

        this.data[size] = data;
        this.size++;
        heapifyUp();
    }

    public T poll() {
        if (isEmpty()) {
            return null;
        }

        T rootData = this.data[0];
        this.data[0] = this.data[this.size - 1];

        this.size--;

        heapifyDown(0);
        shrinkIfNeeded();

        return rootData;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }

        return this.data[0];
    }

    public int size() {
        return this.size;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
        this.data = (T[]) new Comparable[this.capacity];
    }

    public void printHeapTree() {
        printHeapTree(0, 0);
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    private void printHeapTree(int index, int depth) {
        if (index >= size)
            return;

        printHeapTree(rightChildIndex(index), depth + 1);
        System.out.println("    ".repeat(depth) + data[index]);
        printHeapTree(leftChildIndex(index), depth + 1);
    }

    private void heapifyUp() {
        int currentIndex = this.size - 1;

        while (currentIndex > 0) {
            int parentIdx = parentIndex(currentIndex);
            if (isHigherPriority(this.data[currentIndex], this.data[parentIdx])) {
                swap(currentIndex, parentIdx);
                currentIndex = parentIdx;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int currentIndex) {
        while (true) {
            int leftIndex = leftChildIndex(currentIndex);
            int rightIndex = rightChildIndex(currentIndex);
            int swapIndex = currentIndex;

            if (leftIndex < this.size && isHigherPriority(this.data[leftIndex], this.data[swapIndex])) {
                swapIndex = leftIndex;
            }

            if (rightIndex < this.size && isHigherPriority(this.data[rightIndex], this.data[swapIndex])) {
                swapIndex = rightIndex;
            }

            if (swapIndex == currentIndex) {
                break;
            }

            swap(currentIndex, swapIndex);
            currentIndex = swapIndex;
        }
    }

    private boolean isHigherPriority(T first, T second) {
        return (this.heapType == Type.MAX) ? first.compareTo(second) > 0 : first.compareTo(second) < 0;
    }

    private int leftChildIndex(int parentIndex) {
        return (2 * parentIndex) + 1;
    }

    private int rightChildIndex(int parentIndex) {
        return (2 * parentIndex) + 2;
    }

    private int parentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private void swap(int indexOne, int indexTwo) {
        T temp = this.data[indexOne];
        this.data[indexOne] = this.data[indexTwo];
        this.data[indexTwo] = temp;
    }

    @SuppressWarnings("unchecked")
    private void resizeIfNeeded() {
        if (this.size < this.capacity)
            return;

        int newCapacity = this.capacity * 2;
        T[] newData = (T[]) new Comparable[newCapacity];

        for (int i = 0; i < this.size; i++)
            newData[i] = this.data[i];

        this.capacity = newCapacity;
        this.data = newData;
    }

    @SuppressWarnings("unchecked")
    private void shrinkIfNeeded() {
        // if greater than 25% of the capacity or the capacity is equal to default
        if (this.size >= this.capacity / 4 || this.capacity <= DEFAULT_CAPACITY)
            return;

        int newCapacity = Math.max(this.capacity / 2, DEFAULT_CAPACITY);
        T[] newData = (T[]) new Comparable[newCapacity];

        for (int i = 0; i < this.size; i++)
            newData[i] = this.data[i];

        this.capacity = newCapacity;
        this.data = newData;
    }
}
