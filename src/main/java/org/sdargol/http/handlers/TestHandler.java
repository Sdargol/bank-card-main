package org.sdargol.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.sdargol.utils.BasicHTML;
import org.sdargol.utils.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class TestHandler implements HttpHandler {
    private static final Logger LOGGER = Log.getLogger(TestHandler.class.getName());

    private void handleResponse(HttpExchange exchange){
        String htmlResponse = "<h1>" +
                "Hello world" +
                "</h1>";
        htmlResponse = BasicHTML.generate(htmlResponse);

        String jsonResponse = "{\"id\":0,\"info\":\"Test\"}";

        //здесь jsonResponse.length() менять
        try {
            exchange.sendResponseHeaders(200, jsonResponse.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //это для json
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        //здесь os.write(jsonResponse.getBytes(StandardCharsets.UTF_8)); менять
        try(OutputStream os = exchange.getResponseBody()){
            os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info(exchange.getRequestURI().getQuery());
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        handleResponse(exchange);
    }
}