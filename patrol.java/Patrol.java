import java.io.*;
import java.util.*;

class Patrol {
    private List<Edge> edges = new ArrayList<>(); // Store all edges
    private int totalCost = 0; // Store total cost of the Minimum Spanning Tree

    // No-argument constructor
    public Patrol() {}

    // Reads edges from the patrol.txt file and calculates the Minimum Spanning Tree
    public void patrolEdges() {
        try {
            // Define the file path. Ensure the file is in the correct directory.
            String filePath = "patrol.txt"; // File containing the edge information
            System.out.println("Looking for file at: " + filePath);

            File file = new File(filePath);

            // Check if the file exists
            if (!file.exists()) {
                System.out.println("Error: patrol.txt not found at " + filePath + ". Please ensure the file is in the correct location.");
                return;
            }

            // Read patrol.txt file
            Scanner scanner = new Scanner(file);
            System.out.println("Reading patrol.txt...");

            // Parse each line to extract edges between planets and their respective costs
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                String planet1 = line[0];
                String planet2 = line[1];
                int cost = Integer.parseInt(line[2]);

                // Add the edge to the list
                edges.add(new Edge(planet1, planet2, cost));
            }
       git     scanner.close();

            // Calculate MST using Kruskal's Algorithm
            List<Edge> mst = kruskalMST();

            // Output the total cost and the edges in the MST
            System.out.println("Total Cost: " + totalCost);
            for (Edge edge : mst) {
                System.out.println("(" + edge.planet1 + ", " + edge.planet2 + ", " + edge.cost + ")");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: patrol.txt not found. Please ensure the file is in the correct location.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    // Kruskal's Algorithm to find the Minimum Spanning Tree
    private List<Edge> kruskalMST() {
        List<Edge> mst = new ArrayList<>();
        
        // Sort the edges by their cost in ascending order
        Collections.sort(edges, Comparator.comparingInt(e -> e.cost));
        UnionFind uf = new UnionFind();

        // Iterate through each edge and add it to the MST if it doesn't form a cycle
        for (Edge edge : edges) {
            if (uf.union(edge.planet1, edge.planet2)) { // If the edge connects two disjoint sets, include it
                mst.add(edge);
                totalCost += edge.cost;
            }
        }
        return mst;
    }

    // Edge class to represent graph edges (connections between planets)
    private static class Edge {
        String planet1, planet2; // Planets connected by this edge
        int cost; // Cost of the connection (e.g., distance or resources required)

        Edge(String planet1, String planet2, int cost) {
            this.planet1 = planet1;
            this.planet2 = planet2;
            this.cost = cost;
        }
    }

    // Union-Find class to manage disjoint sets for Kruskal's Algorithm (avoiding cycles)
    private static class UnionFind {
        private final Map<String, String> parent = new HashMap<>();

        // Find the root of a node (planet) using path compression
        public String find(String node) {
            parent.putIfAbsent(node, node); // Initialize the node's parent if not already initialized
            if (!parent.get(node).equals(node)) {
                parent.put(node, find(parent.get(node))); // Path compression
            }
            return parent.get(node);
        }

        // Union two nodes (planets) if they are not already connected (forming a cycle)
        public boolean union(String node1, String node2) {
            String root1 = find(node1);
            String root2 = find(node2);
            if (!root1.equals(root2)) {
                parent.put(root1, root2); // Union the two sets
                return true;
            }
            return false;
        }
    }

    // Main method for testing the patrol system
    public static void main(String[] args) {
        Patrol myPatrol = new Patrol();
        myPatrol.patrolEdges(); // Start the patrol process
    }
}
