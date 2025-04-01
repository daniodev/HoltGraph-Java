import TipiDiNodo.Nodo;
import TipiDiNodo.Processo;
import TipiDiNodo.Risorsa;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static final String verde = "\u001B[32m";
    public static final String bianco = "\u001B[0m";
    public static final String rosso = "\u001B[31m";
    public static final String blu = "\u001B[34m";
    public static final String giallo = "\u001B[33m";

    public static void main(String[] args){
        Grafo grafo = new Grafo();

        while (true){

            System.out.println("Cosa desideri fare: ");
            System.out.println("1: Aggiungi un nodo.");
            System.out.println("2: Aggiungi un arco.");
            System.out.println("3: Stampa grafo fino ad ora.");
            System.out.println("4: Riduci il grafo.");
            System.out.println("5: Esci.");

            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();

            if(option == 1){
                System.out.println("Che tipo di nodo si vuole aggiungere: ");
                System.out.println("1: Processo.");
                System.out.println("2: Risorsa.");

                int optionn = scanner.nextInt();

                if(optionn == 1){
                    System.out.println("Inserire l'id del processo: ");
                    grafo.aggiungiNodo(new Processo((short) scanner.nextInt()));
                }

                if (optionn == 2){
                    System.out.println("Inserire l'id della risorsa: ");
                    grafo.aggiungiNodo(new Risorsa((short) scanner.nextInt()));
                }
            }

            if(option == 2){

                System.out.println("Inserire il processo: ");
                System.out.println("Processi disponibili: ");

                for(Nodo nodo: grafo.getNodi()){
                    if(nodo instanceof Processo) {
                        System.out.print(nodo.getId() + ", ");
                    }
                }
                System.out.println();
                Processo processo = (Processo) grafo.getNodo((short) scanner.nextInt());

                System.out.println("Inserire la risorsa: ");
                System.out.println("Risorse disponibili: ");
                for(Nodo nodo: grafo.getNodi()){
                    if(nodo instanceof Risorsa) {
                        System.out.print(nodo.getId() + ", ");
                    }
                }
                System.out.println();
                Risorsa risorsa = (Risorsa) grafo.getNodo((short) scanner.nextInt());

                if(!risorsa.isBusy()){
                    grafo.aggiungiArco(new Arco(risorsa, processo));
                    risorsa.setBusy();
                    System.out.println(verde + "Risorsa " + risorsa.getId() + " libera, arco uscente." + bianco);
                }else{
                    grafo.aggiungiArco(new Arco(processo, risorsa));
                    risorsa.aggiungiProcessoInAttesa(processo);
                    System.out.println(rosso + "Risorsa " + risorsa.getId() + " occupata, arco entrante." + bianco);
                    System.out.println(rosso + "Processo " + processo.getId() + " aggiunto in attesa di: " + risorsa.getId() + bianco);
                }

            }

            if(option == 3){

                System.out.println(verde + "Grafo fino ad ora: " + bianco);

                String startingArrow = "-->";
                String destinazionArrow = "<--";

                for (Nodo n: grafo.getNodi()) {
                    if(n instanceof Processo) {
                        System.out.print(blu + n.getId() + bianco);
                    }
                    if(n instanceof Risorsa){
                        System.out.print(giallo + n.getId() + bianco);
                    }

                    for(Arco arco: grafo.getArchi()){
                        if(arco.getNodoDidestinazione().getId() == n.getId()){
                            System.out.print(destinazionArrow);
                            if(arco.getNodoDipartenza() instanceof Processo){
                                System.out.print(blu + arco.getNodoDipartenza().getId() + " " + bianco);
                            }else{
                                System.out.print(giallo + arco.getNodoDipartenza().getId() + " " + bianco);
                            }
                        }
                        if(arco.getNodoDipartenza().getId() == n.getId()){
                            System.out.print(startingArrow);
                            if(arco.getNodoDidestinazione() instanceof Processo){
                                System.out.print(blu + arco.getNodoDidestinazione().getId() + " " + bianco);
                            }else {
                                System.out.print(giallo + arco.getNodoDidestinazione().getId() + " " + bianco);
                            }
                        }
                    }
                    System.out.println();
                }

            }

            if (option == 4) {
                for (Nodo risorsa : grafo.getNodi()) {
                    if (risorsa instanceof Risorsa) {

                        if (((Risorsa) risorsa).isBusy()) {
                            System.out.println(giallo + "Risorsa: " + risorsa.getId() + " occupata, controllo riducibilità." + bianco);

                            Processo risorsaBusyWithProcess = null;
                            boolean notInAttesaP = true;

                            // Usa un ciclo for tradizionale
                            for (int i = 0; i < grafo.getArchi().size(); i++) {
                                Arco arco = grafo.getArchi().get(i);
                                if (arco.getNodoDipartenza().equals(risorsa)) {
                                    risorsaBusyWithProcess = (Processo) arco.getNodoDidestinazione();
                                    System.out.println(giallo + "Processo occupante: " + risorsaBusyWithProcess.getId() + bianco);

                                    for (Arco a : grafo.getArchi()) {
                                        if (a.getNodoDipartenza().equals(risorsaBusyWithProcess)) {
                                            System.out.println(rosso + "Arco non riducibile, trovato processo in attesa." + bianco);
                                            notInAttesaP = false;
                                            break;
                                        }
                                    }
                                }

                                if (notInAttesaP) {
                                    System.out.println(blu + "Arco partente da: " + risorsaBusyWithProcess.getId() + " rimosso!" + bianco);
                                    grafo.getArchi().remove(i);

                                    if (!((Risorsa) risorsa).getProcessiInAttesa().isEmpty()) {
                                        System.out.println(blu + "Arco partente da: " + ((Risorsa) risorsa).getProcessiInAttesa().get(0).getId() + " invertito!" + bianco);
                                        for (Arco a : grafo.getArchi()) {
                                            if (a.getNodoDipartenza().equals(((Risorsa) risorsa).getProcessiInAttesa().get(0))) {
                                                grafo.getArchi().remove(a);
                                                grafo.getArchi().add(new Arco(risorsa, ((Risorsa) risorsa).getProcessiInAttesa().get(0)));
                                                ((Risorsa) risorsa).getProcessiInAttesa().remove(0);
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

            if(option == 5){
                System.out.println("Uscito correttamente dal programma.");
                break;
            }

        }


    }

}
