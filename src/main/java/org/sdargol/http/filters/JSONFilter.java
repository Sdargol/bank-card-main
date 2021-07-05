package org.sdargol.http.filters;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import org.sdargol.utils.Log;

import java.io.IOException;
import java.util.logging.Logger;

public class JSONFilter extends Filter {
    private final static String DESCRIPTION = "JSON Filter";
    private final static Logger LOGGER = Log.getLogger(JSONFilter.class.getName());

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        LOGGER.info("JSONFilter run");
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        chain.doFilter(exchange);
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
