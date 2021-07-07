package org.sdargol;

import org.sdargol.db.h2.ConnectionPool;
import org.sdargol.http.server.Server;
import org.sdargol.utils.FileLoader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> listFileData = FileLoader.load("/sql/init.sql");
        String sql = FileLoader.listToString(listFileData);

        try(Connection conn = ConnectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Server server = new Server();
        server.start();
    }
}
