package org.sdargol.db.dao.api;

import org.sdargol.dto.DTOTransaction;
import org.sdargol.dto.request.DTOConfirm;
import org.sdargol.dto.response.DTOMessage;

import java.util.List;

public interface IDAOTransaction {
    DTOMessage create(DTOTransaction transaction);
    DTOMessage confirm(DTOConfirm confirm);
    List<DTOTransaction> getAll();
    DTOTransaction getById(int id);
}
