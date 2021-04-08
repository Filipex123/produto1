import Entidade.Coordenada;
import Entidade.Labirinto;
import Entidade.Pilha;
import Ferramenta.ArquivoUtils;

public class Main {
    public static void main(String[] args) {
        boolean progressivo = true;

        ArquivoUtils arq = new ArquivoUtils();
        Labirinto lab;

        try {

            lab = arq.carregarArquivo("labirinto2");
            final int tamanhoLab = lab.getAltura() * lab.getLargura();

            Pilha<Coordenada> caminhos = new Pilha<Coordenada>(tamanhoLab);
            Pilha<Coordenada> adjacentes = new Pilha<Coordenada>(3);
            Pilha<Pilha<Coordenada>> possibilidades = new Pilha(tamanhoLab);

            while (!lab.onSaida()) {
                if (progressivo) adjacentes = lab.getAdjacentes();

                if (!adjacentes.isVazia()) {
                    lab.andar(adjacentes.recupereUmElemento());
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
                        lab.voltar(caminhos.recupereUmElemento());
                        adjacentes = possibilidades.recupereUmElemento();
                        possibilidades.retireUmElemento();
                    }
                }
            }
            System.out.println("\n========================\n");
            lab.imprimeLabirinto();

            Pilha<Coordenada> aux = new Pilha<Coordenada>(tamanhoLab);
            while (!caminhos.isVazia()) {
                aux.guardeUmElemento(caminhos.recupereUmElemento());
                caminhos.retireUmElemento();
            }

            while (!aux.isVazia()) {
                System.out.println(aux.recupereUmElemento().toString());
                aux.retireUmElemento();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
