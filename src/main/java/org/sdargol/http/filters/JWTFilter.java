package org.sdargol.http.filters;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import org.sdargol.utils.Log;

import java.io.IOException;
import java.util.logging.Logger;

public class JWTFilter extends Filter {
    private final static String DESCRIPTION = "JWT filter";
    private final static Logger LOGGER = Log.getLogger(JWTFilter.class.getName());

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        LOGGER.info("JWT Filter run");
        chain.doFilter(exchange);
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
