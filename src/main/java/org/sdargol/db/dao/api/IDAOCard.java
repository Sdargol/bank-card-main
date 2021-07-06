package org.sdargol.db.dao.api;

import org.sdargol.dto.DTOAccount;
import org.sdargol.dto.DTOCard;
import org.sdargol.dto.request.DTORefill;
import org.sdargol.dto.response.DTOMessage;

import java.util.List;

public interface IDAOCard {
    DTOMessage createCard();
    List<DTOCard> getAllCards();
    DTOAccount addMoney(DTORefill req);
    DTOAccount getBalance(Long cardNumber);
}
