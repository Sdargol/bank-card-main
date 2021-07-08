package org.sdargol.db.dao;

import org.sdargol.db.dao.api.IDAOAccount;
import org.sdargol.db.dao.api.IDAOUser;
import org.sdargol.db.h2.ConnectionPool;
import org.sdargol.dto.DTOUser;
import org.sdargol.dto.request.DTOTransfer;
import org.sdargol.dto.request.DTOUserTransfer;
import org.sdargol.dto.response.DTOMessage;
import org.sdargol.utils.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOUser implements IDAOUser {
    private final static Logger LOGGER = Log.getLogger(DAOUser.class.getName());

    @Override
    public DTOMessage create(DTOUser user) {
        DTOMessage msg = null;
        int idUser = 0;
        int idAccount = 0;
        try(Connection connection = ConnectionPool.getConnection()) {
            connection.setAutoCommit(false);

            String sql = "INSERT INTO users (login,password) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.execute();

            //штука, отвечающая за последний id
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            idUser = rs.getInt(1);

            sql = "INSERT INTO accounts (money) VALUES (?)";
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, 0);
            ps.execute();

            //штука, отвечающая за последний id
            rs = ps.getGeneratedKeys();
            rs.next();
            idAccount = rs.getInt(1);

            System.out.println("user id: " + idUser + " account id: " + idAccount);

            sql = "INSERT INTO AccountToUser (account_id, user_id) VALUES (?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,idAccount);
            ps.setInt(2,idUser);
            ps.execute();

            msg = new DTOMessage("User and Account successfully created");

            ps.close();
            connection.commit();
        } catch (SQLException e) {
            msg = new DTOMessage(e.getMessage());
            LOGGER.log(Level.WARNING, "Error create user", e);
        }
        return msg;
    }

    @Override
    public List<DTOUser> getAll() {
        List<DTOUser> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new DTOUser(rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password")));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error get users", e);
        }
        return users;
    }

    @Override
    public DTOUser getByLogin(String login) {
        List<DTOUser> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            String sql = "SELECT * FROM users WHERE login = (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new DTOUser(rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password")));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error get users", e);
        }
        return users.stream()
                .findFirst()
                .orElse(new DTOUser());
    }

    @Override
    public DTOMessage transferMoney(DTOUserTransfer transfer) {
        DTOMessage msg = new DTOMessage("DAOUser transferMoney");
        int fromId = 0;
        int toId = 0;
        try(Connection connection = ConnectionPool.getConnection()){
            String sql = "SELECT * FROM users WHERE login = (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,transfer.getLoginFrom());
            ResultSet rs = ps.executeQuery();

            rs.next();
            fromId = rs.getInt("id");
            rs.close();

            ps.setString(1,transfer.getLoginTo());
            rs = ps.executeQuery();

            rs.next();
            toId = rs.getInt("id");
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DTOTransfer tr = new DTOTransfer(fromId,toId,transfer.getCount());

        IDAOAccount account = new DAOAccount();
        msg = account.transferMoney(tr);
        return msg;
    }
}
