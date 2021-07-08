package org.sdargol.controllers;

import org.sdargol.controllers.annotation.Mapping;
import org.sdargol.controllers.annotation.RestController;
import org.sdargol.controllers.core.api.IController;
import org.sdargol.controllers.core.request.BaseRequestEntity;
import org.sdargol.controllers.core.response.Response;
import org.sdargol.controllers.core.response.ResponseEntity;
import org.sdargol.controllers.method.HTTPMethod;
import org.sdargol.db.dao.DAOUser;
import org.sdargol.db.dao.api.IDAOUser;
import org.sdargol.dto.DTOUser;
import org.sdargol.dto.request.DTOUserTransfer;
import org.sdargol.dto.response.DTOMessage;
import org.sdargol.http.security.annotation.Security;
import org.sdargol.json.JSONConverter;

import java.util.List;

@RestController(url = "/api/v1/users")
@Security
public class UserController implements IController {
    private final static IDAOUser users = new DAOUser();

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
        DTOUserTransfer tr = new DTOUserTransfer("admin@gmail.com",
                "user@gmail.com",
                555);
        return Response.ok(users.transferMoney(tr));
    }
}
