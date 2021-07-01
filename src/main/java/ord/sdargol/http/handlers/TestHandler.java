package ord.sdargol.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ord.sdargol.utils.BasicHTML;
import ord.sdargol.utils.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class TestHandler implements HttpHandler {
    private static final Logger LOGGER = Log.getLogger(TestHandler.class.getName());

    private void handleResponse(HttpExchange exchange){
        LOGGER.info("TestHandler run");

        String htmlResponse = "<h1>" +
                "Hello world" +
                "</h1>";
        htmlResponse = BasicHTML.generate(htmlResponse);

        try {
            exchange.sendResponseHeaders(200, htmlResponse.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(OutputStream os = exchange.getResponseBody()){
            os.write(htmlResponse.getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        handleResponse(exchange);
    }
}
