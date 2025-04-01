import it.mazambo.typesofedge.Node;
import it.mazambo.typesofedge.Process;
import it.mazambo.typesofedge.Resource;

import java.util.Scanner;

public class Main {

    public static final String GREEN = "\u001B[32m";
    public static final String WHITE = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        Graph graph = new Graph();

        while (true) {

            System.out.println("What would you like to do: ");
            System.out.println("1: Add a Node.");
            System.out.println("2: Add an edge.");
            System.out.println("3: Print the graph.");
            System.out.println("4: Reduce the graph.");
            System.out.println("5: Exit.");

            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();

            if (option == 1) {
                System.out.println("Which type of node would you like to add: ");
                System.out.println("1: Process.");
                System.out.println("2: Resource.");

                int optionn = scanner.nextInt();

                if (optionn == 1) {
                    System.out.println("Insert the ID of the process: ");
                    graph.addNode(new Process((short) scanner.nextInt()));
                }

                if (optionn == 2) {
                    System.out.println("Insert the ID of the resource: ");
                    graph.addNode(new Resource((short) scanner.nextInt()));
                }
            }

            if (option == 2) {

                System.out.println("Insert the process: ");
                System.out.println("Available processes: ");

                if (!graph.getNodi().isEmpty() || !graph.getEdges().isEmpty()) {
                    for (Node node : graph.getNodi()) {
                        if (node instanceof Process) {
                            System.out.print(node.getId() + ", ");
                        }
                    }
                    System.out.println();
                    Process process = (Process) graph.getNodo((short) scanner.nextInt());

                    System.out.println("Insert the resource: ");
                    System.out.println("Available resources: ");

                    for (Node node : graph.getNodi()) {
                        if (node instanceof Resource) {
                            System.out.print(node.getId() + ", ");
                        }
                    }

                    System.out.println();
                    Resource resource = (Resource) graph.getNodo((short) scanner.nextInt());

                    if (!resource.isBusy()) {
                        graph.addEdge(new Edge(resource, process));
                        resource.setBusy();
                        System.out.println(GREEN + "Resource " + resource.getId() + " free, outgoing edge." + WHITE);
                    } else {

                        graph.addEdge(new Edge(process, resource));
                        resource.addPendingProcess(process);
                        System.out.println(GREEN + "Resource " + resource.getId() + " occupied, incoming edge." + WHITE);
                        System.out.println(GREEN + "Process " + process.getId() + " added inside: " + resource.getId() + " wait list." + WHITE);

                    }
                } else {
                    System.out.println(RED + "Error: Number of processes and resources insufficient." + WHITE);
                }

            }

            if (option == 3) {

                System.out.println(GREEN + "Current graph: " + WHITE);

                String startingArrow = "-->";
                String destinationArrow = "<--";

                for (Node n : graph.getNodi()) {
                    if (n instanceof Process) {
                        System.out.print(BLUE + n.getId() + WHITE);
                    }
                    if (n instanceof Resource) {
                        System.out.print(YELLOW + n.getId() + WHITE);
                    }

                    for (Edge edge : graph.getEdges()) {
                        if (edge.getDestinationEdge().getId() == n.getId()) {
                            System.out.print(destinationArrow);
                            if (edge.getSourceEdge() instanceof Process) {
                                System.out.print(BLUE + edge.getSourceEdge().getId() + " " + WHITE);
                            } else {
                                System.out.print(YELLOW + edge.getSourceEdge().getId() + " " + WHITE);
                            }
                        }
                        if (edge.getSourceEdge().getId() == n.getId()) {
                            System.out.print(startingArrow);
                            if (edge.getDestinationEdge() instanceof Process) {
                                System.out.print(BLUE + edge.getDestinationEdge().getId() + " " + WHITE);
                            } else {
                                System.out.print(YELLOW + edge.getDestinationEdge().getId() + " " + WHITE);
                            }
                        }
                    }
                    System.out.println();
                }

            }

            if (option == 4) {
                for (Node resource : graph.getNodi()) {
                    if (resource instanceof Resource) {

                        if (((Resource) resource).isBusy()) {
                            System.out.println(YELLOW + "Resource: " + resource.getId() + " occupied, checking reducibility." + WHITE);

                            Process resourceBusyWithProcess = null;
                            boolean notWaitingProcess = true;

                            // Identify the occupying process
                            for (int i = 0; i < graph.getEdges().size(); i++) {
                                Edge edge = graph.getEdges().get(i);
                                //Checks for every edge to find the one with the one with the same source of the buys process.
                                if (edge.getSourceEdge().equals(resource)) {
                                    resourceBusyWithProcess = (Process) edge.getDestinationEdge();
                                    System.out.println(YELLOW + "Occupying process: " + resourceBusyWithProcess.getId() + WHITE);

                                    for (Edge a : graph.getEdges()) {
                                        //Checks if the found process is waiting for a resource.
                                        if (a.getSourceEdge().equals(resourceBusyWithProcess)) {
                                            System.out.println(RED + "Edge not reducible, waiting process found." + WHITE);
                                            notWaitingProcess = false;
                                            break;
                                        }
                                    }
                                }

                                //If the process has no waiting processes them the graph can be reduced!
                                if (notWaitingProcess && resourceBusyWithProcess != null) {
                                    System.out.println(BLUE + "Outgoing edge from: " + resourceBusyWithProcess.getId() + " removed!" + WHITE);
                                    graph.getEdges().remove(i);

                                    //Checks if the graph has waiting processes and inverts the edge (arrow).
                                    if (!((Resource) resource).getPendingProcesses().isEmpty()) {
                                        System.out.println(BLUE + "Outgoing edge from: " + ((Resource) resource).getPendingProcesses().get(0).getId() + " inverted!" + WHITE);
                                        for (Edge a : graph.getEdges()) {
                                            if (a.getSourceEdge().equals(((Resource) resource).getPendingProcesses().get(0))) {
                                                graph.getEdges().remove(a);
                                                graph.getEdges().add(new Edge(resource, ((Resource) resource).getPendingProcesses().get(0)));
                                                ((Resource) resource).getPendingProcesses().remove(0);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (option == 5) {
                System.out.println("Successfully exited the program.");
                break;
            }

        }
    }
}