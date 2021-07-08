package org.sdargol;

import org.sdargol.db.dao.DAOAccount;
import org.sdargol.db.dao.DAOUser;
import org.sdargol.db.dao.api.IDAOAccount;
import org.sdargol.db.dao.api.IDAOUser;
import org.sdargol.db.h2.ConnectionPool;
import org.sdargol.dto.DTOUser;
import org.sdargol.dto.request.DTOTransfer;
import org.sdargol.http.server.Server;
import org.sdargol.utils.FileLoader;
import org.sdargol.utils.Props;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> listFileData = FileLoader.load("/sql/init.sql");
        String sql = FileLoader.listToString(listFileData);

        try(Connection conn = ConnectionPool.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //IDAOUser u = new DAOUser();
        //DTOUser user = new DTOUser(0,"dimon@gmail.com", "password123");
        //u.create(user);

        //IDAOAccount acc = new DAOAccount();
        //DTOTransfer tr = new DTOTransfer(777,778,11);

        Server server = new Server();
        server.start();
    }
}
