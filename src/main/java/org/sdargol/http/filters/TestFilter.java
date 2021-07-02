package org.sdargol.http.filters;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import org.sdargol.json.jwt.JWTProvider;
import org.sdargol.utils.Log;

import java.io.IOException;
import java.util.logging.Logger;

public class TestFilter extends Filter {
    private final static String DESCRIPTION = "Filter for testing";
    private final static Logger LOGGER = Log.getLogger(TestFilter.class.getName());

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        LOGGER.info("Run TestFilter");

        /*exchange.getRequestHeaders()
                .entrySet()
                .forEach(System.out::println);*/

        JWTProvider jwt = new JWTProvider();

        LOGGER.info(jwt.generateToken("admin"));

        String token = jwt.generateToken("admin");
        String name = jwt.getLoginFromToken(token);
        System.out.println("Hello " + name);

        chain.doFilter(exchange);
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
