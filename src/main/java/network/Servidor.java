package network;

import network.entidade.ComunicadoDeDesligamento;
import network.servidor.AceitadoraDeConexao;
import network.servidor.UsuarioConexao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Servidor {

    private static final int PORTA_PADRAO = 2021;

    public static void main(String[] args) {

        ArrayList<UsuarioConexao> usuarios = new ArrayList<> ();

        AceitadoraDeConexao aceitadoraDeConexao = null;
        try {
            aceitadoraDeConexao = new AceitadoraDeConexao(PORTA_PADRAO, usuarios);
            aceitadoraDeConexao.start();
        }
        catch (Exception erro) {
            System.err.println ("Escolha uma porta apropriada e liberada para uso!\n");
            return;
        }

        //Bloco que ouve o desligamento
        for(;;) {
            System.out.println ("O servidor esta ativo! Para desativa-lo,");
            System.out.println ("use o comando \"desativar\"\n");
            System.out.print   ("> ");

            String comando = null;
            try {
                comando = new BufferedReader(new InputStreamReader(System.in)).readLine();
            }
            catch (Exception ignored) {}

            if (comando.toLowerCase().equals("desativar")) {

                synchronized(usuarios) {
                    ComunicadoDeDesligamento comunicadoDeDesligamento = new ComunicadoDeDesligamento ();

                    for (UsuarioConexao usuario:usuarios) {
                        if(!usuario.getConexao().isClosed()){
                            try {
                                usuario.receba(comunicadoDeDesligamento);
                                usuario.adeus();
                            }
                            catch (Exception erro) {}
                        }
                    }
                }
                System.out.println ("O servidor foi desativado!\n");
                System.exit(0);
            }
            else if(comando.toLowerCase().equals("ls")){
                // Lista conexões ativas (não closeds)
                usuarios.forEach(item -> {
                    if(!item.getConexao().isClosed()){
                        System.out.print("| 1 |");
                    }
                });

            }
            else{
                System.err.println ("Comando invalido!\n");
            }
        }
    }
}
