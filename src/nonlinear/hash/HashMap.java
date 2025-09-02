package nonlinear.hash;

import java.util.Objects;

import linear.array.DynamicArray;

public class HashMap<K, V> {
    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            if (key == null || value == null) {
                throw new IllegalArgumentException("Key and value cannot be null");
            }
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private static final int DEFAULT_SIZE = 16;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    private Node<K, V>[] buckets;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMap() {
        this.capacity = DEFAULT_SIZE;
        this.buckets = (Node<K, V>[]) new Node[DEFAULT_SIZE];
        this.size = 0;
    }

    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        return Math.floorMod(key.hashCode(), capacity);
    }

    public void insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }
        if ((double) (size + 1) / capacity > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
        int index = hash(key);
        Node<K, V> current = buckets[index];
        Node<K, V> prev = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                return;
            }
            prev = current;
            current = current.next;
        }

        Node<K, V> newNode = new Node<>(key, value);
        if (prev == null) {
            buckets[index] = newNode;
        } else {
            prev.next = newNode;
        }
        size++;
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = hash(key);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public void remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = hash(key);
        Node<K, V> current = buckets[index];
        if (current == null) {
            return;
        }
        Node<K, V> prev = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
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

    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        return get(key) != null;
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        buckets = (Node<K, V>[]) new Node[capacity];
        size = 0;
    }

    public DynamicArray<K> keys() {
        DynamicArray<K> keys = new DynamicArray<>();
        for (int i = 0; i < buckets.length; i++) {
            Node<K, V> current = buckets[i];
            while (current != null) {
                keys.append(current.key);
                current = current.next;
            }
        }
        return keys;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldBuckets = buckets;
        capacity *= 2;
        buckets = (Node<K, V>[]) new Node[capacity];
        size = 0;
        for (Node<K, V> node : oldBuckets) {
            while (node != null) {
                insert(node.key, node.value);
                node = node.next;
            }
        }
    }
}