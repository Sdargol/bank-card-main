package org.sdargol.db.dao.api;

import org.sdargol.dto.DTOAccount;
import org.sdargol.dto.request.DTOTransfer;
import org.sdargol.dto.response.DTOMessage;

import java.util.List;

public interface IDAOAccount {
    DTOMessage create();
    List<DTOAccount> getAll();
    DTOMessage update(DTOAccount account);
    DTOMessage transferMoney(DTOTransfer transfer);
}
