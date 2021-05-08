import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main2 {

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://localhost:3306/projetoC";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "admin";

    public static void main(String[] args) {

        Connection conn;
        Statement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //Testa sua conexão//
            if (conn != null) {
                System.out.println("STATUS--->Conectado com sucesso!");
            } else {
                throw new Exception("STATUS--->Não foi possivel realizar conexão");
            }

            stmt = conn.createStatement();

            String sql = "select * from books";
            ResultSet rs = stmt.executeQuery(sql);


            while(rs.next()){
                //Display values
                System.out.println("ISBN: " + rs.getString("isbn"));
                System.out.println("TITLE: " + rs.getString("title"));
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
