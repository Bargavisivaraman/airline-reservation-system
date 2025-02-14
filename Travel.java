import java.util.*;

// Directed Graph Implementation for Travel
public class Travel {
    private final List<Edge> edges;
    private final Map<String, List<Edge>> adjacencyList;

    public Travel() {
        edges = new ArrayList<>();
        adjacencyList = new HashMap<>();
    }

    // Add edge to the graph
    public void addEdge(String start, String end, int weight) {
        edges.add(new Edge(start, end, weight));
        adjacencyList.putIfAbsent(start, new ArrayList<>());
        adjacencyList.get(start).add(new Edge(start, end, weight));
    }

    // Print all edges
    public void printEdges() {
        for (Edge edge : edges) {
            System.out.println(edge);
        }
    }

    // Find the quickest route using Dijkstra's Algorithm
    public void quickTravel(String start, String destination) {
        // Priority queue for shortest distance calculations
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        Map<String, Integer> distances = new HashMap<>(); // Shortest known distance to each node
        Map<String, String> previous = new HashMap<>(); // To reconstruct the path

        // Initialize distances to infinity and source distance to 0
        for (String planet : adjacencyList.keySet()) {
            distances.put(planet, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // If the destination is reached, break out of the loop
            if (current.name.equals(destination)) {
                break;
            }

            // Explore neighbors
            List<Edge> neighbors = adjacencyList.getOrDefault(current.name, new ArrayList<>());
            for (Edge edge : neighbors) {
                int newDist = current.distance + edge.weight;
                if (newDist < distances.get(edge.end)) {
                    distances.put(edge.end, newDist);
                    previous.put(edge.end, current.name);
                    queue.add(new Node(edge.end, newDist));
                }
            }
        }

        // If destination is unreachable
        if (!distances.containsKey(destination) || distances.get(destination) == Integer.MAX_VALUE) {
            System.out.println("No path exists from " + start + " to " + destination);
            return;
        }

        // Reconstruct and print the shortest path
        List<String> path = new ArrayList<>();
        String current = destination;
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);

        System.out.println("Path: " + String.join(", ", path));
        System.out.println("Total Travel Time: " + distances.get(destination));
    }

    // Helper classes
    private static class Edge {
        String start, end;
        int weight;

        Edge(String start, String end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override //overrides method
        public String toString() {
            return start + " -> " + end + " (" + weight + ")";
        }
    }

    private static class Node {
        String name;
        int distance;

        Node(String name, int distance) {
            this.name = name;
            this.distance = distance;
        }
    }
}