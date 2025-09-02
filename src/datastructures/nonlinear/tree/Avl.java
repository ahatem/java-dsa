package datastructures.nonlinear.tree;

import datastructures.linear.queue.LinkedQueue;
import datastructures.linear.stack.LinkedStack;

public class Avl<T extends Comparable<T>> {

    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;
        int height;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 0;
        }

        @Override
        public String toString() {
            return this.data.toString();
        }
    }

    private Node<T> root;
    private int size;

    public Avl() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void insert(T data) {
        if (data == null)
            return;
        this.root = insert(this.root, data);
    }

    public void remove(T data) {
        if (data == null)
            return;
        this.root = remove(this.root, data);
    }

    public boolean contains(T data) {
        return contains(root, data);
    }

    public int size() {
        return this.size;
    }

    public int height() {
        return height(this.root);
    }

    public void clear() {
        this.root = null;
        this.size = 0;
    }

    public void printTree() {
        printTree(this.root, 0);
    }

    private void printTree(Node<T> node, int depth) {
        if (node == null)
            return;

        printTree(node.right, depth + 1);
        System.out.println("    ".repeat(depth) + node.data);
        printTree(node.left, depth + 1);
    }

    private boolean contains(Node<T> node, T data) {
        if (node == null)
            return false;

        int comparison = data.compareTo(node.data);
        if (comparison < 0)
            return contains(node.left, data);
        else if (comparison > 0)
            return contains(node.right, data);

        return true;
    }

    private Node<T> insert(Node<T> node, T data) {
        if (node == null) {
            this.size++;
            return new Node<>(data);
        }

        int comparison = data.compareTo(node.data);
        if (comparison < 0) {
            node.left = insert(node.left, data);
        } else if (comparison > 0) {
            node.right = insert(node.right, data);
        }

        updateHeight(node);
        return balance(node);
    }

    private Node<T> remove(Node<T> node, T data) {
        if (node == null) {
            return null;
        }

        int comparison = data.compareTo(node.data);
        if (comparison < 0) {
            node.left = remove(node.left, data);
        } else if (comparison > 0) {
            node.right = remove(node.right, data);
        } else {
            this.size--;

            // Node found
            if (node.left == null && node.right == null) {
                return null;
            }
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            // Two children
            Node<T> successor = findMin(node.right);
            node.data = successor.data;
            node.right = remove(node.right, successor.data);
        }

        updateHeight(node);
        return balance(node);
    }

    private Node<T> balance(Node<T> parent) {
        int balanceFactor = getBalanceFactor(parent);

        if (balanceFactor > 1) { // left heavy
            if (getBalanceFactor(parent.left) >= 0) {
                parent = rotateRight(parent); // LL
            } else {
                parent.left = rotateLeft(parent.left); // LR
                parent = rotateRight(parent);
            }
        } else if (balanceFactor < -1) { // right heavy
            if (getBalanceFactor(parent.right) <= 0) {
                parent = rotateLeft(parent); // RR
            } else {
                parent.right = rotateRight(parent.right); // RL
                parent = rotateLeft(parent);
            }
        }

        return parent;
    }

    public void preOrder() {
        if (isEmpty())
            return;

        LinkedStack<Node<T>> stack = new LinkedStack<>();
        stack.push(this.root);

        while (!stack.isEmpty()) {
            Node<T> current = stack.pop();
            System.out.print(current.data + " ");

            if (current.right != null)
                stack.push(current.right);
            if (current.left != null)
                stack.push(current.left);
        }
        System.out.println();
    }

    public void inOrder() {
        if (isEmpty())
            return;

        LinkedStack<Node<T>> stack = new LinkedStack<>();
        Node<T> current = this.root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            System.out.print(current.data + " ");
            current = current.right;
        }
        System.out.println();
    }

    public void postOrder() {
        if (isEmpty())
            return;

        LinkedStack<Node<T>> result = new LinkedStack<>();
        LinkedStack<Node<T>> stack = new LinkedStack<>();
        stack.push(this.root);

        while (!stack.isEmpty()) {
            Node<T> current = stack.pop();
            result.push(current);

            if (current.left != null)
                stack.push(current.left);
            if (current.right != null)
                stack.push(current.right);
        }

        while (!result.isEmpty()) {
            System.out.print(result.pop().data + " ");
        }
        System.out.println();
    }

    public void levelOrder() {
        if (isEmpty()) {
            return;
        }

        LinkedQueue<Node<T>> queue = new LinkedQueue<>();
        queue.enqueue(this.root);

        while (!queue.isEmpty()) {
            Node<T> current = queue.dequeue();
            System.out.print(current.data + " ");

            if (current.left != null) {
                queue.enqueue(current.left);
            }
            if (current.right != null) {
                queue.enqueue(current.right);
            }
        }
    }

    public boolean isBalanced() {
        return isBalanced(this.root);
    }

    private boolean isBalanced(Node<T> node) {
        if (node == null) {
            return true;
        }

        int balanceFactor = height(node.left) - height(node.right);
        if (balanceFactor > 1 || balanceFactor < -1) {
            return false;
        }

        return isBalanced(node.left) && isBalanced(node.right);
    }

    private Node<T> rotateRight(Node<T> parent) {
        Node<T> leftChild = parent.left;
        Node<T> leftRight = leftChild.right;

        leftChild.right = parent;
        parent.left = leftRight;

        updateHeight(parent);
        updateHeight(leftChild);

        return leftChild;
    }

    private Node<T> rotateLeft(Node<T> parent) {
        Node<T> rightChild = parent.right;
        Node<T> rightLeft = rightChild.left;

        rightChild.left = parent;
        parent.right = rightLeft;

        updateHeight(parent);
        updateHeight(rightChild);

        return rightChild;
    }

    private void updateHeight(Node<T> node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private int getBalanceFactor(Node<T> node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private int height(Node<T> node) {
        return (node == null) ? 0 : node.height;
    }

    private Node<T> findMin(Node<T> node) {
        while (node.left != null)
            node = node.left;
        return node;
    }
}
