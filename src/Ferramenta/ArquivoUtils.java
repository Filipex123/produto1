package Ferramenta;

import Entidade.Coordenada;

import java.io.*;

public class ArquivoUtils {

    private BufferedReader leitor;
    private BufferedWriter escritor;
    private String[][] labirinto;
    private String data = "";

    public void carregarArquivo(final String nomeArquivo) throws Exception {

        String str = null;
        try {
            this.leitor = new BufferedReader(new FileReader(nomeArquivo + ".txt"));
//            this.escritor = new BufferedWriter(new FileWriter("labirinto.txt"));

            int lineCont = 1;
            int lines = 0;
            int colums = 0;

            while((str = this.leitor.readLine()) != null) {

                if(lineCont == 1)
                    lines = Integer.parseInt(str);
                if(lineCont == 2)
                    colums = str.length();

                data+=str;
                lineCont++;
            }
            this.leitor.close();
            this.labirinto = new String[lines][colums];
//            this.escritor.close();
        } catch(IOException e) {
            System.out.println("Arquivo corrompido");
        }
    }

    public String[][] montaLabirinto() {
        int cont = 1;
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
