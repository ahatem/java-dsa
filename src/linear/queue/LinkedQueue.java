package linear.queue;

public class LinkedQueue<T> {
    private class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    public LinkedQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public void enqueue(T data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            this.front = newNode;
            this.rear = newNode;
        } else {
            this.rear.next = newNode;
            this.rear = newNode;
        }
        this.size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T result = this.front.data;
        this.front = this.front.next;
        this.size--;

        if (isEmpty()) {
            this.rear = null;
        }
        return result;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }

        return this.front.data;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean contains(T data) {
        if (data == null || isEmpty()) {
            return false;
        }

        Node current = this.front;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }
}
