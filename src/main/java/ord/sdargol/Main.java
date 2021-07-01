package ord.sdargol;

import ord.sdargol.http.server.Server;

import java.sql.*;
import java.util.Date;

public class Main {
    /*public static void main(String[] args) {
        printInfo("MAIN","run");

        Connection conn = null;

        try {
            Class.forName ("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/test");
            Statement statement = conn.createStatement();
            //statement.execute("CREATE TABLE test (id INT)");
            //statement.execute("INSERT INTO test (id) VALUES (777)");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test");
            //System.out.println(resultSet.getInt(1));
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }*/

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
