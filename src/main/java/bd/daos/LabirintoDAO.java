package bd.daos;

import bd.core.MariaDBUtils;
import bd.dbos.LabirintoDBO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LabirintoDAO {

    public static LabirintoDBO getLabirinto(String identificador) throws Exception {

        try {
            Connection conn = MariaDBUtils.getConexao();

            String sql = "SELECT * FROM Labirinto WHERE identificador = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, nome);
            ps.setString(1, identificador);

            ResultSet resultado = ps.executeQuery();

            if (!resultado.first())
                throw new Exception ("Nao cadastrado");

            ps.close();

            return new LabirintoDBO(resultado.getString("nome"),
                    resultado.getString("identificador"),
                    resultado.getTimestamp("dataCriacao").toLocalDateTime(),
                    resultado.getTimestamp("dataEdicao").toLocalDateTime(),
                    resultado.getString("conteudo"));

        } catch (Exception ex) {
            throw new Exception ("Erro ao procurar labirinto: ["+ex+"]");
        }
    }

    public static List<LabirintoDBO> getLabirintos() throws Exception {

        try {
            Connection conn = MariaDBUtils.getConexao();

            String sql = "SELECT * FROM Labirinto";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet resultado = ps.executeQuery();
            ps.close();

            if (!resultado.first())
                throw new Exception ("Nenhum elemento encontrado");

            List<LabirintoDBO> labirintoDBOS = new ArrayList<>();
            while (resultado.next()) {
                labirintoDBOS.add(new LabirintoDBO(resultado.getString("nome"),
                        resultado.getString("identificador"),
                        resultado.getTimestamp("dataCriacao").toLocalDateTime(),
                        resultado.getTimestamp("dataEdicao").toLocalDateTime(),
                        resultado.getString("conteudo")));
            }

            return labirintoDBOS;
        } catch (Exception ex) {
            throw new Exception ("Erro ao procurar labirinto: ["+ex+"]");
        }
    }

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

    public static boolean update(LabirintoDBO lab) throws Exception {

        try {
            Connection conn = MariaDBUtils.getConexao();

            String sql = "UPDATE Labirinto " +
                    "SET conteudo = ?" +
                    "WHERE nome = ? AND identificador = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, lab.getConteudo());
            ps.setString(2, lab.getNome());
            ps.setString(3, lab.getIdentificador());

            ps.executeQuery();

            ps.close();
            return true;

        } catch (Exception ex) {
            throw new Exception ("Erro ao inserir labirinto: ["+ex+"]");
        }
    }

    public static boolean delete(LabirintoDBO lab) throws Exception {

        try {
            Connection conn = MariaDBUtils.getConexao();

            String sql = "DELETE FROM Labirinto WHERE nome = ? AND identificador = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, lab.getNome());
            ps.setString(2, lab.getIdentificador());

            ps.executeQuery();

            ps.close();
            return true;
        } catch (Exception ex) {
            throw new Exception ("Erro ao deletar labirinto: ["+ex+"]");
        }
    }
}
