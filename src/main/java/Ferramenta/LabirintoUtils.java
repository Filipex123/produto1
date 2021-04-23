package Ferramenta;

import Entidade.Coordenada;
import Entidade.Labirinto;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils para carregar e validar arquivo de texto referente ao labirinto
 */
public class LabirintoUtils {

    /**
     * Método responsável por carregar um arquivo txt e retorna-lo em String
     * @param arquivo a ser aberto
     * @return estrutura String de labirinto
     * @throws Exception exceções de validação de arquivo
     */
    public static String carregarArquivo(final File arquivo) throws Exception {

        StringBuilder data;
        try {
            BufferedReader leitor = new BufferedReader(new FileReader(arquivo.getAbsolutePath()));
            Integer.parseInt(leitor.readLine());
            String str;
            data = new StringBuilder();
            while ((str = leitor.readLine()) != null) {
                data.append(str).append("\n");
            }
            leitor.close();
        } catch (NumberFormatException e) {
            throw new Exception("Arquivo corrompido: problema [ A primeira linha deve ser um numero ]");
        } catch (Exception e) {
            throw new Exception("Arquivo corrompido: problema [ " + e.getMessage() + " ]");
        }

        return data.toString();
    }

    public static Labirinto carregaString(String data) throws Exception {

        verifica(data, false);
        String[] linhas = data.split("\n");

        Coordenada cEntrada = new Coordenada();
        Coordenada cSaida = new Coordenada();

        for (int i = 0; i < linhas.length; i++) {
            if (linhas[i].contains("E")) {
                cEntrada.setX(i);
                cEntrada.setY(linhas[i].indexOf("E"));
            }

            if (linhas[i].contains("S")) {
                cSaida.setX(i);
                cSaida.setY(linhas[i].indexOf("S"));
            }
        }

        return new Labirinto(linhas.length, linhas[0].length(), cEntrada, cSaida, data);
    }

    public static void verifica(String data, boolean salvando) throws Exception {
        Pattern pattern = Pattern.compile(salvando?("^[ES#\\s*]+$"):("^[ES#\\s]+$"));
        Matcher matcher = pattern.matcher(data);

        if (!matcher.matches()) {
            throw new Exception("Labirinto possui caracteres inválidos");
        }

        if (StringUtils.countMatches(data, "E") != 1) {
            throw new Exception("Labirinto deve ter exatamente uma entrada");
        }

        if (StringUtils.countMatches(data, "S") != 1) {
            throw new Exception("Labirinto deve ter exatamente uma saída");
        }

        String[] lista = data.split("\n");
        int tamLinha = lista[0].length();

        if (lista.length < 3) {
            throw new Exception("O número mínimo de linhas deve ser 3");
        }

        boolean saidaValida = false;
        boolean entradaValida = false;
        for (int i = 0; i < lista.length; i++) {
            if (lista[i].length() != tamLinha) {
                throw new Exception("Todas as linhas devem ter o mesmo tamanho [linha: " + (i + 1) + "]");
            }

            if (i == 0 || i == lista.length - 1) {
                if (lista[i].contains(" ")) {
                    throw new Exception("Nao pode haver espaço vazio na primeira ou ultima linha");
                }

                if (lista[i].substring(0, 1).contains("E") || lista[i].substring(0, 1).contains("S")
                        || lista[i].substring(tamLinha - 1).contains("E") || lista[i].substring(tamLinha - 1).contains("S")) {
                    throw new Exception("Entrada e/ou Saida nao podem estar nos cantos");
                }

                if (lista[i].contains("E")) {
                    entradaValida = true;
                }

                if (lista[i].contains("S")) {
                    saidaValida = true;
                }
            } else {

                if (lista[i].substring(0, 1).contains(" ") || lista[i].substring(tamLinha - 1).contains(" ")) {
                    throw new Exception("Nao pode haver espaço vazio na primeira coluna nem na última coluna [linha: " + (i + 1) + "]");
                }

                if (lista[i].substring(0, 1).contains("E") || lista[i].substring(tamLinha - 1).contains("E")) {
                    entradaValida = true;
                }

                if (lista[i].substring(0, 1).contains("S") || lista[i].substring(tamLinha - 1).contains("S")) {
                    saidaValida = true;
                }
            }
        }

        if (!saidaValida) {
            throw new Exception("Saida (S) está em local inválido");
        }

        if (!entradaValida) {
            throw new Exception("Entrada (E) está em local inválido");
        }
    }
}
