package org.sdargol.http.filters;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import org.sdargol.utils.Log;

import java.io.IOException;
import java.util.logging.Logger;

public class FilterCORS extends Filter {
    private final static String DESCRIPTION = "Filter for CORS";
    private final static Logger LOGGER = Log.getLogger(FilterCORS.class.getName());

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
        exchange.getResponseHeaders().add("Access-Control-Allow-Credentials-Header", "*");
        LOGGER.info("CORS Filter run");
        chain.doFilter(exchange);
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
