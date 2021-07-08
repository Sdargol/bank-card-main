package org.sdargol.http.handlers;


import com.sun.net.httpserver.HttpExchange;
import org.sdargol.controllers.core.response.ResponseEntity;
import org.sdargol.utils.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResponseSender {
    private final static Logger LOGGER = Log.getLogger(ResponseSender.class.getName());

    public static<T> boolean send(final HttpExchange exc, final ResponseEntity<T> res){
        try {
            exc.sendResponseHeaders(res.getStatus(), res.getJson().length());
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error send headers", e);
            return false;
        }

        try (OutputStream os = exc.getResponseBody()) {
            os.write(res.getJson().getBytes(StandardCharsets.UTF_8));
            os.flush();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error send body", e);
            return false;
        }

        return true;
    }
}
