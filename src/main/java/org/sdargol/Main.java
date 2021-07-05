package org.sdargol;

import org.sdargol.db.CardDAO;
import org.sdargol.db.dao.api.ICardDAO;
import org.sdargol.db.h2.ConnectionPool;
import org.sdargol.dto.CardDTO;
import org.sdargol.json.Converter;
import org.sdargol.json.IConverter;
import org.sdargol.utils.FileLoader;
import org.sdargol.http.server.Server;

import javax.smartcardio.Card;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {

    //INSERT INTO test (id) VALUES (5),(777),(999);
    public static void main(String[] args) {
        List<String> listFileData = FileLoader.load("/sql/init.sql");
        String sql = FileLoader.listToString(listFileData);

        Connection conn = null;

        try {
            //Class.forName ("org.h2.Driver");
            //conn = DriverManager.getConnection("jdbc:h2:~/test");
            //conn = DriverManager.getConnection("jdbc:h2:mem:");
            conn = ConnectionPool.getConnection();
            Statement statement = conn.createStatement();
            //statement.execute("CREATE TABLE test (id INT)");
            //statement.execute("INSERT INTO test (id) VALUES (777)");

            statement.execute(sql);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ICardDAO card = new CardDAO();
        card.createCard();

        List<CardDTO> allCards = card.getAllCards();
        allCards.forEach(System.out::println);
        
        ConnectionPool.dispose();

        /*while (rs.next()){
            System.out.println(rs.getInt("id") +
                    " " + rs.getLong("number") +
                    " " + rs.getBoolean("status"));
        }*/


    }

    //public static void main(String[] args) {
        /*IConverter<DTOTest> c = new Converter<>();
        String json = c.toJSON(new DTOTest(0, "test"));
        System.out.println(json);
        DTOTest dtoTest = c.toJavaObject(json, DTOTest.class);
        System.out.println(dtoTest.getId() + " " + dtoTest.getInfo());

        ObjectMapper m = new ObjectMapper();

        List<DTOTest> l = new ArrayList<>(Arrays.asList(
                new DTOTest(0, "a"),
                new DTOTest(1, "b"),
                new DTOTest(2, "c")
        ));

        String s = "";

        try {
            s = m.writeValueAsString(l);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("List: " + s);*/



        /*Server server = new Server();
        server.start();*/


        /*Reflections r = new Reflections("org.sdargol.dto");

        Set<Class<? extends ADTOBase>> type = r.getSubTypesOf(ADTOBase.class);

        type.forEach(t -> {
            System.out.println(t.getName());
        });

        Optional<Class<? extends ADTOBase>> dto = type.stream().findFirst();

        try {
            Constructor<? extends ADTOBase> constructor = dto.get().getConstructor();
            DTOTest dtoBase = (DTOTest)constructor.newInstance();
            System.out.println(dtoBase.getInfo() + " " + dtoBase.getId());
        } catch (NoSuchMethodException |
                InvocationTargetException |
                InstantiationException |
                IllegalAccessException e) {
            e.printStackTrace();
        }*/
    //}
}
