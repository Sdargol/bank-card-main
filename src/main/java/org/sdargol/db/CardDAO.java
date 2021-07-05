package org.sdargol.db;

import org.sdargol.db.dao.api.ICardDAO;
import org.sdargol.db.h2.ConnectionPool;
import org.sdargol.dto.AccountDTO;
import org.sdargol.dto.CardDTO;
import org.sdargol.dto.request.AddMoneyRequestDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDAO implements ICardDAO {
    @Override
    public CardDTO createCard() {
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            String sql = "INSERT INTO cards (status) VALUES (?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, false);
            boolean rs = ps.execute();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CardDTO> getAllCards() {
        List<CardDTO> cards = new ArrayList<>();
        try(Connection connection = ConnectionPool.getConnection()) {
            String sql = "SELECT * FROM cards";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                cards.add(new CardDTO(rs.getInt("id"),
                        rs.getLong("number"),
                        rs.getBoolean("status")));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public AccountDTO addMoney(AddMoneyRequestDTO req) {
        return null;
    }

    @Override
    public AccountDTO getBalance(String cardNumber) {
        return null;
    }
}
