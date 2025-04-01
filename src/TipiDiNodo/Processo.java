package TipiDiNodo;

import java.util.List;

public class Processo extends Nodo{
    public Processo(short id, List<Nodo> nodiVicini) {
        super(id, nodiVicini);
    }

    public Processo(short id){
        super(id);
    }
}
