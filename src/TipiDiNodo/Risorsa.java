package TipiDiNodo;

import java.util.ArrayList;
import java.util.List;

public class Risorsa extends Nodo{

    private boolean busy = false;

    public List<Processo> getProcessiInAttesa() {
        return processiInAttesa;
    }

    private List<Processo> processiInAttesa;

    public boolean isBusy() {
        return busy;
    }

    public void setBusy() {
        this.busy = true;
    }

    public Risorsa(short id, List<Nodo> nodiVicini) {
        super(id, nodiVicini);
    }

    public Risorsa(short id){
        super(id);
        processiInAttesa = new ArrayList<>();
    }

    public void aggiungiProcessoInAttesa(Processo p){
        processiInAttesa.add(p);
    }

}
