package network.entidade;

import bd.dbos.LabirintoDBO;

/**
 * Classe para comunicar pedido de salvamento de um labirinto para o servidor
 */
public class PedidoSalvamento extends Comunicado {

    private LabirintoDBO labirinto;

    /**
     * Construtor de pedido de salvamento de labirintos
     *
     * @param labirinto labirinto a ser salvado
     */
    public PedidoSalvamento(LabirintoDBO labirinto) {
        this.labirinto = labirinto;
    }

    /**
     * Método para pegar o valor o identificador do cliente
     * @return String identificador do cliente
     */
    public LabirintoDBO getLabirinto() {
        return labirinto;
    }
}
