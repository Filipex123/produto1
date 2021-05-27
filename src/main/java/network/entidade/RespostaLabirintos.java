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

    /**
     * Método para impressão customizada de Resposta labirintos
     * @return String impressão de pedido Resposta labirintos
     */
    @Override
    public String toString(){
        return "{\n    Labirinto: " + this.labirintos
                + "\n}";
    }

    /**
     * Método hashCoode de pedido de Resposta labirintos
     * @return int hash code
     */
    @Override
    public int hashCode() {

        int ret = 666;

        ret = 13*ret + this.labirintos.hashCode();

        return (ret<0)? -ret : ret;
    }


    public RespostaLabirintos(RespostaLabirintos modelo){
        this.labirintos = modelo.labirintos;
    }

    /**
     * Método para clone de Resposta labirintos
     *
     * @return Object de Resposta labirintos clonado
     */
    @Override
    public Object clone ()
    {
        RespostaLabirintos respLab = null;

        try
        {
            respLab = new RespostaLabirintos(this);
        }
        catch (Exception erro)
        {
            System.err.println("Erro ao clonar [" + erro + "]");
        }

        return respLab;
    }
}
