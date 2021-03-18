package Entidade;

public class Labirinto {

    private Coordenada entrada;
    private Coordenada saida;
    private int altura;
    private int largura;
    private String[][] mapa;

    public Labirinto(int altura, int largura, Coordenada entrada, Coordenada saida) {

        this.altura = altura;
        this.largura = largura;
        this.entrada = entrada;
        this.saida = saida;
        this.mapa = new String[altura][largura];
    }

}
