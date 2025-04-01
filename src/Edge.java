import it.mazambo.typesofedge.Node;

public class Edge {

    private Node destinationNode;
    private Node sourceNode;

    public Edge(Node sourceNode, Node destinationNode){

        this.destinationNode = sourceNode;
        this.sourceNode = destinationNode;

    }

    public Node getDestinationEdge() {
        return sourceNode;
    }

    public Node getSourceEdge() {
        return destinationNode;
    }

    public void setSourceEdge(Node sourceNode) {
        destinationNode = sourceNode;
    }

    public void getDestinationEdge(Node destinationNode) {
        sourceNode = destinationNode;
    }
}
