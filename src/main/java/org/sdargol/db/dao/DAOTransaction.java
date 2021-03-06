package org.sdargol.db.dao;

import org.sdargol.db.dao.api.IDAOTransaction;
import org.sdargol.db.dao.core.IDAO;
import org.sdargol.db.h2.ConnectionPool;
import org.sdargol.dto.DTOTransaction;
import org.sdargol.dto.request.DTOConfirm;
import org.sdargol.dto.response.DTOMessage;
import org.sdargol.utils.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOTransaction implements IDAOTransaction, IDAO {
    private final static Logger LOGGER = Log.getLogger(DAOTransaction.class.getName());

    @Override
    public DTOMessage create(DTOTransaction transaction) {
        DTOMessage msg = null;
        try(Connection connection = ConnectionPool.getConnection()) {
            String sql = "INSERT INTO transactions (from_user_id, to_user_id, counts, status) VALUES (?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, transaction.getFromAccountId());
            ps.setInt(2, transaction.getToAccountId());
            ps.setInt(3, transaction.getCount());
            ps.setBoolean(4, false);
            ps.execute();

            msg = new DTOMessage(String.format("Transaction successfully created from = %d, to = %d, count = %d, status = %b",
                    transaction.getFromAccountId(),
                    transaction.getToAccountId(),
                    transaction.getCount(),
                    transaction.isStatus()
            ));

            ps.close();
        } catch (SQLException e) {
            msg = new DTOMessage(e.getMessage());
            LOGGER.log(Level.WARNING, "Error create transaction", e);
        }
        return msg;
    }

    /*@Override
    public DTOMessage confirm(DTOConfirm confirm) {
        DTOMessage msg;
        try (Connection connection = ConnectionPool.getConnection()) {
            String sql = "UPDATE transactions SET status = (?) WHERE id = (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setInt(2,confirm.getNumber());
            int row = ps.executeUpdate();

            msg = new DTOMessage("Transaction with id = "+ confirm.getNumber() +" successfully confirm, row = " + row);

            ps.close();
        } catch (SQLException e) {
            msg = new DTOMessage(e.getMessage());
            LOGGER.log(Level.WARNING, "Error confirm transaction", e);
        }
        return msg;
    }*/

    @Override
    public DTOMessage confirm(DTOConfirm confirm) {
        DTOMessage msg;
        try (Connection connection = ConnectionPool.getConnection()) {
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM transactions WHERE id = (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, confirm.getNumber());
            ResultSet rs = ps.executeQuery();

            rs.next();
            DTOTransaction transaction = new DTOTransaction();
            transaction.setId(confirm.getNumber());
            transaction.setFromAccountId(rs.getInt("from_account_id"));
            transaction.setToAccountId(rs.getInt("to_account_id"));
            transaction.setCount(rs.getInt("counts"));
            transaction.setStatus(rs.getBoolean("status"));
            rs.close();

            // ?????????? ???????????? ?? from
            sql = "SELECT * FROM accounts WHERE id = (?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, transaction.getFromAccountId());
            rs = ps.executeQuery();

            rs.next();
            int fromMoney = rs.getInt("money");
            rs.close();

            // ?????????? ???????????? ?? to
            sql = "SELECT * FROM accounts WHERE id = (?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, transaction.getToAccountId());
            rs = ps.executeQuery();

            rs.next();
            int toMoney = rs.getInt("money");
            rs.close();

            // ???????????? ??????????????
            fromMoney = fromMoney - transaction.getCount();
            toMoney = toMoney + transaction.getCount();

            // ?????????????????? ??????????
            sql = "UPDATE accounts SET money = (?) WHERE id = (?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, fromMoney);
            ps.setInt(2, transaction.getFromAccountId());
            ps.executeUpdate();

            sql = "UPDATE accounts SET money = (?) WHERE id = (?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, toMoney);
            ps.setInt(2, transaction.getToAccountId());
            ps.executeUpdate();

            // ???????????????????????? ????????????????????
            sql = "UPDATE transactions SET status = (?) WHERE id = (?)";
            ps = connection.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setInt(2, confirm.getNumber());
            int row = ps.executeUpdate();

            //System.out.println(transaction);

            msg = new DTOMessage("Transaction with id = "+ confirm.getNumber() +" successfully confirm, row = " + row);

            ps.close();
            connection.commit();
        } catch (SQLException e) {
            msg = new DTOMessage(e.getMessage());
            LOGGER.log(Level.WARNING, "Error confirm transaction", e);
        }
        return msg;
    }

    @Override
    public List<DTOTransaction> getAll() {
        List<DTOTransaction> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            String sql = "SELECT * FROM transactions";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new DTOTransaction(rs.getInt("id"),
                        rs.getInt("from_account_id"),
                        rs.getInt("to_account_id"),
                        rs.getInt("counts"),
                        rs.getBoolean("status")
                ));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error get transactions", e);
        }
        return users;
    }

    @Override
    public DTOTransaction getById(int id) {
        List<DTOTransaction> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            String sql = "SELECT * FROM transactions WHERE id = (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new DTOTransaction(rs.getInt("id"),
                        rs.getInt("from_user_id"),
                        rs.getInt("to_user_id"),
                        rs.getInt("counts"),
                        rs.getBoolean("status")
                ));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error get transactions", e);
        }
        return users.stream().findFirst().orElse(new DTOTransaction());
    }
}
