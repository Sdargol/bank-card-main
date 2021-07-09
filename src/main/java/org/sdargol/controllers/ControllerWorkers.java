package org.sdargol.controllers;

import org.sdargol.controllers.annotation.Mapping;
import org.sdargol.controllers.annotation.RestController;
import org.sdargol.controllers.core.api.IController;
import org.sdargol.controllers.core.request.BaseRequestEntity;
import org.sdargol.controllers.core.response.Response;
import org.sdargol.controllers.core.response.ResponseEntity;
import org.sdargol.controllers.method.HTTPMethod;
import org.sdargol.db.dao.DAOAccount;
import org.sdargol.db.dao.DAOCard;
import org.sdargol.db.dao.DAOTransaction;
import org.sdargol.db.dao.DAOUser;
import org.sdargol.db.dao.api.IDAOCard;
import org.sdargol.db.dao.api.IDAOTransaction;
import org.sdargol.db.dao.api.IDAOUser;
import org.sdargol.db.dao.core.SManagerDAO;
import org.sdargol.dto.DTOTransaction;
import org.sdargol.dto.DTOUser;
import org.sdargol.dto.request.DTOConfirm;
import org.sdargol.dto.response.DTOMessage;
import org.sdargol.http.security.annotation.Security;
import org.sdargol.json.JSONConverter;

import java.util.List;

@RestController(url = "/api/v1/workers")
@Security
public class ControllerWorkers implements IController {
    private final IDAOUser users = SManagerDAO.getInstance().get(DAOUser.class);
    private final IDAOTransaction transaction = SManagerDAO.getInstance().get(DAOTransaction.class);
    private final IDAOCard cards = SManagerDAO.getInstance().get(DAOCard.class);

    @Mapping(url = "/api/v1/workers/users/create", httpMethod = HTTPMethod.POST)
    public ResponseEntity<DTOMessage> createUser(BaseRequestEntity req){
        DTOUser user = JSONConverter.toJavaObject(req.getExchange().getRequestBody(), DTOUser.class);
        return Response.ok(users.create(user));
    }

    @Mapping(url = "/api/v1/workers/cards/confirm", httpMethod = HTTPMethod.POST)
    public ResponseEntity<DTOMessage> confirmCard(BaseRequestEntity req){
        DTOConfirm confirm = JSONConverter.toJavaObject(req.getExchange().getRequestBody(), DTOConfirm.class);
        return Response.ok(cards.confirm(confirm));
    }

    @Mapping(url = "/api/v1/workers/transactions/confirm", httpMethod = HTTPMethod.POST)
    public ResponseEntity<DTOMessage> confirmTransaction(BaseRequestEntity req){
        DTOConfirm confirm = JSONConverter.toJavaObject(req.getExchange().getRequestBody(), DTOConfirm.class);
        return Response.ok(transaction.confirm(confirm));
    }

    @Mapping(url = "/api/v1/workers/transactions")
    public ResponseEntity<List<DTOTransaction>> getTransaction(BaseRequestEntity req){
        return Response.ok(transaction.getAll());
    }
}
