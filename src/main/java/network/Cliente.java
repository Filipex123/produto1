package network;

import interfaces.Janela;
import network.entidade.Comunicado;
import network.entidade.PedidoLabirintos;
import network.entidade.RespostaLabirintos;
import network.entidade.Resultado;
import network.servidor.UsuarioConexao;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
