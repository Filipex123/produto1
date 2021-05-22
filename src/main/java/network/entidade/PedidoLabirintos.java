package network.entidade;

import network.entidade.base.Comunicado;

/**
 * Classe para comunicar pedido de labirinto para o servidor
 */
public class PedidoLabirintos implements Comunicado {

    private final String idCliente;

    /**
     * Construtor de pedido de labirintos
     *
     * @param idCliente identificador do cliente requisitante
     */
    public PedidoLabirintos(String idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Método para pegar o valor o identificador do cliente
     * @return String identificador do cliente
     */
    public String getIdCliente() {
        return idCliente;
    }
}
