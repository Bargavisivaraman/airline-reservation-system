import java.util.*;

public class Patrol {
    private List<Edge> edges; // List to store all edges
    private List<String> nodes; // List to store all unique nodes
    private Set<String> visitedNodes; // To track visited nodes
    private List<Edge> mstEdges; // To store edges in the MST

    public Patrol() {
        this.edges = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.visitedNodes = new HashSet<>();
        this.mstEdges = new ArrayList<>();
    }

    // Add undirected edge
    public void addEdge(String start, String end, int weight) {
        edges.add(new Edge(start, end, weight));
        edges.add(new Edge(end, start, weight)); // Undirected edge

        // Add nodes to the nodes list (avoid duplicates)
        if (!nodes.contains(start)) {
            nodes.add(start);
        }
        if (!nodes.contains(end)) {
            nodes.add(end);
        }
    }

    // Print all edges
    public void printEdges() {
        for (Edge edge : edges) {
            System.out.println(edge);
        }
    }

    // Prim's Algorithm to map the MST
    public void patrolEdges() {
        if (edges.isEmpty()) {
            System.out.println("No edges to process.");
            return;
        }

        // Start with any node (take the first edge’s startNode)
        String startNode = edges.get(0).getStartNode();
        visitedNodes.add(startNode);

        int totalCost = 0;

        while (visitedNodes.size() < nodes.size()) {
            Edge smallestEdge = null;

            // Finds the smallest edge that connects visited nodes to unvisited nodes
            for (Edge edge : edges) {
                if (visitedNodes.contains(edge.getStartNode()) && !visitedNodes.contains(edge.getEndNode())) {
                    if (smallestEdge == null || edge.getWeight() < smallestEdge.getWeight()) {
                        smallestEdge = edge;
                    }
                }
            }

            if (smallestEdge != null) {
                // Add the smallest edge to MST
                mstEdges.add(smallestEdge);
                visitedNodes.add(smallestEdge.getEndNode());
                totalCost += smallestEdge.getWeight();
            } else {
                break; // No valid edge found, break to avoid infinite loop
            }
        }

        // Print MST edges
        System.out.println("Total Cost: " + totalCost);
        for (Edge edge : mstEdges) {
            System.out.println("(" + edge.getStartNode() + ", " + edge.getEndNode() + ", " + edge.getWeight() + ")");
        }
    }
}