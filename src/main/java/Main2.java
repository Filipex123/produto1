import bd.daos.LabirintoDAO;

public class Main2 {

    public static void main(String[] args) {

//        Connection conn;
//        Statement stmt = null;

        try {

//            Labirinto lab = new Labirinto("", "192.168.0.1", null, null, "Teste 2");
//            Labirintos.insert(lab);
            System.out.println(LabirintoDAO.getLabirinto("Andre Luis", "192.168.0.1"));
//            lab.setConteudo("Teste 1");
//            Labirintos.update(lab);
//            System.out.println(Labirintos.getLabirinto("Leonardo Guedes", "192.168.0.2"));
//            Labirintos.delete(Labirintos.getLabirinto("Leonardo Guedes", "192.168.0.2"));

//            List<Labirinto> labs = Labirintos.getLabirintos();
//            labs.forEach(System.out::println);
//            Class.forName(JDBC_DRIVER);
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//
//            //Testa sua conexão//
//            if (conn != null) {
//                System.out.println("STATUS--->Conectado com sucesso!");
//            } else {
//                throw new Exception("STATUS--->Não foi possivel realizar conexão");
//            }
//
//            stmt = conn.createStatement();
//
//            String sql = "select * from books";
//            ResultSet rs = stmt.executeQuery(sql);


//            while(rs.next()){
//                //Display values
//                System.out.println("ISBN: " + rs.getString("isbn"));
//                System.out.println("TITLE: " + rs.getString("title"));
//            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
