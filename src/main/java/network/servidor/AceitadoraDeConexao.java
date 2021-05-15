package network.servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AceitadoraDeConexao extends Thread {

    private ServerSocket conexao;
    private ArrayList<UsuarioConexao> usuarios;

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
