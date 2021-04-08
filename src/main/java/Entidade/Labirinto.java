package Entidade;

public class Labirinto {

    private Coordenada entrada;
    private Coordenada saida;
    private int altura;
    private int largura;
    private String[][] mapa;
    private Coordenada atual;

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

    public int getAltura() {
        return this.altura;
    }

    public int getLargura() {
        return this.largura;
    }

    public boolean onSaida() {
        return this.atual.validaIgualdade(this.saida);
    }

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

    public void andar(Coordenada value) {
        this.atual = value;
        int x = this.atual.getX();
        int y = this.atual.getY();

        if (mapa[x][y].equals(" ")) {
            mapa[x][y] = "*";
        }
    }

    public void voltar(Coordenada value) {
        int x = this.atual.getX();
        int y = this.atual.getY();
        mapa[x][y] = " ";
        this.atual = value;
    }

    public void imprimeLabirinto() {
        for (int i = 0; i < this.altura; i++) {
            for (int j = 0; j < this.largura; j++) {
                System.out.print(mapa[i][j]);
            }
            System.out.println();
        }
    }

}
