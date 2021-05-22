package network.entidade;

import network.entidade.base.Comunicado;

/**
 * Classe para comunicar pedido de salvamento de um labirinto para o servidor
 */
public class PedidoSalvamento implements Comunicado {

    private final LabirintoNetworkEntity labirinto;

    /**
     * Construtor de pedido de salvamento de labirintos
     *
     * @param labirinto labirinto a ser salvado
     */
    public PedidoSalvamento(LabirintoNetworkEntity labirinto) {
        this.labirinto = labirinto;
    }

    /**
     * Método para pegar o valor o identificador do cliente
     * @return String identificador do cliente
     */
    public LabirintoNetworkEntity getLabirinto() {
        return labirinto;
    }
}
