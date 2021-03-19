import Entidade.Coordenada;
import Entidade.Labirinto;
import Entidade.Pilha;
import Ferramenta.ArquivoUtils;

public class Main {
    public static void main(String[] args) throws Exception {

        Pilha<Coordenada> caminhos = new Pilha<>(40);
        Pilha<Coordenada> adjacentes = new Pilha<>(3);
        Pilha<Pilha<Coordenada>> possibilidades = new Pilha(40);

        Coordenada coordenadaEntrada = new Coordenada(5,5);
        Coordenada coordenadaSaida = new Coordenada(2,2);

        Labirinto labirinto = new Labirinto(5,5,new Coordenada(5,5),coordenadaSaida);

        ArquivoUtils arq = new ArquivoUtils();

        try {
            arq.carregarArquivo("labirinto2");
            String[][] lab = arq.montaLabirinto();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
