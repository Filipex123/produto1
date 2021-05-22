package network.conexao;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Classe de thread para conexao com o servidor
 */
public class AceitadoraDeConexao extends Thread {

    private ServerSocket conexao;
    private ArrayList<UsuarioConexao> usuarios;

    /**
     * Construtor da thread
     *
     * @param porta a ser escutada
     * @param usuarios lista de clientes a serem conectados com o servidor
     * @throws Exception exception de conexao
     */
    public AceitadoraDeConexao(int porta, ArrayList<UsuarioConexao> usuarios) throws Exception {
        try {
            this.conexao = new ServerSocket(porta);
        } catch (Exception erro) {
            throw new Exception("Problema ao abrir conexão [porta: " + porta + "]");
        }

        if (usuarios == null)
            throw new Exception("Usuarios ausentes");

        this.usuarios = usuarios;
    }

    /**
     * Método que inicia a thread que escuta a conexão
     */
    public void run() {
        while(true) {
            Socket conexao = null;
            try {
                conexao = this.conexao.accept();
            } catch (Exception erro) {
                continue;
            }

            SupervisoraDeConexao supervisoraDeConexao = null;
            try {
                supervisoraDeConexao = new SupervisoraDeConexao(conexao, usuarios);
            } catch (Exception erro) {}
            supervisoraDeConexao.start();
        }
    }
}
