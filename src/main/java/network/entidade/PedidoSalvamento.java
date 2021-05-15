package network.entidade;

import bd.dbos.LabirintoDBO;
import network.entidade.Comunicado;

public class PedidoSalvamento extends Comunicado {

    private LabirintoDBO labirinto;

    public PedidoSalvamento(LabirintoDBO labirinto) {
        this.labirinto = labirinto;
    }

    public LabirintoDBO getLabirinto() {
        return labirinto;
    }
}
