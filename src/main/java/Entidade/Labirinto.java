package Entidade;

/**
 * Entidade de labirinto com as informações dimensionais
 */
public class Labirinto {

    private Coordenada entrada;
    private Coordenada saida;
    private int altura;
    private int largura;
    private String[][] mapa;
    private Coordenada atual;

    /**
     * Construtor de labirinto
     *
     * @param altura altura
     * @param largura largura
     * @param entrada entrada
     * @param saida saida
     * @param data paredes do labirinto
     */
    public Labirinto(int altura, int largura, Coordenada entrada, Coordenada saida, String data) {
        this.altura = altura;
        this.largura = largura;
        this.entrada = entrada;
        this.atual = entrada;
        this.saida = saida;
        this.mapa = montaLabirinto(data);
    }

    private String[][] montaLabirinto(String data) {
        String[][] labirinto = new String[this.altura][this.largura];
        int cont = 0;
        for (int i = 0; i < this.altura; i++) {
            for (int j = 0; j < this.largura; j++) {
                labirinto[i][j] = data.substring(cont, cont + 1);
                System.out.print(labirinto[i][j]);
                cont++;
            }
            System.out.println();
        }
        return labirinto;
    }

    /**
     * Método get para a propriedade Altura
     *
     * @return int valor da altura
     */
    public int getAltura() {
        return this.altura;
    }

    /**
     * Método get para a propriedade Largura
     *
     * @return int valor da largura
     */
    public int getLargura() {
        return this.largura;
    }

    /**
     * Método que verifica se o cursor está na saída
     *
     * @return int valor da altura
     */
    public boolean onSaida() {
        return this.atual.validaIgualdade(this.saida);
    }

    /**
     * Método para pegar a coordenada adjacente da posição atual
     *
     * @return Pilha<Coordenada> com as coordenadas adjacentes
     */
    public Pilha<Coordenada> getAdjacentes() throws Exception {
        Pilha<Coordenada> adj = new Pilha<Coordenada>(3);
        int x = this.atual.getX(), y = this.atual.getY();

        if (y != 0 && (mapa[x][y - 1].equals(" ") || mapa[x][y - 1].equals("S"))) {
            Coordenada value = new Coordenada(x, y - 1);
            adj.guardeUmElemento(value);
        }
        if (x < altura - 1 && (mapa[x + 1][y].equals(" ") || mapa[x + 1][y].equals("S"))) {
            Coordenada value = new Coordenada(x + 1, y);
            adj.guardeUmElemento(value);
        }
        if (y < largura - 1 && (mapa[x][y + 1].equals(" ") || mapa[x][y + 1].equals("S"))) {
            Coordenada value = new Coordenada(x, y + 1);
            adj.guardeUmElemento(value);
        }
        if (x != 0 && (mapa[x - 1][y].equals(" ") || mapa[x - 1][y].equals("S"))) {
            Coordenada value = new Coordenada(x - 1, y);
            adj.guardeUmElemento(value);
        }
        return adj;
    }

    /**
     * Método para caminhar no labirinto
     *
     * @param value coordenada para onde ele tenta caminhar
     */
    public void andar(Coordenada value) {
        this.atual = value;
        int x = this.atual.getX();
        int y = this.atual.getY();

        if (mapa[x][y].equals(" ")) {
            mapa[x][y] = "*";
        }
    }

    /**
     * Método para voltar caso nao tenha adjacentes livres para andar
     *
     * @param value coordenada para onde ele tenta caminhar
     */
    public void voltar(Coordenada value) {
        int x = this.atual.getX();
        int y = this.atual.getY();
        mapa[x][y] = " ";
        this.atual = value;
    }

    /**
     * Método que imprime o labirinto
     */
    public void imprimeLabirinto() {
        for (int i = 0; i < this.altura; i++) {
            for (int j = 0; j < this.largura; j++) {
                System.out.print(mapa[i][j]);
            }
            System.out.println();
        }
    }

}
