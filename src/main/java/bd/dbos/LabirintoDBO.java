package bd.dbos;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidade de modelo do banco referente a tabela Labirinto
 */
public class LabirintoDBO implements Cloneable, Serializable {

    private final String nome;
    private final String identificador;
    private final LocalDateTime dataCriacao;
    private final LocalDateTime dataEdicao;
    private final String conteudo;

    /**
     * Método para pegar o valor de Nome
     * @return String nome do labirinto
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Método para pegar o valor do identificador
     * @return String nome do identificador
     */
    public String getIdentificador() {
        return this.identificador;
    }

    /**
     * Método para pegar o valor da data de criação do labirinto
     * @return LocalDateTime data de criação do labirinto
     */
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    /**
     * Método para pegar o valor da data de edição do labirinto
     * @return LocalDateTime data de edição do labirinto
     */
    public LocalDateTime getDataEdicao() {
        return dataEdicao;
    }

    /**
     * Método para pegar o valor do conteúdo do labirinto
     * @return String valor do conteúdo do labirinto
     */
    public String getConteudo() {
        return this.conteudo;
    }

    /**
     * Construtor completo para retorno do banco
     *
     * @param nome nome do labirinto
     * @param identificador identificador do labirinto
     * @param dataCriacao data de criação do labirinto
     * @param dataEdicao data de edição do labirinto
     * @param conteudo conteúdo do labirinto
     */
    public LabirintoDBO(String nome, String identificador, LocalDateTime dataCriacao, LocalDateTime dataEdicao, String conteudo) {
        this.nome = nome;
        this.identificador = identificador;
        this.dataCriacao = dataCriacao;
        this.dataEdicao = dataEdicao;
        this.conteudo = conteudo;
    }

    /**
     * Construtor de cópia
     * @param modelo objeto a ser copiado
     */
    public LabirintoDBO(LabirintoDBO modelo){
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
        LabirintoDBO ret = null;

        try
        {
            ret = new LabirintoDBO(this);
        }
        catch (Exception erro)
        {
            System.err.println("Erro ao clonar [" + erro + "]");
        }

        return ret;
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

        if(!(obj instanceof LabirintoDBO)) {
            return false;
        }

        LabirintoDBO lab = (LabirintoDBO)obj;

        if(!this.nome.equals(lab.nome)
                && !this.identificador.equals(lab.identificador)
                && !this.dataCriacao.equals(((LabirintoDBO) obj).dataCriacao)
                && !this.dataEdicao.equals(((LabirintoDBO) obj).dataEdicao)
                && !this.conteudo.equals(lab.conteudo)){
            return false;
        }
        return true;
    }
}
