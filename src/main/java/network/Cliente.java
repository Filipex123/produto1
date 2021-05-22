package network;

import interfaces.Janela;


/**
 * Classe responsável por carregar a interface gráfica do cliente
 *
 * @author Filipe Zanelato
 * @author Filipe Neves
 * @author Leonardo Guedes
 * @author Pedro Denilson
 */
public class Cliente {
    public static void main(String[] args) {
        try {
            new Janela();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
