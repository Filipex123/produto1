package entidade;

import java.util.Arrays;

/**
 * Entidade de labirinto com as informações dimensionais
 */
public class LabirintoEntity {

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
    public LabirintoEntity(int altura, int largura, Coordenada entrada, Coordenada saida, String data) {
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
            for (int j = 0; j < this.largura; j++) {
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
        return this.atual.equals(this.saida);
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

    public String resolve() throws Exception {

        final int tamanhoLab = this.altura * this.largura;
        boolean progressivo = true;

        Pilha<Coordenada> caminhos = new Pilha<Coordenada>(tamanhoLab);
        Pilha<Coordenada> adjacentes = new Pilha<Coordenada>(3);
        Pilha<Pilha<Coordenada>> possibilidades = new Pilha(tamanhoLab);
        StringBuilder caminhoResolvido = new StringBuilder();

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

        Pilha<Coordenada> caminhoAux = new Pilha<Coordenada>(tamanhoLab);
        while (!caminhos.isVazia()) {
            caminhoAux.guardeUmElemento(caminhos.recupereUmElemento());
            caminhos.retireUmElemento();
        }

        int cont = 0;
        while (!caminhoAux.isVazia()) {
            if(cont % 13 == 0) {
                caminhoResolvido.append("\n");
            }
            caminhoResolvido.append(caminhoAux.recupereUmElemento().toString()).append(" - ");
            caminhoAux.retireUmElemento();
            cont++;
        }

        return caminhoResolvido.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null ) {
            return false;
        }

        if (obj.getClass()!= LabirintoEntity.class) {
            return false;
        }

        LabirintoEntity labirintoEntity = (LabirintoEntity) obj;

        return altura == labirintoEntity.altura &&
                largura == labirintoEntity.largura &&
                entrada.equals(labirintoEntity.entrada) &&
                saida.equals(labirintoEntity.saida) &&
                Arrays.deepEquals(mapa, labirintoEntity.mapa) &&
                atual.equals(labirintoEntity.atual);
    }

    @Override
    public int hashCode() {
        int ret = 69;

        ret = 13*ret + new Byte((byte)this.altura).hashCode();
        ret = 17*ret + new Byte((byte)this.largura).hashCode();
        ret = 23*ret + new Byte(String.valueOf(this.atual)).hashCode();
        ret = 19*ret + new Byte(String.valueOf(this.entrada)).hashCode();
        ret = 29*ret + new Byte(Arrays.deepToString(this.mapa)).hashCode();
        ret = 31*ret + new Byte(String.valueOf(this.saida)).hashCode();

        return (ret<0)? -ret : ret;
    }

    @Override
    public String toString() {
        return "Labirinto{" +
                "entrada=" + entrada +
                ", saida=" + saida +
                ", altura=" + altura +
                ", largura=" + largura +
                ", mapa=" + Arrays.toString(mapa) +
                ", atual=" + atual +
                '}';
    }
}
