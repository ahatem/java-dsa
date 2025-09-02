package nonlinear.tree;

import java.util.function.Consumer;

import linear.queue.LinkedQueue;
import linear.stack.LinkedStack;

public class BinarySearchTree<T extends Comparable<T>> {
    public static enum TreeTraverse {
        PRE_ORDER, IN_ORDER, POST_ORDER, LEVEL_ORDER
    }

    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node<T> root;

    public BinarySearchTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void clear() {
        this.root = null;
    }

    public void insert(T data) {
        if (data == null) {
            return;
        }

        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            this.root = newNode;
            return;
        }

        Node<T> current = this.root;
        Node<T> parent = null;
        while (current != null) {
            parent = current;
            int comparison = data.compareTo(current.data);
            if (comparison < 0) {
                current = current.left;
            } else if (comparison > 0) {
                current = current.right;
            } else {
                return; // skip duplicates
            }
        }

        int comparison = data.compareTo(parent.data);
        if (comparison < 0) {
            parent.left = newNode;
        } else if (comparison > 0) {
            parent.right = newNode;
        }
    }

    public boolean contains(T data) {
        if (isEmpty() || data == null) {
            return false;
        }

        Node<T> current = this.root;
        while (current != null) {
            int comparison = data.compareTo(current.data);
            if (comparison < 0) {
                current = current.left;
            } else if (comparison > 0) {
                current = current.right;
            } else {
                return true;
            }
        }

        return false;
    }

    public void remove(T data) {
        if (isEmpty() || data == null) {
            return;
        }

        Node<T> current = this.root;
        Node<T> parent = null;

        // indicate if the current node is a left child for the parent
        boolean isLeftChild = false;

        // Search for the node to delete
        while (current != null && data.compareTo(current.data) != 0) {
            parent = current;
            int comparison = data.compareTo(current.data);
            if (comparison < 0) {
                current = current.left;
                isLeftChild = true;
            } else if (comparison > 0) {
                current = current.right;
                isLeftChild = false;
            }
        }

        if (current == null) {
            return; // node not exist
        }

        // Case 1: Leaf node
        if (current.left == null && current.right == null) {
            if (current == this.root) {
                this.root = null;
            } else if (isLeftChild) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        // Case 2: One child
        else if (current.left == null) { // current has only right child
            if (current == this.root) {
                this.root = current.right;
            } else if (isLeftChild) { // the current is a left child of the parent = (parent.left = current)
                parent.left = current.right;
            } else { // the current is a right child of the parent = (parent.right = current)
                parent.right = current.right;
            }
        } else if (current.right == null) { // current has only left child
            if (current == this.root) {
                this.root = current.left;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
        }
        // Case 3: Two children
        else {
            Node<T> successor = findMin(current.right);
            Node<T> successorParent = findParent(successor);

            current.data = successor.data;

            if (successorParent == current) {
                // successor is immediate right child
                // bypass successor by linking current.right to successor.right
                current.right = successor.right;
            } else {
                // successor is the minimum node in current.right
                // it has no left child, may have right child
                // update parent's left link to bypass successor
                successorParent.left = successor.right;
            }
        }
    }

    public void traverse(TreeTraverse traverseType, Consumer<T> consumer) {
        if (isEmpty() || traverseType == null || consumer == null) {
            return;
        }

        if (traverseType == TreeTraverse.LEVEL_ORDER) {
            traverseBreadthFirst(this.root, consumer);
        } else {
            traverseDepthFirst(this.root, traverseType, consumer);
        }
    }

    public T min() {
        if (isEmpty()) {
            return null;
        }

        return findMin(this.root).data;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }

        return findMax(this.root).data;
    }

    public int size() {
        int size = 0;

        LinkedQueue<Node<T>> queue = new LinkedQueue<>();
        if (!isEmpty()) {
            queue.enqueue(this.root);
        }
        while (!queue.isEmpty()) {
            Node<T> current = queue.dequeue();
            size++;

            if (current.left != null) {
                queue.enqueue(current.left);
            }
            if (current.right != null) {
                queue.enqueue(current.right);
            }
        }

        return size;
    }

    public int height() {
        if (isEmpty()) {
            return 0;
        }

        LinkedQueue<Node<T>> queue = new LinkedQueue<>();
        queue.enqueue(this.root);

        int height = 0;
        while (!queue.isEmpty()) {
            int levelNodesCount = queue.size();
            if (levelNodesCount == 0) {
                break;
            }

            height++;

            // consume all nodes at this level and add their children
            for (int i = 0; i < levelNodesCount; i++) {
                Node<T> current = queue.dequeue(); // consume each node
                if (current.left != null) { // add the left child
                    queue.enqueue(current.left);
                }
                if (current.right != null) { // add the right child
                    queue.enqueue(current.right);
                }
            }
        }

        return height;
    }

    public int countLeaves() {
        int leaves = 0;

        LinkedQueue<Node<T>> queue = new LinkedQueue<>();
        if (!isEmpty()) {
            queue.enqueue(this.root);
        }
        while (!queue.isEmpty()) {
            Node<T> current = queue.dequeue();
            if (current.left == null && current.right == null) {
                leaves++;
                continue;
            }

            if (current.left != null) {
                queue.enqueue(current.left);
            }
            if (current.right != null) {
                queue.enqueue(current.right);
            }
        }

        return leaves;
    }

    public int countInternalNodes() {
        return size() - countLeaves();
    }

    public boolean isBst() {
        return isBst(this.root, null, null);
    }
    /*
     * ==================================================
     * private methods
     * ==================================================
     */

    private boolean isBst(Node<T> node, T min, T max) {
        if (node == null) {
            return true;
        }

        if ((min != null && node.data.compareTo(min) <= 0) ||
                (max != null && node.data.compareTo(max) >= 0)) {
            return false;
        }

        return isBst(node.left, min, node.data) &&
                isBst(node.right, node.data, max);
    }

    @SuppressWarnings("unused")
    private void preOrder(Consumer<T> consumer) {
        if (isEmpty()) {
            return;
        }

        LinkedStack<Node<T>> stack = new LinkedStack<>();
        stack.push(this.root);

        while (!stack.isEmpty()) {
            Node<T> current = stack.pop();
            if (current.right != null) {
                stack.push(current.right);
            }

            consumer.accept(current.data);
            Node<T> leftChild = current.left;
            while (leftChild != null) {
                consumer.accept(leftChild.data);
                if (leftChild.right != null) {
                    stack.push(leftChild.right);
                }
                leftChild = leftChild.left;
            }
        }
    }

    @SuppressWarnings("unused")
    private void inOrder(Consumer<T> consumer) {
        if (isEmpty()) {
            return;
        }

        LinkedStack<Node<T>> stack = new LinkedStack<>();
        Node<T> current = root;

        while (current != null || !stack.isEmpty()) {

            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            consumer.accept(current.data);

            current = current.right;
        }

    }

    @SuppressWarnings("unused")
    private void postOrder(Consumer<T> consumer) {
        if (isEmpty()) {
            return;
        }

        LinkedStack<Node<T>> output = new LinkedStack<>();

        LinkedStack<Node<T>> stack = new LinkedStack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node<T> current = stack.pop();
            output.push(current);
            if (current.left != null) {
                stack.push(current.left);
            }
            if (current.right != null) {
                stack.push(current.right);
            }
        }

        while (!output.isEmpty()) {
            consumer.accept(output.pop().data);
        }
    }

    private void traverseBreadthFirst(Node<T> node, Consumer<T> consumer) {
        LinkedQueue<Node<T>> queue = new LinkedQueue<>();
        queue.enqueue(node);

        while (!queue.isEmpty()) {
            Node<T> current = queue.dequeue();
            consumer.accept(current.data);

            if (current.left != null) {
                queue.enqueue(current.left);
            }
            if (current.right != null) {
                queue.enqueue(current.right);
            }
        }
    }

    private void traverseDepthFirst(Node<T> node, TreeTraverse traverseType, Consumer<T> consumer) {
        if (node == null) {
            return;
        }
        if (traverseType == TreeTraverse.PRE_ORDER) {
            consumer.accept(node.data);
        }
        traverseDepthFirst(node.left, traverseType, consumer);
        if (traverseType == TreeTraverse.IN_ORDER) {
            consumer.accept(node.data);
        }
        traverseDepthFirst(node.right, traverseType, consumer);
        if (traverseType == TreeTraverse.POST_ORDER) {
            consumer.accept(node.data);
        }
    }

    @SuppressWarnings("unused")
    private Node<T> findSuccessor(Node<T> node) {
        if (node == null) {
            return null;
        }

        if (node.right != null) {
            return findMin(node.right);
        }

        Node<T> successor = null;
        Node<T> current = this.root;

        while (current != null) {
            int comparison = node.data.compareTo(current.data);
            if (comparison < 0) {
                successor = current; // Potential successor
                current = current.left;
            } else if (comparison > 0) {
                current = current.right;
            } else {
                break; // Found the node
            }
        }

        return successor;
    }

    @SuppressWarnings("unused")
    private Node<T> findPredecessor(Node<T> node) {
        if (node == null) {
            return null;
        }

        if (node.left != null) {
            return findMax(node.left);
        }

        Node<T> predecessor = null;
        Node<T> current = this.root;

        while (current != null) {
            int comparison = node.data.compareTo(current.data);
            if (comparison < 0) {
                current = current.left;
            } else if (comparison > 0) {
                predecessor = current;
                current = current.right;
            } else {
                break;
            }
        }
        return predecessor;
    }

    // Find the minimum node in the sub-tree of the given node.
    private Node<T> findMin(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // Find the maximum node in the sub-tree of the given node.
    private Node<T> findMax(Node<T> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // Finds the parent of a given node in the tree.
    private Node<T> findParent(Node<T> node) {
        if (node == root) {
            return null;
        }

        Node<T> current = root;
        Node<T> parent = null;

        while (current != null && current != node) {
            parent = current;
            if (node.data.compareTo(current.data) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return parent;

    }

}
