import Entidade.Coordenada;
import Entidade.Labirinto;
import Entidade.Pilha;
import Ferramenta.ArquivoUtils;

public class Main {
    public static void main(String[] args) throws Exception {

        boolean progressivo = true;

        ArquivoUtils arq = new ArquivoUtils();
        Labirinto lab;

        try {

            lab = arq.carregarArquivo("labirinto5");

            Pilha<Coordenada> caminhos = new Pilha<>(lab.getAltura() * lab.getLargura());
            Pilha<Coordenada> adjacentes = new Pilha<>(3);
            Pilha<Pilha<Coordenada>> possibilidades = new Pilha(lab.getAltura() * lab.getLargura());

            while( !lab.onSaida() ){
                if( progressivo ) adjacentes = lab.getAdjacentes();

                if( !adjacentes.isVazia() ){
                    lab.andar( adjacentes.recupereUmElemento() );
                    caminhos.guardeUmElemento( adjacentes.recupereUmElemento() );
                    adjacentes.retireUmElemento();
                    possibilidades.guardeUmElemento( adjacentes );
                    progressivo = true;
                } else {
                    progressivo = false;
                    while ( adjacentes.isVazia() ) {
                        caminhos.retireUmElemento();
                        if(caminhos.isVazia())
                            throw new Exception("Saida não encontrada");
                        lab.voltar( caminhos.recupereUmElemento() );
                        adjacentes = possibilidades.recupereUmElemento();
                        possibilidades.retireUmElemento();
                    }
                }
            }
            System.out.println("\n========================\n");
            lab.imprimeLabirinto();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
