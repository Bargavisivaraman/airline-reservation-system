import java.util.*;

public class Tour {
    private final List<Edge> edges;
    private final Map<String, List<Edge>> adjacencyList;

    public Tour() {
        edges = new ArrayList<>();
        adjacencyList = new HashMap<>();
    }

    public void addEdge(String start, String end, int weight) {
        Edge edge = new Edge(start, end, weight);
        edges.add(edge);
        adjacencyList.putIfAbsent(start, new ArrayList<>());
        adjacencyList.get(start).add(edge);

        // Adding reverse edge for bidirectional travel
        Edge reverseEdge = new Edge(end, start, weight);
        adjacencyList.putIfAbsent(end, new ArrayList<>());
        adjacencyList.get(end).add(reverseEdge);
    }

    public void quickTour(String start) {
        if (!adjacencyList.containsKey(start)) {
            System.out.println("Starting planet not found in the tour.");
            return;
        }

        List<String> planets = new ArrayList<>(adjacencyList.keySet());
        List<String> bestPath = new ArrayList<>();
        int[] minCost = {Integer.MAX_VALUE};

        findBestPath(start, start, new ArrayList<>(), 0, planets.size(), bestPath, minCost, new HashSet<>());

        if (bestPath.isEmpty()) {
            System.out.println("No tour available.");
        } else {
            System.out.println("Path: " + String.join(", ", bestPath));
            System.out.println("Total Tour Time: " + minCost[0]);
        }
    }

    private void findBestPath(String current, String start, List<String> currentPath, int currentCost, int totalPlanets,
                              List<String> bestPath, int[] minCost, Set<String> visited) {
        currentPath.add(current);
        visited.add(current);

        if (currentPath.size() == totalPlanets) {
            for (Edge edge : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                if (edge.getEndNode().equals(start)) {
                    int tourCost = currentCost + edge.getWeight();
                    if (tourCost < minCost[0]) {
                        minCost[0] = tourCost;
                        bestPath.clear();
                        bestPath.addAll(currentPath);
                        bestPath.add(start);
                    }
                }
            }
        } else {
            for (Edge edge : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(edge.getEndNode())) {
                    findBestPath(edge.getEndNode(), start, currentPath, currentCost + edge.getWeight(),
                            totalPlanets, bestPath, minCost, visited);
                }
            }
        }

        currentPath.remove(currentPath.size() - 1);
        visited.remove(current);
    }

    // Define the Edge class
    public static class Edge {
        private final String start;
        private final String end;
        private final int weight;

        public Edge(String start, String end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        public String getEndNode() {
            return end;
        }

        public int getWeight() {
            return weight;
        }
    }

    public static void main(String[] args) {
        Tour tour = new Tour();
        tour.addEdge("Earth", "Mars", 5);
        tour.addEdge("Mars", "Venus", 10);
        tour.addEdge("Venus", "Earth", 8);

        tour.quickTour("Earth");
    }
}
