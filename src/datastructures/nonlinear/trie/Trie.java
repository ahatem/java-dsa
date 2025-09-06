package datastructures.nonlinear.trie;

import datastructures.linear.array.DynamicArray;
import datastructures.linear.stack.LinkedStack;
import datastructures.nonlinear.hash.HashMap;

public class Trie {
    private static class Node {
        private final HashMap<Character, Node> children;
        private boolean isWord;

        public Node() {
            this.children = new HashMap<>();
            this.isWord = false;
        }
    }

    private Node root;

    public Trie() {
        this.root = new Node();
    }

    public void insert(String word) {
        Node current = root;
        for (char c : word.toCharArray()) {
            Node next = current.children.get(c);
            if (next == null) {
                next = new Node();
                current.children.insert(c, next);
            }
            current = next;
        }
        current.isWord = true;
    }

    public void delete(String word) {
        LinkedStack<Node> parents = new LinkedStack<>();
        Node current = root;

        for (char c : word.toCharArray()) {
            Node next = current.children.get(c);
            if (next == null) {
                return; // word not exist
            }
            parents.push(current);
            current = next;
        }

        if (!current.isWord) {
            return;
        }

        current.isWord = false;

        for (int i = word.length() - 1; i >= 0; i--) {
            if (!current.children.isEmpty() || current.isWord) {
                break;
            }
            Node parent = parents.pop();
            parent.children.remove(word.charAt(i));
            current = parent;
        }
    }

    public boolean search(String word) {
        Node node = searchPrefix(word);
        return node != null && node.isWord;
    }

    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    public DynamicArray<String> collectAllWords(String prefix) {
        DynamicArray<String> words = new DynamicArray<>();
        Node node = searchPrefix(prefix);

        if (node != null) {
            collectWords(node, prefix, words);
        }

        return words;
    }

    private void collectWords(Node node, String currentWord, DynamicArray<String> words) {
        if (node.isWord) {
            words.append(currentWord);
        }

        for (char key : node.children.keys().toArray(new Character[0])) {
            Node childNode = node.children.get(key);
            collectWords(childNode, currentWord + key, words);
        }
    }

    private Node searchPrefix(String str) {
        Node current = root;
        for (char c : str.toCharArray()) {
            Node next = current.children.get(c);
            if (next == null) {
                return null;
            }
            current = next;
        }
        return current;
    }
}
