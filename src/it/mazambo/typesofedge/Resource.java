package it.mazambo.typesofedge;

import java.util.ArrayList;
import java.util.List;

public class Resource extends Node {

    private boolean busy = false;

    public List<Process> getPendingProcesses() {
        return pendingProcesses;
    }

    private List<Process> pendingProcesses;

    public boolean isBusy() {
        return busy;
    }

    public void setBusy() {
        this.busy = true;
    }

    public Resource(short id){
        super(id);
        pendingProcesses = new ArrayList<>();
        isAProcess = false;
    }

    public void addPendingProcess(Process p){
        pendingProcesses.add(p);
    }

}
