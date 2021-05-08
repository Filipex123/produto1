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
        String ret = "{\n   Nome do Labirinto: " + this.nome
                + "\n   IP do cliente: " + this.identificador
                + "\n   Data de Criação: " + this.dataCriacao
                + "\n   Data de Edicao: " + this.dataEdicao
                + "\n   Conteúdo: " + this.conteudo
                + "\n}";
        return ret;
    }
}
