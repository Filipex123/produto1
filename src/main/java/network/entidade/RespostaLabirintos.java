package network.entidade;

import bd.dbos.LabirintoDBO;

import java.util.List;

/**
 * Classe para responder ao pedido de labirintos para o servidor
 */
public class RespostaLabirintos extends Comunicado {

    private List<LabirintoDBO> labirintos;

    /**
     * Construtor de a resposta de pedido de labirintos
     *
     * @param labirintos labirintos retornados pelo servidor
     */
    public RespostaLabirintos(List<LabirintoDBO> labirintos) {
        this.labirintos = labirintos;
    }

    /**
     * Método para pegar os labirintos retornados
     * @return List<LabirintoDBO> lista de labirintos retornados
     */
    public List<LabirintoDBO> getLabirintos() {
        return labirintos;
    }
}
