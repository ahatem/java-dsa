package datastructures.linear.linkedlist;

public class DoublyCircularLinkedList<T> {

    private class Node {
        T data;
        Node next;
        Node prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public DoublyCircularLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void insertFront(T data) {
        Node newNode = new Node(data);

        if (head == null) {
            newNode.next = newNode;
            newNode.prev = newNode;
            this.head = newNode;
            this.tail = newNode;
            this.size++;
            return;
        }

        newNode.next = this.head;
        newNode.prev = this.tail;
        this.head.prev = newNode;
        this.tail.next = newNode;
        this.head = newNode;
        this.size++;
    }

    public void insertBack(T data) {
        Node newNode = new Node(data);

        if (head == null) {
            newNode.next = newNode;
            newNode.prev = newNode;
            this.head = newNode;
            this.tail = newNode;
            this.size++;
            return;
        }

        newNode.next = this.head;
        newNode.prev = this.tail;
        tail.next = newNode;
        head.prev = newNode;
        this.tail = newNode;
        this.size++;
    }

    public void deleteFront() {
        if (this.head == null) {
            return;
        }

        if (this.head == tail) {
            this.head = null;
            this.tail = null;
            this.size = 0;
            return;
        }

        Node oldHead = this.head;
        this.head = this.head.next;
        this.head.prev = this.tail;
        this.tail.next = this.head;

        oldHead.next = null;
        oldHead.prev = null;

        this.size--;
    }

    public void deleteBack() {
        if (this.head == null) {
            return;
        }

        if (this.head == tail) {
            this.head = null;
            this.tail = null;
            this.size = 0;
            return;
        }

        Node oldTail = this.tail;
        this.tail = this.tail.prev;
        this.tail.next = this.head;
        this.head.prev = this.tail;

        oldTail.next = null;
        oldTail.prev = null;

        this.size--;
    }

    public void displayForward() {
        if (head == null) {
            return;
        }

        Node current = this.head;
        do {
            System.out.print(current.data);
            if (current != this.tail) {
                System.out.print(" <-> ");
            }
            current = current.next;
        } while (current != this.head);
        System.out.println();
    }

    public void displayBackward() {
        if (this.tail == null) {
            return;
        }

        Node current = this.tail;
        do {
            System.out.print(current.data);
            if (current != head) {
                System.out.print(" <-> ");
            }
            current = current.prev;
        } while (current != this.tail);
        System.out.println();
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.head == null && this.tail == null && this.size == 0;
    }

    public T get(int index) {
        Node node = getNodeAt(index);
        if (node == null) {
            return null;
        }

        return node.data;
    }

    public int indexOf(T data) {
        if (isEmpty()) {
            return -1;
        }

        int index = 0;
        Node node = this.head;
        do {
            if ((data == null && node.data == null) ||
                    (data != null && data.equals(node.data))) {
                return index;
            }
            node = node.next;
            index++;
        } while (node != this.head);

        return -1;
    }

    public boolean contains(T data) {
        return indexOf(data) != -1;
    }

    public void insertAt(int index, T data) {
        if (index < 0) {
            index = size + index;
        }

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            insertFront(data);
            return;
        }

        if (index == size) {
            insertBack(data);
            return;
        }

        Node node = getNodeAt(index);
        Node newNode = new Node(data);

        newNode.next = node;
        newNode.prev = node.prev;
        node.prev.next = newNode;
        node.prev = newNode;

        size++;
    }

    public void deleteAt(int index) {
        if (isEmpty()) {
            return;
        }

        if (index < 0) {
            index = size + index;
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            deleteFront();
            return;
        }

        if (index == size - 1) {
            deleteBack();
            return;
        }

        Node node = getNodeAt(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.next = null;
        node.prev = null;

        size--;
    }

    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        if (isEmpty()) {
            return (T[]) new Object[0];
        }

        T[] array = (T[]) new Object[this.size];
        Node current = this.head;
        for (int i = 0; i < this.size; i++) {
            array[i] = current.data;
            current = current.next;
        }

        return array;
    }

    public void reverse() {
        if (isEmpty() || size == 1) {
            return;
        }

        Node current = this.head;
        Node next = null;

        do {
            next = current.next;
            current.next = current.prev;
            current.prev = next;
            current = next;
        } while (current != this.head);

        next = this.head;
        this.head = this.tail;
        this.tail = next;
    }

    public T peekFront() {
        if (isEmpty()) {
            return null;
        }

        return this.head.data;
    }

    public T peekBack() {
        if (isEmpty()) {
            return null;
        }

        return this.tail.data;
    }

    public void set(int index, T newData) {
        Node node = getNodeAt(index);
        if (node == null) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        node.data = newData;
    }

    public void remove(T data) {
        int index = indexOf(data);
        if (index == -1) {
            return;
        }
        deleteAt(index);
    }

    public void removeAll(T data) {
        int index = indexOf(data);
        while (index != -1) {
            deleteAt(index);
            index = indexOf(data);
        }
    }

    public int count(T data) {
        if (isEmpty()) {
            return 0;
        }

        int count = 0;
        Node current = this.head;
        do {
            if ((data == null && current.data == null) || (data != null && data.equals(current.data))) {
                count++;
            }
            current = current.next;
        } while (current != this.head);

        return count;
    }

    private Node getNodeAt(int index) {
        if (isEmpty()) {
            return null;
        }

        if (index < 0) {
            index = size + index;
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index <= size / 2) { // walk forward
            Node node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;
        } else { // walk backward
            Node node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    public T findMiddleFirst() {
        if (isEmpty()) {
            return null;
        }

        Node fast = head;
        Node slow = head;

        while (fast.next != head && fast.next.next != head) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow.data;
    }

    public boolean isPalindrome() {
        if (isEmpty()) {
            return false;
        }

        if (this.head == this.tail) {
            return true;
        }

        Node start = this.head;
        Node end = this.tail;

        while (start != end && start.prev != end) {
            if ((start.data == null && end.data != null) ||
                    (start.data != null && !start.data.equals(end.data))) {
                return false;
            }
            start = start.next;
            end = end.prev;
        }

        return true;
    }

    public void rotate(int k) {
        if (isEmpty() || size == 1) {
            return;
        }

        k = k % size;
        if (k < 0) {
            k = size + k;
        }

        if (k == 0) {
            return;
        }

        head = getNodeAt(k);
        tail = head.prev;
    }
}
