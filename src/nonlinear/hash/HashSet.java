package nonlinear.hash;

import java.util.Objects;

import linear.array.DynamicArray;

public class HashSet<T> {
    private static class Node<T> {
        private T element;
        private Node<T> next;

        public Node(T element) {
            if (element == null) {
                throw new IllegalArgumentException("Element cannot be null");
            }
            this.element = element;
            this.next = null;
        }
    }

    private static final int DEFAULT_SIZE = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    private Node<T>[] buckets;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public HashSet() {
        this.capacity = DEFAULT_SIZE;
        this.buckets = (Node<T>[]) new Node[DEFAULT_SIZE];
        this.size = 0;
    }

    private int hash(T element) {
        if (element == null) {
            return 0;
        }
        return Math.floorMod(element.hashCode(), capacity);
    }

    public void add(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        if ((double) (size + 1) / capacity > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
        int index = hash(element);
        Node<T> current = buckets[index];
        Node<T> prev = null;

        while (current != null) {
            if (Objects.equals(current.element, element)) {
                return;
            }
            prev = current;
            current = current.next;
        }

        Node<T> newNode = new Node<>(element);
        if (prev == null) {
            buckets[index] = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    public boolean contains(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        int index = hash(element);
        Node<T> current = buckets[index];

        while (current != null) {
            if (Objects.equals(current.element, element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void remove(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        int index = hash(element);
        Node<T> current = buckets[index];
        if (current == null) {
            return;
        }
        Node<T> prev = null;

        while (current != null) {
            if (Objects.equals(current.element, element)) {
                if (prev == null) {
                    buckets[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        buckets = (Node<T>[]) new Node[capacity];
        size = 0;
    }

    public DynamicArray<T> elements() {
        DynamicArray<T> elements = new DynamicArray<>();
        for (int i = 0; i < buckets.length; i++) {
            Node<T> current = buckets[i];
            while (current != null) {
                elements.append(current.element);
                current = current.next;
            }
        }
        return elements;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<T>[] oldBuckets = buckets;
        capacity *= 2;
        buckets = (Node<T>[]) new Node[capacity];
        size = 0;
        for (Node<T> node : oldBuckets) {
            while (node != null) {
                add(node.element);
                node = node.next;
            }
        }
    }
}