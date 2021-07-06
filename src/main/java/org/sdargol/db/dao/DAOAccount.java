package org.sdargol.db.dao;

import org.sdargol.db.dao.api.IDAOAccount;
import org.sdargol.db.dao.api.IDAOTransaction;
import org.sdargol.db.h2.ConnectionPool;
import org.sdargol.dto.DTOAccount;
import org.sdargol.dto.DTOTransaction;
import org.sdargol.dto.request.DTOTransfer;
import org.sdargol.dto.response.DTOMessage;
import org.sdargol.utils.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOAccount implements IDAOAccount {
    private final static Logger LOGGER = Log.getLogger(DAOAccount.class.getName());

    @Override
    public DTOMessage create() {
        DTOMessage msg = null;
        try(Connection connection = ConnectionPool.getConnection()) {
            String sql = "INSERT INTO accounts (money) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, 0);

            ps.execute();

            //штука, отвечающая за последний id
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            System.out.println(rs.getInt(1));

            msg = new DTOMessage("Account successfully created");
            ps.close();
        } catch (SQLException e) {
            msg = new DTOMessage(e.getMessage());
            LOGGER.log(Level.WARNING, "Error create account", e);
        }
        return msg;
    }

    @Override
    public List<DTOAccount> getAll() {
        List<DTOAccount> accounts = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            String sql = "SELECT * FROM accounts";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accounts.add(new DTOAccount(rs.getInt("id"),
                        rs.getLong("number"),
                        rs.getInt("money")));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error get cards", e);
        }
        return accounts;
    }

    @Override
    public DTOMessage update(DTOAccount account) {
        DTOMessage msg = null;
        try(Connection connection = ConnectionPool.getConnection()) {
            String sql = "UPDATE accounts SET money = (?) WHERE id = (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account.getMoney());
            ps.setInt(2, account.getId());
            int rs = ps.executeUpdate();

            if (rs > 0) {
                msg = new DTOMessage("Account successfully update");
            } else {
                msg = new DTOMessage("Account not update");
            }

            ps.close();
        } catch (SQLException e) {
            msg = new DTOMessage(e.getMessage());
            LOGGER.log(Level.WARNING, "Error update account", e);
        }
        return msg;
    }

    @Override
    public DTOMessage transferMoney(DTOTransfer transfer) {
        //Проверить что у from хватает денег на счету
        //Создать транзакцию
        int fromAccountNumber = 0;
        int toAccountNumber = 0;

        try(Connection c = ConnectionPool.getConnection()){
            c.setAutoCommit(false);

            String sql = "SELECT * FROM AccountToUser WHERE user_id = (?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, transfer.getFrom());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                fromAccountNumber = rs.getInt("account_id");
            }
            rs.close();

            sql = "SELECT * FROM AccountToUser WHERE user_id = (?)";
            ps = c.prepareStatement(sql);
            ps.setInt(1, transfer.getTo());
            rs = ps.executeQuery();

            while (rs.next()) {
                toAccountNumber = rs.getInt("account_id");
            }
            rs.close();

            System.out.println("from: " + fromAccountNumber + " to " + toAccountNumber);

            //sql = "INSERT INTO transactions (from_user_id, to_user_id, counts, status) VALUES (?, ?, ?, ?);";
            //ps = c.prepareStatement(sql);
            //ps.setInt(1,fromAccountNumber);
            //ps.setInt(2,toAccountNumber);
            //ps.setInt(3, transfer.getCount());
            //ps.setBoolean(4,false);
            //ps.execute();

            ps.close();
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        IDAOTransaction transaction = new DAOTransaction();
        DTOTransaction dtoTransaction = new DTOTransaction(0,
                fromAccountNumber,
                toAccountNumber,
                transfer.getCount(),
                false
        );

        return transaction.create(dtoTransaction);
    }
}
