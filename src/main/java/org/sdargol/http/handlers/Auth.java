package org.sdargol.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.sdargol.controllers.core.response.ResponseEntity;
import org.sdargol.dto.response.DTOMessage;
import org.sdargol.json.jwt.JWTProvider;

import java.io.IOException;

public class Auth implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        JWTProvider jwt = new JWTProvider();
        String token = jwt.generateToken("user@gmail.com");

        ResponseEntity<DTOMessage> res = new ResponseEntity<>(200, new DTOMessage(token));
        ResponseSender.send(exchange, res);
    }
}
