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
//        try {
//            new Janela();
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }

        Socket conexao = null;
        try {
            conexao = new Socket("localhost", 2021);
        } catch (Exception ex) {
            System.err.println ("Indique o servidor e a porta corretos!\n");
        }

        ObjectOutputStream transmissor=null;
        try {
            transmissor = new ObjectOutputStream(conexao.getOutputStream());
        }
        catch (Exception erro) {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        ObjectInputStream receptor=null;
        try {
            receptor = new ObjectInputStream(conexao.getInputStream());
        }
        catch (Exception erro) {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        UsuarioConexao servidor=null;
        try
        {
            servidor = new UsuarioConexao(conexao, receptor, transmissor);
        }
        catch (Exception erro)
        {
            System.err.println ("Indique o servidor e a porta corretos!\n");
            return;
        }

        try {
            servidor.receba(new PedidoLabirintos("192.168.0.1"));

            Comunicado comunicado = null;
            do
            {
                comunicado = (Comunicado)servidor.espie ();
            }
            while (!(comunicado instanceof RespostaLabirintos));

            RespostaLabirintos res = (RespostaLabirintos)comunicado;
            res.getLabirintos().forEach(System.out::println);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
