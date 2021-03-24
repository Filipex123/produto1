import Entidade.Coordenada;
import Entidade.Labirinto;
import Entidade.Pilha;
import Ferramenta.ArquivoUtils;

public class Main {
    public static void main(String[] args) throws Exception {

        Pilha<Coordenada> caminhos = new Pilha<>(40);
        Pilha<Coordenada> adjacentes = new Pilha<>(3);
        Pilha<Pilha<Coordenada>> possibilidades = new Pilha(100);

        Coordenada coordenadaEntrada = new Coordenada(5,5);
        Coordenada coordenadaSaida = new Coordenada(2,2);


        ArquivoUtils arq = new ArquivoUtils();
        Labirinto lab;

        try {

            lab = arq.carregarArquivo("labirinto");

            while(!lab.onSaida()){
                adjacentes = lab.getAdjacentes();
                if(!adjacentes.isVazia()){
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
        System.out.println("oidajsdioa");
    }
}
