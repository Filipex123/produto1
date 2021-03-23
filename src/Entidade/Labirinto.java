package Entidade;

public class Labirinto {

    private Coordenada entrada;
    private Coordenada saida;
    private int altura;
    private int largura;
    private String[][] mapa;

    public Labirinto(int altura, int largura, Coordenada entrada, Coordenada saida, String data) {
        this.altura = altura;
        this.largura = largura;
        this.entrada = entrada;
        this.saida = saida;
        this.mapa = montaLabirinto(data);
    }

    private String[][] montaLabirinto(String data) {
        String[][] labirinto = new String[this.altura][this.largura];
        int cont = 0;
        for(int i = 0; i < this.altura; i++) {
            for(int j = 0; j < this.largura; j++) {
                labirinto[i][j] = data.substring(cont,cont+1);
                System.out.print(labirinto[i][j]);
                cont++;
            }
            System.out.println();
        }
        return labirinto;
    }

    public Coordenada getEntrada() {
        return entrada;
    }
    public Coordenada getSaida() {
        return saida;
    }
    public String[][] getMapa() {
        return mapa;
    }


}
