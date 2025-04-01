import TipiDiNodo.Nodo;

public class Arco {

    private Nodo NodoDipartenza;
    private Nodo NodoDidestinazione;

    public Arco(Nodo nodoDipartenza, Nodo nodoDidestinazione){

        this.NodoDipartenza = nodoDipartenza;
        this.NodoDidestinazione = nodoDidestinazione;

    }

    public Nodo getNodoDidestinazione() {
        return NodoDidestinazione;
    }

    public Nodo getNodoDipartenza() {
        return NodoDipartenza;
    }

    public void setNodoDipartenza(Nodo nodoDipartenza) {
        NodoDipartenza = nodoDipartenza;
    }

    public void setNodoDidestinazione(Nodo nodoDidestinazione) {
        NodoDidestinazione = nodoDidestinazione;
    }
}
