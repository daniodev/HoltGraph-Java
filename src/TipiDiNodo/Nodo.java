package TipiDiNodo;

import java.util.ArrayList;
import java.util.List;

public class Nodo {

    protected short id;
    protected List<Nodo> nodiVicini;

    public Nodo(short id, List<Nodo> nodiVicini) {
            this.id = id;

        this.nodiVicini = nodiVicini;
    }

    public Nodo(short id){
        this.id = id;
        this.nodiVicini = new ArrayList<>();
    }

    public short getId(){
        return this.id;
    }

    public void aggiungiVicino(Nodo n){
        this.nodiVicini.add(n);
    }

    public void aggiungiVicini(List<Nodo> nodi){
        for(Nodo n: nodi){
            if(!this.nodiVicini.contains(n)) {
                this.nodiVicini.add(n);
            }
        }
    }

}