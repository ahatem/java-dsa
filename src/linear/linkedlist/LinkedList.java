package linear.linkedlist;

public class LinkedList<T> {
    private static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void prepend(T value) {
        if (value == null) {
            return;
        }

        Node<T> newNode = new Node<>(value);

        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
            this.size++;
            return;
        }

        newNode.next = this.head;
        this.head = newNode;
        this.size++;
    }

    public void append(T value) {
        if (value == null) {
            return;
        }

        Node<T> newNode = new Node<>(value);

        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;
            this.size++;
            return;
        }

        this.tail.next = newNode;
        this.tail = newNode;
        this.size++;
    }

    public void insert(int index, T value) {
        if (value == null || index < 0 || index > this.size) {
            return;
        }

        if (index == 0) {
            prepend(value);
            return;
        }

        if (index == this.size) {
            append(value);
            return;
        }

        Node<T> previous = nodeAt(index - 1);
        Node<T> newNode = new Node<>(value);
        newNode.next = previous.next;
        previous.next = newNode;
        this.size++;
    }

    public void delete(T value) {
        deleteAt(indexOf(value));
    }

    public void deleteAt(int index) {
        if (isEmpty() || index < 0 || index >= this.size) {
            return;
        }

        if (index == 0) {
            popFirst();
            return;
        }

        if (index == this.size - 1) {
            popLast();
            return;
        }

        Node<T> previous = nodeAt(index - 1);
        Node<T> current = previous.next;
        previous.next = current.next;
        current.next = null; // for garbage collector
        this.size--;
    }

    public T peekFirst() {
        if (isEmpty()) {
            return null;
        }

        return this.head.value;
    }

    public T peekLast() {
        if (isEmpty()) {
            return null;
        }

        return this.tail.value;
    }

    public int indexOf(T value) {
        if (isEmpty() || value == null) {
            return -1;
        }

        Node<T> temp = this.head;
        int index = 0;
        while (temp != null) {
            if (value.equals(temp.value)) {
                return index;
            }
            temp = temp.next;
            index++;
        }

        return -1;
    }

    public T getAt(int index) {
        if (isEmpty() || index < 0 || index >= this.size) {
            return null;
        }

        return nodeAt(index).value;
    }

    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    public T popFirst() {
        if (isEmpty()) {
            return null;
        }

        Node<T> oldHead = this.head;
        this.head = this.head.next;
        oldHead.next = null; // for garbage collector
        this.size--;

        if (isEmpty()) {
            this.tail = null;
        }

        return oldHead.value;
    }

    public T popLast() {
        if (isEmpty()) {
            return null;
        }
        Node<T> oldTail = this.tail;
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
            this.size--;
            return oldTail.value;
        }
        Node<T> previous = nodeAt(this.size - 2);
        this.tail = previous;
        this.tail.next = null;
        this.size--;
        return oldTail.value;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] array = (T[]) new Object[this.size];

        Node<T> current = this.head;
        int index = 0;
        while (current != null) {
            array[index++] = current.value;
            current = current.next;
        }

        return array;
    }

    private Node<T> nodeAt(int index) {
        if (isEmpty() || index < 0 || index >= this.size) {
            return null;
        }

        Node<T> node = this.head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
