package network.entidade;

import bd.dbos.LabirintoDBO;
import network.entidade.base.Comunicado;

import java.time.LocalDateTime;

/**
 * Entidade de labirinto para a conexão entre cliente e servidor
 */
public class LabirintoNetworkEntity implements Comunicado {

    private final String nome;
    private final String identificador;
    private final LocalDateTime dataCriacao;
    private final LocalDateTime dataEdicao;
    private final String conteudo;

    public LabirintoNetworkEntity(String nome, String identificador, LocalDateTime dataCriacao, LocalDateTime dataEdicao, String conteudo) {
        this.nome = nome;
        this.identificador = identificador;
        this.dataCriacao = dataCriacao;
        this.dataEdicao = dataEdicao;
        this.conteudo = conteudo;
    }

    public String getNome() {
        return nome;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getConteudo() {
        return conteudo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataEdicao() {
        return dataEdicao;
    }

    public LabirintoNetworkEntity(LabirintoNetworkEntity modelo){
        this.nome = modelo.nome;
        this.identificador = modelo.identificador;
        this.dataCriacao = modelo.dataCriacao;
        this.dataEdicao = modelo.dataEdicao;
        this.conteudo = modelo.conteudo;
    }

    /**
     * Método para clone de Labirinto
     *
     * @return Object do labirinto clonado
     */
    @Override
    public Object clone ()
    {
        LabirintoNetworkEntity labNet = null;

        try
        {
            labNet = new LabirintoNetworkEntity(this);
        }
        catch (Exception erro)
        {
            System.err.println("Erro ao clonar [" + erro + "]");
        }

        return labNet;
    }

    /**
     * Método para impressão customizada do Labirinto
     * @return String impressão do objeto Labirinto
     */
    @Override
    public String toString(){
        return "{\n   Nome do Labirinto: " + this.nome
                + "\n   IP do cliente: " + this.identificador
                + "\n   Data de Criação: " + this.dataCriacao
                + "\n   Data de Edicao: " + this.dataEdicao
                + "\n   Conteúdo: " + this.conteudo
                + "\n}";
    }

    /**
     * Método hashCoode de Labirinto
     * @return int hash code
     */
    @Override
    public int hashCode() {

        int ret = 666;

        ret = 13*ret + this.nome.hashCode();
        ret = 13*ret + this.identificador.hashCode();
        ret = 13*ret + this.dataCriacao.hashCode();
        ret = 13*ret + this.dataEdicao.hashCode();
        ret = 13*ret + this.conteudo.hashCode();

        return (ret<0)? -ret : ret;
    }

    /**
     * Método equals para o Labirinto
     * @param obj outro objeto a ser comparado
     * @return boolean do resultado
     */
    @Override
    public boolean equals(Object obj) {

        if(this == obj) {
            return true;
        }

        if(obj == null) {
            return false;
        }

        if(!(obj instanceof LabirintoNetworkEntity)) {
            return false;
        }

        LabirintoNetworkEntity labNet = (LabirintoNetworkEntity)obj;

        if(!this.nome.equals(labNet.nome)
                && !this.identificador.equals(labNet.identificador)
                && !this.dataCriacao.equals(((LabirintoNetworkEntity) obj).dataCriacao)
                && !this.dataEdicao.equals(((LabirintoNetworkEntity) obj).dataEdicao)
                && !this.conteudo.equals(labNet.conteudo)){
            return false;
        }
        return true;
    }
}