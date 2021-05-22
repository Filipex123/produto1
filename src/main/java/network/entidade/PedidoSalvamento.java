package network.entidade;

import network.servidor.LabirintoNetworkEntity;

/**
 * Classe para comunicar pedido de salvamento de um labirinto para o servidor
 */
public class PedidoSalvamento extends Comunicado {

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
