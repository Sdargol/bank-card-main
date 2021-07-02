package org.sdargol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;
import org.sdargol.db.h2.ConnectionPool;
import org.sdargol.dto.ADTOBase;
import org.sdargol.dto.DTOTest;
import org.sdargol.http.server.Server;
import org.sdargol.json.Converter;
import org.sdargol.json.IConverter;
import org.sdargol.utils.FileLoader;
import sun.reflect.Reflection;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class Main {

   /* public static void main(String[] args) {

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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test");

            while (resultSet.next()){
                System.out.println(resultSet.getInt("id"));
            }
            //System.out.println(resultSet.getInt(1));
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ConnectionPool.dispose();

        try {
            conn = ConnectionPool.getConnection();
            Statement statement = conn.createStatement();
            //statement.execute("CREATE TABLE test (id INT)");
            statement.execute("INSERT INTO test (id) VALUES (5)");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test");

            while (resultSet.next()){
                System.out.println(resultSet.getInt("id"));
            }
            //System.out.println(resultSet.getInt(1));
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/

    public static void main(String[] args) {
        IConverter<DTOTest> c = new Converter<>();
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

        System.out.println("List: " + s);

        /*Server server = new Server();
        server.start();*/

        Reflections r = new Reflections("org.sdargol.dto");

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
        }
    }
}
