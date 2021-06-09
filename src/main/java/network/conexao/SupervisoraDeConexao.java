package network.conexao;

import bd.daos.LabirintoDAO;
import bd.dbos.LabirintoDBO;
import ferramenta.LabirintoAdapter;
import network.entidade.base.Comunicado;
import network.entidade.PedidoLabirintos;
import network.entidade.PedidoSalvamento;
import network.entidade.RespostaLabirintos;
import network.entidade.LabirintoNetworkEntity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe supervisora de conexao
 */
public class SupervisoraDeConexao extends Thread {

    private UsuarioConexao usuario;
    private Socket conexao;
    private ArrayList<UsuarioConexao> usuarios;

    /**
     * Construtor
     * @param conexao socker conexão
     * @param usuarios lista de usuários conectados
     * @throws Exception
     */
    public SupervisoraDeConexao(Socket conexao, ArrayList<UsuarioConexao> usuarios) throws Exception {

        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

        this.conexao  = conexao;
        this.usuarios = usuarios;
    }

    /**
     * Método que roda a thread
     */
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
                    PedidoSalvamento pedidoSalvamento = ((PedidoSalvamento) comunicado);

                    LabirintoDBO labPesquisar = LabirintoAdapter.toDBO(pedidoSalvamento.getLabirinto());

                    if(LabirintoDAO.getLabirintoByNomeAndIdentificador(labPesquisar.getNome(),labPesquisar.getIdentificador()).isPresent()){
                        LabirintoDAO.update(labPesquisar);
                    }
                    else{
                        LabirintoDAO.insert(labPesquisar);
                    }
		        }
                else if (comunicado instanceof PedidoLabirintos) {
                    PedidoLabirintos pedidoLabirintos = (PedidoLabirintos)comunicado;
                    List<LabirintoNetworkEntity> labirintosRetornados = LabirintoDAO.getLabirintosByIdentificador(pedidoLabirintos.getIdCliente())
                            .stream()
                            .map(LabirintoAdapter::toNetworkEntity)
                            .collect(Collectors.toList());

					this.usuario.receba(new RespostaLabirintos(labirintosRetornados));
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
