package network.servidor;

import network.entidade.Comunicado;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 * Entidade para cada conexão com o servidor
 */
public class UsuarioConexao {

    private Socket conexao;
    private ObjectInputStream receptor;
    private ObjectOutputStream transmissor;
    
    private Comunicado proximoComunicado = null;

    private final Semaphore mutEx = new Semaphore (1,true);

    /**
     * Construtor da entidade de conexão
     *
     * @param conexao socket da conexão
     * @param receptor objeto receptor
     * @param transmissor objeto transmissor
     * @throws Exception exceção de conexão
     */
    public UsuarioConexao(Socket conexao, ObjectInputStream receptor, ObjectOutputStream transmissor) throws Exception {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (receptor==null)
            throw new Exception ("Receptor ausente");

        if (transmissor==null)
            throw new Exception ("Transmissor ausente");

        this.conexao = conexao;
        this.receptor = receptor;
        this.transmissor = transmissor;
    }

    /**
     * Método que transmite para o servidor, fazendo-o 'receber' a informação
     *
     * @param x comunicado
     * @throws Exception erro na transmissão
     */
    public void receba(Comunicado x) throws Exception {
        try {
            this.transmissor.writeObject(x);
            this.transmissor.flush();
        }
        catch (IOException erro) {
            throw new Exception ("Erro de transmissao");
        }
    }

    /**
     * Método que escuta a resposta do servidor para o cliente
     *
     * @return um comunicado
     * @throws Exception erro na recepção
     */
    public Comunicado espie() throws Exception {
        try {
            this.mutEx.acquireUninterruptibly();
            if (this.proximoComunicado == null) {
                this.proximoComunicado = (Comunicado)this.receptor.readObject();
            }
            this.mutEx.release();
            return this.proximoComunicado;
        }
        catch (Exception erro) {
            throw new Exception ("Erro de recepcao ["+erro+"]");
        }
    }

    /**
     * Método que recebe a resposta do servidor, fazendo-o 'enviar'
     *
     * @return um comunicado
     * @throws Exception erro na recepção
     */
    public Comunicado envie() throws Exception {
        try {
            if (this.proximoComunicado == null) {
                this.proximoComunicado = (Comunicado)this.receptor.readObject();
            }
            Comunicado ret = this.proximoComunicado;
            this.proximoComunicado = null;
            return ret;
        }
        catch (Exception erro) {
            throw new Exception ("Erro de recepcao [\"+erro+\"]");
        }
    }

    /**
     * Método que encerra a conexão entre cliente e servidor
     *
     * @throws Exception erro na desconexão
     */
    public void adeus() throws Exception
    {
        try {
            this.transmissor.close();
            this.receptor.close();
            this.conexao.close();
        }
        catch (Exception erro) {
            throw new Exception ("Erro de desconexao");
        }
    }

    /**
     * Método que pega a conexão dessa entidade
     *
     * @return Socket conexão
     */
    public Socket getConexao(){
        return this.conexao;
    }
}
