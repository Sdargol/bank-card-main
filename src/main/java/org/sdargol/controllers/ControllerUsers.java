package org.sdargol.controllers;

import org.sdargol.controllers.annotation.Mapping;
import org.sdargol.controllers.annotation.RestController;
import org.sdargol.controllers.core.api.IController;
import org.sdargol.controllers.core.request.BaseRequestEntity;
import org.sdargol.controllers.core.response.Response;
import org.sdargol.controllers.core.response.ResponseEntity;
import org.sdargol.controllers.method.HTTPMethod;
import org.sdargol.db.dao.DAOCard;
import org.sdargol.db.dao.DAOUser;
import org.sdargol.db.dao.api.IDAOUser;
import org.sdargol.db.dao.core.SManagerDAO;
import org.sdargol.dto.DTOUser;
import org.sdargol.dto.request.DTORefill;
import org.sdargol.dto.request.DTOUserTransfer;
import org.sdargol.dto.response.DTOMessage;
import org.sdargol.http.security.Context;
import org.sdargol.http.security.annotation.Security;
import org.sdargol.json.JSONConverter;

import java.util.List;

@RestController(url = "/api/v1/users")
@Security
public class ControllerUsers implements IController {
    private final IDAOUser users = SManagerDAO.getInstance().get(DAOUser.class);

    @Mapping(url = "/api/v1/users", httpMethod = HTTPMethod.POST)
    public ResponseEntity<DTOMessage> create(BaseRequestEntity req) {
        DTOUser user = JSONConverter.toJavaObject(req.getExchange().getRequestBody(), DTOUser.class);
        return Response.ok(users.create(user));
    }

    @Mapping(url = "/api/v1/users")
    public ResponseEntity<List<DTOUser>> getAll(BaseRequestEntity req) {
        return Response.ok(users.getAll());
    }

    @Mapping(url = "/api/v1/users/transfer-money", httpMethod = HTTPMethod.POST)
    public ResponseEntity<DTOMessage> transferMoney(BaseRequestEntity req) {
        DTOUserTransfer transfer = JSONConverter.toJavaObject(req.getExchange().getRequestBody(), DTOUserTransfer.class);
        transfer.setLoginFrom(Context.getContext().get().getFirst());

        System.out.println(transfer);
        return Response.ok(users.transferMoney(transfer));
    }
}
