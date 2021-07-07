package org.sdargol.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.sdargol.json.jwt.JWTProvider;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Auth implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        JWTProvider jwt = new JWTProvider();
        String token = jwt.generateToken("admin@gmail.com");
        String jsonResult = String.format("{\"token\": %s}", token);

        try {
            exchange.sendResponseHeaders(200, jsonResult.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(jsonResult.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
