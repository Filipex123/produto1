import Entidade.Coordenada;
import Entidade.Labirinto;
import Entidade.Pilha;
import Ferramenta.ArquivoUtils;

public class Main {
    public static void main(String[] args) throws Exception {

        Pilha<Pilha<Coordenada>> possibilidades = new Pilha(100);
        ArquivoUtils arq = new ArquivoUtils();
        Labirinto lab;

        try {
            lab = arq.carregarArquivo("labirinto3");

            while (!lab.onSaida()) {
                Pilha<Coordenada> adjacentes = lab.getAdjacentes();
                if (!adjacentes.isVazia()) {
                    lab.andar(adjacentes.recupereUmElemento());
                    adjacentes.retireUmElemento();
                }
                possibilidades.guardeUmElemento(adjacentes);
                System.out.println("===================");
                lab.imprimeLabirinto();
                System.out.println("===================");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
