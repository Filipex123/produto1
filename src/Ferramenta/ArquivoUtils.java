package Ferramenta;

import Entidade.Coordenada;
import Entidade.Labirinto;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.FileReader;

public class ArquivoUtils {

    public Labirinto carregarArquivo(final String nomeArquivo) throws Exception {

        StringBuilder data;
        int lines;
        int columns = 0;
        try {
            BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo + ".txt"));
            lines = Integer.parseInt(leitor.readLine());

            if(lines < 3) {
                throw new Exception("Tamanho de linhas deve ser ");
            }

            String str = null;
            data  = new StringBuilder();
            Coordenada cEntrada = null;
            Coordenada cSaida = null;
            int contE = 0;
            int contS = 0;
            int contLines = 1;
            while((str = leitor.readLine()) != null) {
                if (columns != 0 && columns != str.length()) {
                    throw new Exception("Todas as linhas devem ter o mesmo tamanho");
                }

                columns = str.length();

                if (!str.matches("^[ES#\\s]+$")) {
                    throw new Exception("Arquivo contém caracteres inválidos");
                }

                if(contLines == 1 || contLines == lines) {

                    if(str.contains(" ")) {
                        throw new Exception("Nao pode haver espaço vazio na primeira ou ultima linha");
                    }

                    if(str.substring(0,1).contains("E") || str.substring(0,1).contains("S")
                            || str.substring(columns).contains("E") || str.substring(columns).contains("S")) {
                        throw new Exception("Entrada e/ou Saida nao podem estar nos cantos");
                    }

                    contE += StringUtils.countMatches(str, "E");
                    if(contE == 1 && cEntrada == null) cEntrada = new Coordenada(contLines-1 ,str.indexOf("E"));
                    contS += StringUtils.countMatches(str, "S");
                    if(contS == 1 && cSaida == null) cSaida = new Coordenada(contLines-1 ,str.indexOf("S"));
                } else {
                    if(str.substring(0,1).contains(" ")) {
                        throw new Exception("Nao pode haver espaço vazio na primeira coluna");
                    }

                    if(str.substring(1, columns-2).contains("S") || str.substring(1, columns-2).contains("E")) {
                        throw new Exception("Entrada e/ou Saida devem estar apenas nas extremidades");
                    }
                    contE += StringUtils.countMatches(str.charAt(0) + str.substring(columns-1,columns), "E");
                    if(contE == 1 && cEntrada == null) cEntrada = new Coordenada(contLines-1 ,str.indexOf("E"));
                    contS += StringUtils.countMatches(str.charAt(0) + str.substring(columns-1,columns), "S");
                    if(contS == 1 && cSaida == null) cSaida = new Coordenada(contLines-1 ,str.indexOf("S"));
                }

                columns = str.length();
                data.append(str);
                contLines++;
            }

            if (contE != 1 || contS != 1) {
                throw new Exception("O labirinto deve ter 1 entrada e 1 saida");
            }
            leitor.close();
            return new Labirinto(lines, columns, cEntrada, cSaida, data.toString());

        } catch(NumberFormatException e) {
            throw new Exception("Arquivo corrompido: problema [ A primeira linha deve ser um numero ]");
        } catch(Exception e) {
            throw new Exception("Arquivo corrompido: problema [ " + e.getMessage() + " ]");
        }
    }
}
