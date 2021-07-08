package org.sdargol.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.sdargol.controllers.core.response.ResponseEntity;
import org.sdargol.http.security.Context;
import org.sdargol.http.url.URLParser;

import java.io.IOException;

public class EntryPoint implements HttpHandler {
    private final URLParser urlParser = new URLParser();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ResponseSender.send(exchange, run(exchange));
        if(Context.getContext().get() != null){
            System.out.println("Удаляем из контекста (в handle)");
            Context.getContext().remove();
        }
    }

    private <T> ResponseEntity<T> run(HttpExchange exchange){
        return urlParser.run(exchange);
    }
}
