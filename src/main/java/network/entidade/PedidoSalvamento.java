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


    /**
     * Método para impressão customizada dPedido Salvemento
     * @return String impressão de Pedido Salvemento
     */
    @Override
    public String toString(){
        return "{\n    Labirinto: " + this.labirinto
                + "\n}";
    }

    /**
     * Método hashCoode de Pedido Salvemento
     * @return int hash code
     */
    @Override
    public int hashCode() {

        int ret = 666;

        ret = 13*ret + this.labirinto.hashCode();

        return (ret<0)? -ret : ret;
    }


    public PedidoSalvamento(PedidoSalvamento modelo){
        this.labirinto = modelo.labirinto;
    }

    /**
     * Método para clone de Pedido Salvemento
     *
     * @return Object de Pedido Salvemento
     */
    @Override
    public Object clone ()
    {
        PedidoSalvamento pedSalvamento = null;

        try
        {
            pedSalvamento = new PedidoSalvamento(this);
        }
        catch (Exception erro)
        {
            System.err.println("Erro ao clonar [" + erro + "]");
        }

        return pedSalvamento;
    }
}
}
