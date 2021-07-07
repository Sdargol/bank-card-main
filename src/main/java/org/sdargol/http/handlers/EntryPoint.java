package org.sdargol.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.sdargol.controllers.core.response.ResponseEntity;
import org.sdargol.http.url.URLParser;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class EntryPoint implements HttpHandler {
    private final URLParser urlParser = new URLParser();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ResponseEntity response = urlParser.run(exchange);

        try {
            exchange.sendResponseHeaders(response.getStatus(), response.getJson().length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getJson().getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
