package datastructures.linear.array;

import java.util.Arrays;

public class DynamicArray<T> {
    private static final int DEFAULT_CAPACITY = 4;

    private T[] data;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public DynamicArray() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.data = (T[]) new Object[this.capacity];
    }

    public void append(T data) {
        insert(this.size, data);
    }

    public void prepend(T data) {
        insert(0, data);
    }

    public T get(int index) {
        validateIndex(index);
        return this.data[index];
    }

    public void set(int index, T data) {
        validateIndex(index);
        this.data[index] = data;
    }

    public void insert(int index, T data) {
        if (index < 0 || index > this.size)
            throw new IllegalArgumentException("Index out of bounds: " + index);
        resizeIfNeeded();
        // REMEMBER: 'size' points to the next available slot for a new element
        // When we set i = size, we're starting from this new slot
        // The loop shifts elements one position to the right to make space at 'index'
        // Each element at position i-1 moves to position i
        for (int i = this.size; i > index; i--) {
            this.data[i] = this.data[i - 1];
        }
        this.data[index] = data;
        this.size++;
    }

    public void removeAt(int index) {
        validateIndex(index);
        for (int i = index; i < this.size - 1; i++) {
            this.data[i] = this.data[i + 1];
        }
        this.data[this.size - 1] = null;
        this.size--;
        shrinkIfNeeded();
    }

    public T pop() {
        if (isEmpty())
            throw new IllegalStateException("Array is empty");
        T data = this.data[this.size - 1];
        this.data[this.size - 1] = null;
        this.size--;
        shrinkIfNeeded();
        return data;
    }

    public int size() {
        return this.size;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        @SuppressWarnings("unchecked")
        T[] newData = (T[]) new Object[DEFAULT_CAPACITY];
        this.data = newData;
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
    }

    public int indexOf(T data) {
        if (isEmpty()) {
            return -1;
        }

        for (int i = 0; i < this.size; i++) {
            if ((data == null && this.data[i] == null) || (data != null && data.equals(this.data[i]))) {
                return i;
            }
        }

        return -1;
    }

    public int lastIndexOf(T data) {
        if (isEmpty()) {
            return -1;
        }

        for (int i = this.size - 1; i >= 0; i--) {
            if ((data == null && this.data[i] == null) ||
                    (data != null && data.equals(this.data[i]))) {
                return i;
            }
        }

        return -1;
    }

    public boolean contains(T data) {
        return indexOf(data) != -1;
    }

    public void remove(T data) {
        int index = indexOf(data);
        if (index == -1)
            return;
        removeAt(index);
    }

    public void reverseArray() {
        for (int i = 0; i < size / 2; i++) {
            T temp = this.data[i];
            this.data[i] = this.data[this.size - 1 - i];
            this.data[this.size - 1 - i] = temp;
        }
    }

    public void removeAll(T data) {
        int index = indexOf(data);
        while (index != -1) {
            removeAt(index);
            index = indexOf(data);
        }
    }

    public int count(T data) {
        int count = 0;
        for (int i = 0; i < this.size; i++) {
            if ((data == null && this.data[i] == null) ||
                    (data != null && data.equals(this.data[i]))) {
                count++;
            }
        }
        return count;
    }

    public T[] toArray() {
        return Arrays.copyOf(data, this.size);
    }

    @SuppressWarnings("unchecked")
    private void resizeIfNeeded() {
        if (this.size < this.capacity)
            return;
        int newCapacity = this.capacity * 2;
        T[] newData = (T[]) new Object[newCapacity];
        for (int i = 0; i < this.size; i++) {
            newData[i] = this.data[i];
        }
        this.data = newData;
        this.capacity = newCapacity;
    }

    @SuppressWarnings("unchecked")
    private void shrinkIfNeeded() {
        if (this.size >= this.capacity / 4 || this.capacity <= DEFAULT_CAPACITY)
            return;
        int newCapacity = Math.max(DEFAULT_CAPACITY, this.capacity / 2);
        T[] newData = (T[]) new Object[newCapacity];
        for (int i = 0; i < this.size; i++) {
            newData[i] = this.data[i];
        }
        this.data = newData;
        this.capacity = newCapacity;
    }

    private void validateIndex(int index) {
        if (index >= this.size || index < 0)
            throw new IllegalArgumentException("Index out of bounds: " + index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < this.size; i++) {
            sb.append(this.data[i]);
            if (i != this.size - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}