package Ferramenta;

import java.io.*;

public class ArquivoUtils {

    private BufferedReader leitor;
    private BufferedWriter escritor;
    private String[][] labirinto;
    private String data = "";
    private int lines, columns;

    public void carregarArquivo(final String nomeArquivo) throws Exception {

        try {
            this.leitor = new BufferedReader(new FileReader(nomeArquivo + ".txt"));

            int lineCont = 1;
            String str = null;

            while((str = this.leitor.readLine()) != null) {

                if(lineCont == 1){
                    int n = Integer.parseInt(str);
                    if(n >= 3){
                        this.lines = n;
                    }else{
                         // erro aqui   
                    }
                }
                
                if(lineCont == 2)
                    this.columns = str.length();

                data+=str;
                lineCont++;
            }
            this.leitor.close();
            this.labirinto = new String[this.lines][this.columns];

        } catch(IOException e) {
            throw new Exception("Arquivo corrompido");
        }
    }

    public String[][] montaLabirinto() {
        int cont = 1;
        for(int i = 0; i < this.lines; i++) {
            for(int j = 0; j < this.columns; j++) {
                this.labirinto[i][j] = data.substring(cont,cont+1);
                System.out.print(this.labirinto[i][j]);
                cont++;
            }
        }
        return this.labirinto;
    }
    
    public boolean verificaLabirinto(){
        int numE = 0, numS = 0;
        for(int i = 0; i < this.lines; i++) {
            for (int j = 0; j < this.columns; j++) {
                // Verifica se tem algum caracter diferente de [E, S, #, espaço]
                if(!this.labirinto[i][j].equals("E") &&
                        !this.labirinto[i][j].equals("S") &&
                        !this.labirinto[i][j].equals("#") &&
                        !this.labirinto[i][j].equals(" ")){
                    return false;
                }
                // Verificando quantos 'E' e 'S' tem nas bordas
                if (i == 0 || i == this.lines - 1 || j == 0 || j == this.columns - 1) {
                    if (this.labirinto[i][j].equals("E")) {
                        numE++;
                    } else if (this.labirinto[i][j].equals("S")) {
                        numS++;
                    } else if (this.labirinto[i][j].equals(" ")){
                        // Caso tenha algum espaço na borda
                        return false;
                    }
                }
            }
        }
        // caso tenha seja diferente de 1 tem alguma coisa errada
        if(numE != 1 || numS != 1)
            return false;

        return true;
    }

    public String[][] getLabirinto() {
        return labirinto;
    }
}
