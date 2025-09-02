package datastructures.linear.queue;

public class ArrayQueue<T> {
    private static final int DEFAULT_CAPACITY = 4;

    private T[] data;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.front = 0;
        this.rear = 0;
        this.data = (T[]) new Object[this.capacity];
    }

    public void enqueue(T data) {
        resizeIfNeeded();

        this.data[this.rear] = data;
        this.rear = (this.rear + 1) % this.capacity;
        this.size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T result = this.data[this.front];
        this.data[this.front] = null;
        this.front = (this.front + 1) % this.capacity;
        this.size--;
        shrinkIfNeeded();
        return result;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }

        return this.data[this.front];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.front = 0;
        this.rear = 0;
        this.data = (T[]) new Object[this.capacity];
    }

    public int size() {
        return this.size;
    }

    @SuppressWarnings("unchecked")
    private void resizeIfNeeded() {
        if (this.size < this.capacity) {
            return;
        }

        int newCapacity = this.capacity * 2;
        T[] newData = (T[]) new Object[newCapacity];

        for (int i = 0; i < this.size; i++) {
            newData[i] = this.data[(this.front + i) % this.capacity];
        }

        this.data = newData;
        this.capacity = newCapacity;
        this.front = 0;
        this.rear = this.size;
    }

    @SuppressWarnings("unchecked")
    private void shrinkIfNeeded() {
        if (this.size > this.capacity / 4 || this.capacity <= DEFAULT_CAPACITY) {
            return;
        }

        int newCapacity = Math.max(DEFAULT_CAPACITY, this.capacity / 2);
        T[] newData = (T[]) new Object[newCapacity];
        for (int i = 0; i < this.size; i++) {
            newData[i] = this.data[(this.front + i) % this.capacity];
        }

        this.data = newData;
        this.capacity = newCapacity;
        this.front = 0;
        this.rear = this.size;
    }
}