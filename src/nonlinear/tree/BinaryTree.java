package nonlinear.tree;

import linear.queue.LinkedQueue;

public class BinaryTree<T> {
    private class Node {
        T data;
        Node left;
        Node right;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public BinaryTree() {
        this.root = null;
    }

    public void insert(T data) {
        Node newNode = new Node(data);

        if (this.root == null) {
            this.root = newNode;
            this.size++;
            return;
        }

        LinkedQueue<Node> queue = new LinkedQueue<>();
        queue.enqueue(this.root);

        while (!queue.isEmpty()) {
            Node current = queue.dequeue();
            if (current.left == null) {
                current.left = newNode;
                break;
            } else if (current.right == null) {
                current.right = newNode;
                break;
            } else {
                queue.enqueue(current.left);
                queue.enqueue(current.right);
            }
        }

        this.size++;
    }

    public void preOrder() {
        preOrderRec(this.root);
        System.out.println();
    }

    private void preOrderRec(Node root) {
        if (root == null) {
            return;
        }

        System.out.print(root.data + " ");
        preOrderRec(root.left);
        preOrderRec(root.right);
    }

    public void inOrder() {
        inOrderRec(this.root);
        System.out.println();
    }

    private void inOrderRec(Node root) {
        if (root == null) {
            return;
        }

        inOrderRec(root.left);
        System.out.print(root.data + " ");
        inOrderRec(root.right);
    }

    public void postOrder() {
        postOrderRec(this.root);
        System.out.println();
    }

    private void postOrderRec(Node root) {
        if (root == null) {
            return;
        }

        postOrderRec(root.left);
        postOrderRec(root.right);
        System.out.print(root.data + " ");
    }

    public void levelOrder() { // bfs
        if (this.root == null) {
            System.out.println();
            return;
        }

        LinkedQueue<Node> queue = new LinkedQueue<>();
        queue.enqueue(this.root);

        while (!queue.isEmpty()) {
            Node current = queue.dequeue();
            System.out.print(current.data + " ");

            if (current.left != null) {
                queue.enqueue(current.left);
            }

            if (current.right != null) {
                queue.enqueue(current.right);
            }
        }

        System.out.println();
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void clear() {
        this.root = null;
        this.size = 0;
    }

    public int height() {
        return heightRec(this.root);
    }

    private int heightRec(Node root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = heightRec(root.left);
        int rightHeight = heightRec(root.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public boolean contains(T data) {
        if (isEmpty()) {
            return false;
        }

        return containsRec(this.root, data);
    }

    private boolean containsRec(Node root, T data) {
        if (root == null) {
            return false;
        }

        if ((root.data == null && data == null)
                || (root.data != null && root.data.equals(data))) {
            return true;
        }

        return containsRec(root.left, data) || containsRec(root.right, data);
    }

    public int countNodes() {
        if (isEmpty()) {
            return 0;
        }

        LinkedQueue<Node> queue = new LinkedQueue<>();
        queue.enqueue(this.root);

        int count = 0;
        while (!queue.isEmpty()) {
            count++;
            Node current = queue.dequeue();
            if (current.left != null) {
                queue.enqueue(current.left);
            }
            if (current.right != null) {
                queue.enqueue(current.right);
            }
        }
        return count;
    }
}
