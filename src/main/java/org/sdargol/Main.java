package org.sdargol;

import org.sdargol.dto.DTOTest;
import org.sdargol.http.server.Server;
import org.sdargol.json.Converter;
import org.sdargol.json.IConverter;

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
        IConverter<DTOTest> c = new Converter<>();
        String json = c.toJSON(new DTOTest(0, "test"));
        System.out.println(json);
        DTOTest dtoTest = c.toJavaObject(json, DTOTest.class);
        System.out.println(dtoTest.getId() + " " + dtoTest.getInfo());

        Server server = new Server();
        server.start();
    }
}
