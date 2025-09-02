package linear.stack;

public class LinkedStack<T> {
    private class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public LinkedStack() {
        this.head = null;
        this.size = 0;
    }

    public void push(T data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
        this.size++;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }

        T value = this.head.data;
        this.head = this.head.next;
        this.size--;
        return value;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return this.head.data;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        Node current = this.head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");

        return sb.toString();
    }
}
