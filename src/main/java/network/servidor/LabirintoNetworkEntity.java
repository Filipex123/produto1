package network.servidor;

import network.entidade.Comunicado;

import java.time.LocalDateTime;

/**
 * Entidade de labirinto para a conexão entre cliente e servidor
 */
public class LabirintoNetworkEntity extends Comunicado {

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
}