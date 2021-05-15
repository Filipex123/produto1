package network.entidade;

public class PedidoLabirintos extends Comunicado {

    private String idCliente;

    public PedidoLabirintos(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }
}
