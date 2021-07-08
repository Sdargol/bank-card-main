package org.sdargol.db.dao;

import org.sdargol.db.dao.api.IDAOCard;
import org.sdargol.db.h2.ConnectionPool;
import org.sdargol.dto.DTOAccount;
import org.sdargol.dto.DTOCard;
import org.sdargol.dto.request.DTOConfirm;
import org.sdargol.dto.request.DTORefill;
import org.sdargol.dto.response.DTOMessage;
import org.sdargol.utils.Log;
import sun.security.provider.SHA;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOCard implements IDAOCard {
    private final static Logger LOGGER = Log.getLogger(DAOCard.class.getName());

    @Override
    public DTOMessage create(Long number) {
        DTOMessage msg = null;
        try(Connection connection = ConnectionPool.getConnection();) {
            connection.setAutoCommit(false);
            String sql = "INSERT INTO cards (status) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1, false);
            ps.execute();

            //штука, отвечающая за последний id
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int cardId = rs.getInt(1);

            sql = "SELECT * FROM accounts WHERE number = (?)";
            ps = connection.prepareStatement(sql);
            ps.setLong(1, number);
            ResultSet accountsSet = ps.executeQuery();

            accountsSet.next();
            int accountId = accountsSet.getInt("id");
            accountsSet.close();

            sql = "INSERT INTO CardsToAccount (account_id, card_id) VALUES (?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setInt(2, cardId);
            ps.execute();

            msg = new DTOMessage("Card successfully created");
            //LOGGER.info(msg.getMsg());

            ps.close();
            connection.commit();
        } catch (SQLException e) {
            msg = new DTOMessage(e.getMessage());
            LOGGER.log(Level.WARNING, "Error create card", e);
        }
        return msg;
    }

    @Override
    public List<DTOCard> getAll() {
        List<DTOCard> cards = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection()) {
            String sql = "SELECT * FROM cards";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                cards.add(new DTOCard(rs.getInt("id"),
                        rs.getLong("number"),
                        rs.getBoolean("status")));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error get cards", e);
        }
        return cards;
    }

    @Override
    public DTOAccount addMoney(DTORefill req) {
        DTOAccount account = new DTOAccount();
        try (Connection connection = ConnectionPool.getConnection()) {
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM cards WHERE number = (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, req.getNumber());
            ResultSet rs = ps.executeQuery();

            rs.next();
            int id = rs.getInt("id");
            rs.close();

            sql = "SELECT * FROM CardsToAccount WHERE card_id = (?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs =  ps.executeQuery();

            rs.next();
            id = rs.getInt("account_id");
            rs.close();

            sql = "SELECT * FROM accounts WHERE id = (?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs =  ps.executeQuery();

            rs.next();
            int money = rs.getInt("money");
            rs.close();

            sql = "UPDATE accounts SET money = (?) WHERE id = (?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1,(req.getMoney() + money));
            ps.setInt(2,id);
            ps.executeUpdate();

            sql = "SELECT * FROM accounts WHERE id = (?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs =  ps.executeQuery();

            rs.next();
            account.setNumber(rs.getLong("number"));
            account.setMoney(rs.getInt("money"));
            account.setId(rs.getInt("id"));

            ps.close();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error add money cards", e);
        }
        return account;
    }

    @Override
    public DTOAccount getBalance(Long cardNumber) {
        DTOAccount account = new DTOAccount();
        try (Connection connection = ConnectionPool.getConnection()) {
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM cards WHERE number = (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, cardNumber);
            ResultSet rs = ps.executeQuery();

            rs.next();
            int id = rs.getInt("id");
            rs.close();

            sql = "SELECT * FROM CardsToAccount WHERE card_id = (?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs =  ps.executeQuery();

            rs.next();
            id = rs.getInt("account_id");
            rs.close();

            sql = "SELECT * FROM accounts WHERE id = (?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs =  ps.executeQuery();

            rs.next();
            account.setId(rs.getInt("id"));
            account.setMoney(rs.getInt("money"));
            account.setNumber(rs.getLong("number"));
            int money = rs.getInt("money");
            rs.close();

            System.out.println("money: " + money);

            ps.close();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error get cards", e);
        }
        return account;
    }

    @Override
    public DTOMessage confirm(DTOConfirm confirm) {
        DTOMessage msg = new DTOMessage();

        try (Connection connection = ConnectionPool.getConnection()) {
            String sql = "UPDATE cards SET status = TRUE WHERE id = (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, confirm.getNumber());
            ps.execute();
            ps.close();

            msg.setMsg("Card with id = " + confirm.getNumber() + " successfully confirm");

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error get cards", e);
        }
        return msg;
    }
}
