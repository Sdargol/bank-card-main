package org.sdargol.db.dao.api;

import org.sdargol.dto.DTOUser;
import org.sdargol.dto.request.DTORefill;
import org.sdargol.dto.response.DTOMessage;

import java.util.List;

public interface IDAOUser {
    DTOMessage create(DTOUser user);
    List<DTOUser> getAll();
    DTOUser getByLogin(String login);
    DTOMessage addMoney(DTORefill refill);
}
