package org.sdargol.http.filters;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import org.sdargol.db.dao.DAORoles;
import org.sdargol.db.dao.DAOUser;
import org.sdargol.db.dao.api.IDAORoles;
import org.sdargol.db.dao.api.IDAOUser;
import org.sdargol.dto.DTORoles;
import org.sdargol.dto.DTOUser;
import org.sdargol.http.security.Context;
import org.sdargol.http.security.Pair;
import org.sdargol.json.jwt.JWTProvider;
import org.sdargol.utils.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class FilterJWT extends Filter {
    private final static String AUTHORIZATION = "Authorization";
    private final static String DESCRIPTION = "JWT filter";

    private final static Logger LOGGER = Log.getLogger(FilterJWT.class.getName());

    private final static JWTProvider jwt = new JWTProvider();
    private final static IDAOUser users = new DAOUser();
    private final static IDAORoles roles = new DAORoles();

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        String bearer = exchange.getRequestHeaders().getFirst(AUTHORIZATION);
        if(bearer == null) chain.doFilter(exchange);

        LOGGER.info("JWT Filter run");

        String token = getTokenFromRequest(exchange);

        if (jwt.validateToken(token)) {
            String userLogin = jwt.getLoginFromToken(token);
            LOGGER.info("Hello " + userLogin);
            DTOUser user = users.getByLogin(userLogin);

            if(user.getName() == null){
                System.out.println("Не авторизован");
                return;
            }

            DTORoles rolesUser = roles.getByUserId(user.getId());

            if(Context.getContext().get() != null){
                Context.getContext().remove();
            }

            Context.getContext().set(Pair.create(userLogin, rolesUser));
            //CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(userLogin);
            //UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            //SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(exchange);
            return;
        }

        System.out.println("Дошли");

        try {
            exchange.sendResponseHeaders(403, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getTokenFromRequest(HttpExchange exchange) {
        String bearer = exchange.getRequestHeaders().getFirst("Authorization");
        if (bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }
}
