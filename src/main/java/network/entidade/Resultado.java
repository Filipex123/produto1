package network.entidade;

import bd.dbos.LabirintoDBO;

public class Resultado extends Comunicado {

    private LabirintoDBO labirintoResultante;

    public Resultado(LabirintoDBO valorResultante) {
        this.labirintoResultante = valorResultante;
    }

    public LabirintoDBO getLabirintoResultante() {
        return labirintoResultante;
    }

    public String toString ()
    {
        return (""+this.labirintoResultante);
    }

}
