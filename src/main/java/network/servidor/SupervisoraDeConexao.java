package network.servidor;

import bd.daos.LabirintoDAO;
import network.entidade.Comunicado;
import network.entidade.PedidoLabirintos;
import network.entidade.PedidoSalvamento;
import network.entidade.RespostaLabirintos;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SupervisoraDeConexao extends Thread {

    private UsuarioConexao usuario;
    private Socket conexao;
    private ArrayList<UsuarioConexao> usuarios;

    public SupervisoraDeConexao(Socket conexao, ArrayList<UsuarioConexao> usuarios) throws Exception {

        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

        this.conexao  = conexao;
        this.usuarios = usuarios;
    }

    public void run() {

        ObjectOutputStream transmissor;
        try {
            transmissor = new ObjectOutputStream(this.conexao.getOutputStream());
        }
        catch (Exception erro) {
            return;
        }

        ObjectInputStream receptor = null;
        try {
            receptor= new ObjectInputStream(this.conexao.getInputStream());
        }
        catch(Exception ex) {
            try {
                transmissor.close();
            }
            catch (Exception falha){} // so tentando fechar antes de acabar a thread

            return;
        }

        try {
            this.usuario = new UsuarioConexao(this.conexao, receptor, transmissor);
        }
        catch(Exception erro) {} // sei que passei os parametros corretos

        try {
            synchronized(this.usuarios) {
                this.usuarios.add(this.usuario);
            }

            while(true) {
                Comunicado comunicado = this.usuario.envie();

                if(comunicado == null)
                    return;
                else if (comunicado instanceof PedidoSalvamento) {
                    LabirintoDAO.insert(((PedidoSalvamento) comunicado).getLabirinto());
		        }
                else if (comunicado instanceof PedidoLabirintos) {
                    PedidoLabirintos entradinha = (PedidoLabirintos)comunicado;
					this.usuario.receba(new RespostaLabirintos(LabirintoDAO.getLabirintosByIdentificador(entradinha.getIdCliente())));
                }
            }
        } catch (Exception erro) {
            try {
                transmissor.close ();
                receptor.close ();
            }
            catch(Exception falha) {} // so tentando fechar antes de acabar a thread
            return;
        }
    }
}
