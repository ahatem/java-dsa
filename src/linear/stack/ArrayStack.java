package linear.stack;

public class ArrayStack<T> {
    private static final int DEFAULT_CAPACITY = 4;
    private T[] data;
    private int top;
    private int capacity;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.capacity = DEFAULT_CAPACITY;
        this.top = 0;
        this.data = (T[]) new Object[this.capacity];
    }

    public void push(T data) {
        resizeIfNeeded();
        this.data[this.top] = data;
        this.top++;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }

        this.top--;
        T result = this.data[this.top];
        this.data[this.top] = null;
        shrinkIfNeeded();
        return result;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }

        return this.data[this.top - 1];
    }

    public boolean isEmpty() {
        return this.top == 0;
    }

    public int size() {
        return this.top;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        this.capacity = DEFAULT_CAPACITY;
        this.data = (T[]) new Object[this.capacity];
        this.top = 0;
    }

    @SuppressWarnings("unchecked")
    private void resizeIfNeeded() {
        if (this.size() < this.capacity)
            return;

        int newCapacity = this.capacity * 2;
        T[] newData = (T[]) new Object[newCapacity];
        for (int i = 0; i < this.size(); i++) {
            newData[i] = this.data[i];
        }

        this.data = newData;
        this.capacity = newCapacity;
    }

    @SuppressWarnings("unchecked")
    private void shrinkIfNeeded() {
        if (this.size() >= this.capacity / 4 || this.capacity <= DEFAULT_CAPACITY)
            return;
        int newCapacity = Math.max(DEFAULT_CAPACITY, this.capacity / 2);
        T[] newData = (T[]) new Object[newCapacity];
        for (int i = 0; i < this.size(); i++) {
            newData[i] = this.data[i];
        }
        this.data = newData;
        this.capacity = newCapacity;
    }
}
