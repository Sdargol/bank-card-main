package org.sdargol.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.sdargol.http.url.URLParser;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class EntryPoint implements HttpHandler {
    private final URLParser urlParser = new URLParser();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String jsonResponse = "{\"id\":0,\"info\":\"Test\"}";

        urlParser.run(exchange);

        //здесь jsonResponse.length() менять
        try {
            exchange.sendResponseHeaders(200, jsonResponse.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //здесь os.write(jsonResponse.getBytes(StandardCharsets.UTF_8)); менять
        try(OutputStream os = exchange.getResponseBody()){
            os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
