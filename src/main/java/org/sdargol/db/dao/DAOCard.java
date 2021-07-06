package org.sdargol.db.dao;

import org.sdargol.db.dao.api.IDAOCard;
import org.sdargol.db.h2.ConnectionPool;
import org.sdargol.dto.DTOAccount;
import org.sdargol.dto.DTOCard;
import org.sdargol.dto.request.DTORefill;
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

public class DAOCard implements IDAOCard {
    private final static Logger LOGGER = Log.getLogger(DAOCard.class.getName());

    @Override
    public DTOMessage createCard() {
        Connection connection = null;
        DTOMessage msg = null;
        try {
            connection = ConnectionPool.getConnection();
            String sql = "INSERT INTO cards (status) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, false);
            ps.execute();

            msg = new DTOMessage("Card successfully created");

            ps.close();
            connection.close();
        } catch (SQLException e) {
            msg = new DTOMessage(e.getMessage());
            LOGGER.log(Level.WARNING, "Error create card", e);
        }
        return msg;
    }

    @Override
    public List<DTOCard> getAllCards() {
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
        return null;
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
}
