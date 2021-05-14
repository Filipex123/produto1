package network;

import interfaces.Janela;

/**
 * Classe main onde carrega e resolve o labirinto
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
