package Ferramenta;


import Entidade.Labirinto;
import org.apache.commons.lang3.StringUtils;

import java.io.*;


public class ArquivoUtils {

    private String[][] labirinto;
    private StringBuilder data;
    private int lines, columns;

    public void carregarArquivo(final String nomeArquivo) throws Exception {

        try {
            BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo + ".txt"));
            this.lines = Integer.parseInt(leitor.readLine());

            if(this.lines < 3) {
                throw new Exception("Tamanho de linhas deve ser ");
            }

            String str = null;
            data  = new StringBuilder();
            int contE = 0;
            int contS = 0;
            int contLines = 1;
            while((str = leitor.readLine()) != null) {

                if (this.columns != 0 && this.columns != str.length()) {
                    throw new Exception("Todas as linhas devem ter o mesmo tamanho");
                }

                if (!str.matches("^[ES#\\s]+$")) {
                    throw new Exception("Arquivo contém caracteres inválidos");
                }

                if(contLines == 1 || contLines == this.lines) {

                    if(str.contains(" ")) {
                        throw new Exception("Nao pode haver espaço vazio na primeira ou ultima linha");
                    }

                    if(str.substring(0,1).contains("E") || str.substring(0,1).contains("S")
                            || str.substring(columns).contains("E") || str.substring(columns).contains("S")) {
                        throw new Exception("Entrada e/ou Saida nao podem estar nos cantos");
                    }
                    contE += StringUtils.countMatches(str, "E");
                    contS += StringUtils.countMatches(str, "S");
                } else {
                    if(str.substring(0,1).contains(" ")) {
                        throw new Exception("Nao pode haver espaço vazio na primeira coluna");
                    }

                    if(str.substring(1, columns-2).contains("S") || str.substring(1, columns-2).contains("E")) {
                        throw new Exception("Entrada e/ou Saida devem estar apenas nas extremidades");
                    }
                    contE += StringUtils.countMatches(str.charAt(0) + str.substring(columns-1,columns), "E");
                    contS += StringUtils.countMatches(str.charAt(0) + str.substring(columns-1,columns), "S");
                }

                this.columns = str.length();
                data.append(str);
                contLines++;
            }

            if (contE != 1 || contS != 1) {
                throw new Exception("O labirinto deve ter 1 entrada e 1 saida");
            }
            leitor.close();
            this.labirinto = new String[this.lines][this.columns];

        } catch(NumberFormatException e) {
            throw new Exception("Arquivo corrompido: problema [ A primeira linha deve ser um numero ]");
        } catch(Exception e) {
            throw new Exception("Arquivo corrompido: problema [ " + e.getMessage() + " ]");
        }
    }

    //TODO será movido para uma classe Labirinto futuramente
    public String[][] montaLabirinto() {
        int cont = 0;
        for(int i = 0; i < this.lines; i++) {
            for(int j = 0; j < this.columns; j++) {
                this.labirinto[i][j] = data.substring(cont,cont+1);
                System.out.print(this.labirinto[i][j]);
                cont++;
            }
            System.out.println();
        }
        return this.labirinto;
    }
}
