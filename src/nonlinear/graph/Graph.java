package nonlinear.graph;

import linear.array.DynamicArray;
import linear.linkedlist.LinkedList;
import linear.queue.LinkedQueue;
import linear.stack.LinkedStack;
import nonlinear.hash.HashMap;
import nonlinear.hash.HashSet;

public class Graph<T> {
    // we could add weight by replacing T with HashMap<T, Integer> or Node<T>
    private HashMap<T, LinkedList<T>> adjacencyList;
    private boolean isDirected;

    public Graph(boolean isDirected) {
        this.adjacencyList = new HashMap<>();
        this.isDirected = isDirected;
    }

    public boolean addVertex(T vertex) {
        if (vertex == null || adjacencyList.containsKey(vertex)) {
            return false;
        }
        adjacencyList.insert(vertex, new LinkedList<>());
        return true;
    }

    public boolean addEdge(T src, T dest) {
        if (src == null || dest == null || src.equals(dest) || !adjacencyList.containsKey(src)
                || !adjacencyList.containsKey(dest)) {
            return false;
        }

        LinkedList<T> srcEdges = adjacencyList.get(src);
        if (!srcEdges.contains(dest)) {
            srcEdges.append(dest);
        } else {
            return false;
        }

        if (!isDirected) {
            LinkedList<T> destEdges = adjacencyList.get(dest);
            if (!destEdges.contains(src)) {
                destEdges.append(src);
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean removeEdge(T src, T dest) {
        if (src == null || dest == null ||
                !adjacencyList.containsKey(src) || !adjacencyList.containsKey(dest)) {
            return false;
        }

        LinkedList<T> srcEdges = adjacencyList.get(src);
        if (srcEdges.contains(dest)) {
            srcEdges.delete(dest);
        } else {
            return false;
        }

        if (!isDirected) {
            LinkedList<T> destEdges = adjacencyList.get(dest);
            if (destEdges.contains(src)) {
                destEdges.delete(src);
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean removeVertex(T vertex) {
        if (vertex == null || !adjacencyList.containsKey(vertex)) {
            return false;
        }

        DynamicArray<T> keys = adjacencyList.keys();
        for (int i = 0; i < keys.size(); i++) {
            T v = keys.get(i);
            if (!v.equals(vertex)) {
                LinkedList<T> edges = adjacencyList.get(v);
                if (edges != null && edges.contains(vertex)) {
                    edges.delete(vertex);
                }
            }
        }

        adjacencyList.remove(vertex);
        return true;
    }

    public DynamicArray<T> getVertices() {
        return adjacencyList.keys();
    }

    public DynamicArray<T> getNeighbors(T vertex) {
        if (vertex == null || !adjacencyList.containsKey(vertex)) {
            return null;
        }

        LinkedList<T> neighbors = adjacencyList.get(vertex);
        DynamicArray<T> result = new DynamicArray<>();
        for (int i = 0; i < neighbors.size(); i++) {
            result.append(neighbors.getAt(i));
        }
        return result;
    }

    public boolean hasEdge(T src, T dest) {
        if (src == null || dest == null || !adjacencyList.containsKey(src) || !adjacencyList.containsKey(dest)) {
            return false;
        }

        LinkedList<T> srcEdges = adjacencyList.get(src);
        return srcEdges.contains(dest);
    }

    public int size() {
        return this.adjacencyList.size();
    }

    public int edgesCount() {
        int edgesCount = 0;

        DynamicArray<T> keys = adjacencyList.keys();
        for (int i = 0; i < keys.size(); i++) {
            T vertex = keys.get(i);
            edgesCount += adjacencyList.get(vertex).size();
        }
        return isDirected ? edgesCount : edgesCount / 2;
    }

    public void bfs(T start) {
        if (!adjacencyList.containsKey(start)) {
            return;
        }

        LinkedQueue<T> queue = new LinkedQueue<>();
        queue.enqueue(start);

        HashSet<T> visited = new HashSet<>();
        visited.add(start);

        while (!queue.isEmpty()) {
            T current = queue.dequeue();
            System.out.println(current);

            LinkedList<T> neighbors = adjacencyList.get(current);
            if (neighbors == null) { // this must not happen but in case it did
                continue;
            }

            T[] neighborsArray = neighbors.toArray();
            for (T neighbor : neighborsArray) {
                if (!visited.contains(neighbor)) {
                    queue.enqueue(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }

    public void dfs(T start) {
        if (!adjacencyList.containsKey(start)) {
            return;
        }

        LinkedStack<T> stack = new LinkedStack<>();
        stack.push(start);

        HashSet<T> visited = new HashSet<>();
        visited.add(start);

        while (!stack.isEmpty()) {
            T current = stack.pop();
            System.out.println(current);

            LinkedList<T> neighbors = adjacencyList.get(current);
            if (neighbors == null) { // this must not happen but in case it did
                continue;
            }

            T[] neighborsArray = neighbors.toArray();
            for (T neighbor : neighborsArray) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }

    public boolean isConnected() {
        if (adjacencyList.isEmpty()) {
            return true;
        }

        DynamicArray<T> keys = adjacencyList.keys();
        if (keys.size() == 0) {
            return true;
        }

        T startVertex = keys.get(0);

        LinkedQueue<T> queue = new LinkedQueue<>();
        queue.enqueue(startVertex);

        HashSet<T> visited = new HashSet<>();
        visited.add(startVertex);

        while (!queue.isEmpty()) {
            T current = queue.dequeue();
            DynamicArray<T> allNeighbors = new DynamicArray<>();

            // Add outgoing neighbors
            LinkedList<T> outgoingNeighbors = adjacencyList.get(current);
            if (outgoingNeighbors != null) {
                for (T neighbor : outgoingNeighbors.toArray()) {
                    allNeighbors.append(neighbor);
                }
            }

            // For directed graphs, add incoming neighbors (weak connectivity)
            if (isDirected) {
                for (int i = 0; i < keys.size(); i++) {
                    T vertex = keys.get(i);
                    LinkedList<T> vertexNeighbors = adjacencyList.get(vertex);
                    if (vertexNeighbors != null && vertexNeighbors.contains(current)) {
                        allNeighbors.append(vertex);
                    }
                }
            }

            // Process neighbors
            for (T neighbor : allNeighbors.toArray()) {
                if (!visited.contains(neighbor)) {
                    queue.enqueue(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return visited.size() == adjacencyList.size();
    }

    public DynamicArray<T> shortestPathUnweighted(T start, T target) {
        if (start == null || target == null || !adjacencyList.containsKey(start)
                || !adjacencyList.containsKey(target)) {
            return null;
        }

        LinkedQueue<T> queue = new LinkedQueue<>();
        HashSet<T> visited = new HashSet<>();
        HashMap<T, T> parentMap = new HashMap<>();

        queue.enqueue(start);
        visited.add(start);
        parentMap.insert(start, null);

        while (!queue.isEmpty()) {
            T current = queue.dequeue();
            if (current.equals(target)) {
                DynamicArray<T> path = new DynamicArray<>();
                T node = target;
                while (node != null) {
                    path.prepend(node);
                    node = parentMap.get(node);
                }
                return path;
            }

            LinkedList<T> neighbors = adjacencyList.get(current);
            if (!neighbors.isEmpty()) {
                for (T neighbor : neighbors.toArray()) {
                    if (!visited.contains(neighbor)) {
                        queue.enqueue(neighbor);
                        visited.add(neighbor);
                        parentMap.insert(neighbor, current);
                    }
                }
            }
        }

        return null;
    }

    public int connectedIslandsCount() {
        if (adjacencyList.isEmpty()) {
            return 0;
        }

        HashSet<T> visited = new HashSet<>();
        int count = 0;

        DynamicArray<T> keys = adjacencyList.keys();
        for (int i = 0; i < keys.size(); i++) {
            T vertex = keys.get(i);
            if (visited.contains(vertex)) {
                continue;
            }

            count++;
            LinkedQueue<T> queue = new LinkedQueue<>();
            queue.enqueue(vertex);
            visited.add(vertex);

            while (!queue.isEmpty()) {
                T current = queue.dequeue();
                LinkedList<T> neighbors = adjacencyList.get(current);
                if (neighbors != null) {
                    for (T neighbor : neighbors.toArray()) {
                        if (!visited.contains(neighbor)) {
                            queue.enqueue(neighbor);
                            visited.add(neighbor);
                        }
                    }
                }
            }
        }

        return count;
    }
}