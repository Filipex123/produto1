package network.entidade;

import network.entidade.base.Comunicado;

import java.util.List;

/**
 * Classe para responder ao pedido de labirintos para o servidor
 */
public class RespostaLabirintos implements Comunicado {

    private final List<LabirintoNetworkEntity> labirintos;

    /**
     * Construtor de a resposta de pedido de labirintos
     *
     * @param labirintos labirintos retornados pelo servidor
     */
    public RespostaLabirintos(List<LabirintoNetworkEntity> labirintos) {
        this.labirintos = labirintos;
    }

    /**
     * Método para pegar os labirintos retornados
     * @return List<LabirintoDBO> lista de labirintos retornados
     */
    public List<LabirintoNetworkEntity> getLabirintos() {
        return labirintos;
    }
}
