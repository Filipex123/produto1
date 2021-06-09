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

    /**
     * Método para impressão customizada de pedido de labirintos
     * @return String impressão de pedido de labirintos
     */
    @Override
    public String toString(){
        return "{\n    ID do Cliente: " + this.idCliente
                + "\n}";
    }

    /**
     * Método hashCoode de pedido de labirintos
     * @return int hash code
     */
    @Override
    public int hashCode() {

        int ret = 666;

        ret = 13*ret + this.idCliente.hashCode();

        return (ret<0)? -ret : ret;
    }


    public PedidoLabirintos(PedidoLabirintos modelo){
        this.idCliente = modelo.idCliente;
    }

    /**
     * Método para clone de labirintos
     *
     * @return Object de labirintos clonado
     */
    @Override
    public Object clone ()
    {
        PedidoLabirintos pedLab = null;

        try
        {
            pedLab = new PedidoLabirintos(this);
        }
        catch (Exception erro)
        {
            System.err.println("Erro ao clonar [" + erro + "]");
        }

        return pedLab;
    }



}
