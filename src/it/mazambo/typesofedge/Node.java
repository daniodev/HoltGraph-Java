package it.mazambo.typesofedge;

public class Node {

    public boolean isAProcess;
    protected short id;

    public Node(short id){
        this.id = id;
    }

    public short getId(){
        return this.id;
    }

}