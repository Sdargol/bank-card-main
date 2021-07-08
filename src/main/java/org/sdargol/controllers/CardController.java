package org.sdargol.controllers;

import org.sdargol.controllers.annotation.Mapping;
import org.sdargol.controllers.annotation.RestController;
import org.sdargol.controllers.core.api.IController;
import org.sdargol.controllers.core.request.BaseRequestEntity;
import org.sdargol.controllers.core.request.RequestEntity;
import org.sdargol.controllers.core.response.Response;
import org.sdargol.controllers.core.response.ResponseEntity;
import org.sdargol.controllers.method.HTTPMethod;
import org.sdargol.db.dao.DAOCard;
import org.sdargol.db.dao.api.IDAOCard;
import org.sdargol.dto.DTOAccount;
import org.sdargol.dto.DTOCard;
import org.sdargol.dto.request.DTOCreateCard;
import org.sdargol.dto.request.DTORefill;
import org.sdargol.dto.response.DTOMessage;
import org.sdargol.http.security.Context;
import org.sdargol.http.security.annotation.Security;
import org.sdargol.json.JSONConverter;

import java.util.List;

@RestController(url = "/api/v1/cards")
@Security()
public class CardController implements IController {
    private final static IDAOCard card = new DAOCard();

    @Mapping(url = "/api/v1/cards", httpMethod = HTTPMethod.POST)
    public ResponseEntity<DTOMessage> createCard(BaseRequestEntity req){
        DTOCreateCard dcc = JSONConverter.toJavaObject(
                req.getExchange().getRequestBody(),
                DTOCreateCard.class);

        return Response.ok(card.create(dcc.getNumber()));
    }

    @Mapping(url = "/api/v1/cards")
    public ResponseEntity<List<DTOCard>> getAll(BaseRequestEntity req){
        return Response.ok(card.getAll());
    }

    @Mapping(url = "/api/v1/cards/money", httpMethod = HTTPMethod.POST)
    public ResponseEntity<DTOAccount> addMoney(BaseRequestEntity req){
        DTORefill refill = JSONConverter.toJavaObject(req.getExchange().getRequestBody(), DTORefill.class);
        DTOAccount account = card.addMoney(refill);
        return Response.ok(account);
    }

    @Mapping(url = "/api/v1/cards/money/{number}")
    public ResponseEntity<DTOAccount> getBalance(RequestEntity req){
        DTOAccount account = card.getBalance(Long.parseLong(req.getUrlVariable().get("number")));
        //System.out.println("In Controller: " + Context.getContext().get().getFirst() + " " + Context.getContext().get().getSecond());
        return Response.ok(account);
    }
}
