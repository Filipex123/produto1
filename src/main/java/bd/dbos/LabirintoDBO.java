package bd.dbos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LabirintoDBO implements Cloneable, Serializable {

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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataEdicao() {
        return dataEdicao;
    }

    public String getConteudo() {
        return this.conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LabirintoDBO(){}

    public LabirintoDBO(String nome, String identificador, LocalDateTime dataCriacao, LocalDateTime dataEdicao, String conteudo) {
        this.nome = nome;
        this.identificador = identificador;
        this.dataCriacao = dataCriacao;
        this.dataEdicao = dataEdicao;
        this.conteudo = conteudo;
    }

    public LabirintoDBO(String nome, String identificador, String conteudo) {
        this.nome = nome;
        this.identificador = identificador;
        this.conteudo = conteudo;
    }

    public LabirintoDBO(LabirintoDBO modelo){
        this.nome = modelo.nome;
        this.identificador = modelo.identificador;
        this.dataCriacao = modelo.dataCriacao;
        this.dataEdicao = modelo.dataEdicao;
        this.conteudo = modelo.conteudo;
    }

    @Override
    public Object clone ()
    {
        LabirintoDBO ret = null;

        try
        {
            ret = new LabirintoDBO(this);
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
