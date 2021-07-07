package org.sdargol.db.dao.api;

import org.sdargol.dto.DTOAccount;
import org.sdargol.dto.DTOCard;
import org.sdargol.dto.request.DTORefill;
import org.sdargol.dto.response.DTOMessage;

import java.util.List;

public interface IDAOCard {
    DTOMessage create(Long number);
    List<DTOCard> getAll();
    DTOAccount addMoney(DTORefill req);
    DTOAccount getBalance(Long cardNumber);
}
