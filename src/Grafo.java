import TipiDiNodo.Nodo;

import java.util.ArrayList;
import java.util.List;

public class Grafo {

    private List<Nodo> nodi;
    private List<Arco> archi;

    public Grafo(){
        nodi = new ArrayList<>();
        archi = new ArrayList<>();
    }

    public void aggiungiNodo(Nodo nodo){
        for(Nodo n: nodi){
            if((nodo.getId() == n.getId())){
                System.out.println(Main.rosso+ "Errore: Nodo con ID " + nodo.getId() + " già presente." + Main.bianco);
                return;
            }
        }
        nodi.add(nodo);
        System.out.println(Main.verde + "Nodo aggiunto correttamente." + Main.bianco);
    }

    public void aggiungiArco(Arco arco){

        if(!archi.contains(arco)){
            archi.add(arco);
        }

    }

    public List<Nodo> getNodi() {
        return nodi;
    }

    public List<Arco> getArchi() {
        return archi;
    }

    public Nodo getNodo(short id){
        for(Nodo nodo: nodi){
            if(nodo.getId() == id){
                return nodo;
            }
        }
        return null;
    }

}
