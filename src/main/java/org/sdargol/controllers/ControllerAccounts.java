package org.sdargol.controllers;

import org.sdargol.controllers.annotation.Mapping;
import org.sdargol.controllers.annotation.RestController;
import org.sdargol.controllers.core.api.IController;
import org.sdargol.controllers.core.request.BaseRequestEntity;
import org.sdargol.controllers.core.response.Response;
import org.sdargol.controllers.core.response.ResponseEntity;
import org.sdargol.db.dao.DAOAccount;
import org.sdargol.db.dao.DAOCard;
import org.sdargol.db.dao.api.IDAOAccount;
import org.sdargol.db.dao.core.SManagerDAO;
import org.sdargol.dto.DTOAccount;
import org.sdargol.http.security.annotation.Security;

import java.util.List;

@RestController(url = "/api/v1/accounts")
@Security
public class ControllerAccounts implements IController {
    private final IDAOAccount accounts = SManagerDAO.getInstance().get(DAOAccount.class);

    @Mapping(url = "/api/v1/accounts")
    public ResponseEntity<List<DTOAccount>> getAll(BaseRequestEntity req){
        return Response.ok(accounts.getAll());
    }
}
