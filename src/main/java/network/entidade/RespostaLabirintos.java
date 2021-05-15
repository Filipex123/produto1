package network.entidade;

import bd.dbos.LabirintoDBO;

import java.util.List;

public class RespostaLabirintos extends Comunicado {

    private List<LabirintoDBO> labirintos;

    public RespostaLabirintos(List<LabirintoDBO> labirintos) {
        this.labirintos = labirintos;
    }

    public List<LabirintoDBO> getLabirintos() {
        return labirintos;
    }
}
