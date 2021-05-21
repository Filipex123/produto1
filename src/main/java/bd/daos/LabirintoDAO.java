package bd.daos;

import bd.core.MariaDBUtils;
import bd.dbos.LabirintoDBO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que executa comandos na tabela de Labirinto do banco
 */
public class LabirintoDAO {

    /**
     * Método para recuperar todos os registros ligados ao identificador
     * @param identificador identificador do cliente
     * @return List<LabirintoDBO> uma lista de labirintos
     * @throws Exception
     */
    public static List<LabirintoDBO> getLabirintosByIdentificador(String identificador) throws Exception {

        try {
            Connection conn = MariaDBUtils.getConexao();

            String sql = "SELECT * FROM Labirinto WHERE identificador = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, identificador);

            ResultSet resultado = ps.executeQuery();
            ps.close();

            if (!resultado.first())
                throw new Exception ("Nenhum registro para esse identificador [" + identificador + "]");

            List<LabirintoDBO> labirintoDBOS = new ArrayList<>();
            do {
                labirintoDBOS.add(new LabirintoDBO(resultado.getString("nome"),
                        resultado.getString("identificador"),
                        resultado.getTimestamp("dataCriacao").toLocalDateTime(),
                        resultado.getTimestamp("dataEdicao").toLocalDateTime(),
                        resultado.getString("conteudo")));
            } while (resultado.next());

            return labirintoDBOS;

        } catch (Exception ex) {
            throw new Exception ("Erro ao procurar labirinto: ["+ex+"]");
        }
    }

    /**
     * Método para inserir um labirinto no banco
     * @param lab labirinto a ser inserido
     * @return boolean do resultado da inserção
     * @throws Exception
     */
    public static boolean insert(LabirintoDBO lab) throws Exception {

        try {
            Connection conn = MariaDBUtils.getConexao();

            String sql = "INSERT INTO Labirinto (nome, identificador, conteudo) " +
                    "VALUES(?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, lab.getNome());
            ps.setString(2, lab.getIdentificador());
            ps.setString(3, lab.getConteudo());

            ps.executeQuery();

            ps.close();
            return true;
        } catch (Exception ex) {
            throw new Exception ("Erro ao inserir labirinto: ["+ex+"]");
        }
    }
}
