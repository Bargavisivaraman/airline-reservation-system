public class Edge {
    private String startNode;
    private String endNode;
    private int weight;

    public Edge(String startNode, String endNode, int weight) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }

    // Getters
    public String getStartNode() {
        return startNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return startNode + " -> " + endNode + " : " + weight;
    }
}
