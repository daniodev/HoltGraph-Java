import it.mazambo.typesofedge.Node;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Node> nodes;
    private List<Edge> edges;

    public Graph(){
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addNode(Node nodo){
        for(Node n: nodes){
            if((nodo.getId() == n.getId())){
                System.out.println(Main.RED + "Error: Node with ID: " + nodo.getId() + " already exists." + Main.WHITE);
                return;
            }
        }
        nodes.add(nodo);
        System.out.println(Main.GREEN + "Node added correctly." + Main.WHITE);
    }

    public void addEdge(Edge edge){

        if(!edges.contains(edge)){
            edges.add(edge);
        }

    }

    public List<Node> getNodi() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Node getNodo(short id){
        for(Node nodo: nodes){
            if(nodo.getId() == id){
                return nodo;
            }
        }
        return null;
    }

}
