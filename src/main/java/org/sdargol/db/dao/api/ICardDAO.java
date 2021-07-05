package org.sdargol.db.dao.api;

import org.sdargol.dto.AccountDTO;
import org.sdargol.dto.CardDTO;
import org.sdargol.dto.request.AddMoneyRequestDTO;

import java.util.List;

public interface ICardDAO {
    CardDTO createCard();
    List<CardDTO> getAllCards();
    AccountDTO addMoney(AddMoneyRequestDTO req);
    AccountDTO getBalance(String cardNumber);
}
