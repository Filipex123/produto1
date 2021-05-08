package bd.dbos;

import java.time.LocalDateTime;

public class Labirinto implements Cloneable {

    private String nome;
    private String identificador;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEdicao;
    private String conteudo;

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
