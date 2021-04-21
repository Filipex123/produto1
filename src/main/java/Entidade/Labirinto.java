package Entidade;

import org.apache.commons.lang3.StringUtils;

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
     * @param altura  altura
     * @param largura largura
     * @param entrada entrada
     * @param saida   saida
     * @param data    paredes do labirinto
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
        String[] linhas = data.split("\n");
        for (int i = 0; i < this.altura; i++) {
            for(int j = 0; j < this.largura; j++) {
                labirinto[i][j] = "" + linhas[i].charAt(j);
            }
        }
        return labirinto;
    }

    /**
     * Método que verifica se o cursor está na saída
     *
     * @return int valor da altura
     */
    private boolean onSaida() {
        return this.atual.validaIgualdade(this.saida);
    }

    /**
     * Método para pegar a coordenada adjacente da posição atual
     *
     * @return Pilha<Coordenada> com as coordenadas adjacentes
     */
    private Pilha<Coordenada> getAdjacentes() throws Exception {
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
    private void andar(Coordenada value) {
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
    private void voltar(Coordenada value) {
        int x = this.atual.getX();
        int y = this.atual.getY();
        mapa[x][y] = " ";
        this.atual = value;
    }

    /**
     * Método que imprime o labirinto em componente
     */
    public String imprimeLabirinto() {
        StringBuilder lab = new StringBuilder();
        for (int i = 0; i < this.altura; i++) {
            for (int j = 0; j < this.largura; j++) {
                lab.append(mapa[i][j]);
            }
            lab.append("\n");
        }

        return lab.toString();
    }

    public void resolve() throws Exception {

        final int tamanhoLab = this.altura * this.largura;
        boolean progressivo = true;

        Pilha<Coordenada> caminhos = new Pilha<Coordenada>(tamanhoLab);
        Pilha<Coordenada> adjacentes = new Pilha<Coordenada>(3);
        Pilha<Pilha<Coordenada>> possibilidades = new Pilha(tamanhoLab);

        while (!this.onSaida()) {
            if (progressivo) adjacentes = this.getAdjacentes();

            if (!adjacentes.isVazia()) {
                this.andar(adjacentes.recupereUmElemento());
                caminhos.guardeUmElemento(adjacentes.recupereUmElemento());
                adjacentes.retireUmElemento();
                possibilidades.guardeUmElemento(adjacentes);
                progressivo = true;
            } else {
                progressivo = false;
                while (adjacentes.isVazia()) {
                    caminhos.retireUmElemento();
                    if (caminhos.isVazia())
                        throw new Exception("Saida não encontrada");
                    this.voltar(caminhos.recupereUmElemento());
                    adjacentes = possibilidades.recupereUmElemento();
                    possibilidades.retireUmElemento();
                }
            }
        }
    }
}
