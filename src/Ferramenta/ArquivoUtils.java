package Ferramenta;

import java.io.*;

public class ArquivoUtils {

    private BufferedReader leitor;
    private BufferedWriter escritor;
    private final String[][] labirinto;
    private String data;

    public ArquivoUtils() {
        this.labirinto = new String[5][8];
        this.data = "";
    }

    public void carregarArquivo(final String nomeArquivo) {

        String str = null;
        try {
            this.leitor = new BufferedReader(new FileReader(nomeArquivo + ".txt"));
//            this.escritor = new BufferedWriter(new FileWriter("labirinto.txt"));

            while((str = this.leitor.readLine()) != null) {
                data+=str;
            }
            this.leitor.close();
//            this.escritor.close();
        } catch(IOException e) {
            System.out.println("Arquivo corrompido");
        }
    }

    public String[][] montaLabirinto() {
        int cont = 0;
        for(int i = 1; i<=5; i++) {
            for(int j = 1; j<=8; j++) {
                this.labirinto[i-1][j-1] = data.substring(cont,cont+1);
                System.out.print(this.labirinto[i-1][j-1]);
                cont++;
            }
        }
        return this.labirinto;
    }

    public String[][] getLabirinto() {
        return labirinto;
    }
}
