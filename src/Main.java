import Entidade.Coordenada;
import Entidade.Pilha;
import Ferramenta.ArquivoUtils;

public class Main {
    public static void main(String[] args) throws Exception {

        Pilha<Coordenada> caminhos = new Pilha<>(40);
        Pilha<Coordenada> adjacentes = new Pilha<>(3);
        Pilha<Pilha<Coordenada>> possibilidades = new Pilha(40);

        ArquivoUtils arq = new ArquivoUtils();

        try {
            arq.carregarArquivo("labirinto");
            String[][] lab = arq.montaLabirinto();
        } catch (Exception e) {
            //TODO melhorar
            System.err.println("Deu ruim");
        }

    }
}
