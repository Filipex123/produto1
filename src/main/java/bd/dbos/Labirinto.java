package bd.dbos;

import java.time.LocalDateTime;

public class Labirinto implements Cloneable {

    private String nome;
    private String identificador;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEdicao;
    private String conteudo;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataEdicao() {
        return this.dataEdicao;
    }

    public void setDataEdicao(LocalDateTime dataEdicao) {
        this.dataEdicao = dataEdicao;
    }

    public String getConteudo() {
        return this.conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    public Labirinto(){

    }
    public Labirinto (Labirinto modelo){
        this.nome = modelo.nome;
        this.identificador = modelo.identificador;
        this.dataCriacao = modelo.dataCriacao;
        this.dataEdicao = modelo.dataEdicao;
        this.conteudo = modelo.conteudo;
    }

    @Override
    public Object clone ()
    {
        Labirinto ret = null;

        try
        {
          ret = new Labirinto(this);
        }
        catch (Exception erro)
        {}

        return ret;
    }

    @Override
    public String toString(){
        return "{\n   Nome do Labirinto: " + this.nome
                + "\n   IP do cliente: " + this.identificador
                + "\n   Data de Criação: " + this.dataCriacao
                + "\n   Data de Edicao: " + this.dataEdicao
                + "\n   Conteúdo: " + this.conteudo
                + "\n}";
    }

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
}
