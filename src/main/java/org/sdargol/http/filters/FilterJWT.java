package org.sdargol.http.filters;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import org.sdargol.controllers.core.response.Response;
import org.sdargol.db.dao.DAORoles;
import org.sdargol.db.dao.DAOUser;
import org.sdargol.db.dao.api.IDAORoles;
import org.sdargol.db.dao.api.IDAOUser;
import org.sdargol.dto.DTORoles;
import org.sdargol.dto.DTOUser;
import org.sdargol.http.handlers.ResponseSender;
import org.sdargol.http.security.Context;
import org.sdargol.http.security.Pair;
import org.sdargol.http.security.SecurityEngine;
import org.sdargol.json.jwt.JWTProvider;
import org.sdargol.utils.Log;

import java.io.IOException;
import java.util.logging.Logger;

public class FilterJWT extends Filter {
    private final static String AUTHORIZATION = "Authorization";
    private final static String DESCRIPTION = "JWT filter";

    private final static Logger LOGGER = Log.getLogger(FilterJWT.class.getName());

    private final static JWTProvider jwt = new JWTProvider();
    private final static IDAOUser users = new DAOUser();
    private final static IDAORoles roles = new DAORoles();
    private final static SecurityEngine security = new SecurityEngine();

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        LOGGER.info("JWT Filter run");
        String url = exchange.getRequestURI().getPath();

        String bearer = exchange.getRequestHeaders().getFirst(AUTHORIZATION);

        //если запрос без токена и OPTION (обычно браузер отправляет, поэтому передаем дальше)
        if(exchange.getRequestMethod().equals("OPTIONS") && bearer == null){
            System.out.println("OPTIONS");
            chain.doFilter(exchange);
            return;
        }

        //если есть контроллер соответствующий URL, то достаем его и проверяем, есть ли аннотация
        if(!security.checkSecurity(url)){
            System.out.println("контроллер не защищен");
            chain.doFilter(exchange);
            return;
        }

        if(bearer == null){
            ResponseSender.send(exchange, Response.unauthorized());
            return;
        }

        String token = getTokenFromRequest(exchange);
        boolean validToken = jwt.validateToken(token);

        if (validToken) {
            String userLogin = jwt.getLoginFromToken(token);
            LOGGER.info("Hello " + userLogin);

            DTOUser user = users.getByLogin(userLogin);

            if(user.getName() == null){
                System.out.println("Пользователь не найден");
                return;
            }

            DTORoles rolesUser = roles.getByUserId(user.getId());

            boolean bCheckRoles = security.checkRoles(url, rolesUser);

            //если у пользователя нет подходящей роли, то запрещаем
            if(!bCheckRoles){
                ResponseSender.send(exchange, Response.forbidden());
                return;
            }

            if(Context.getContext().get() != null){
                System.out.println("Удаляем из контекста");
                Context.getContext().remove();
            }

            Context.getContext().set(Pair.create(userLogin, rolesUser));

            chain.doFilter(exchange);
        }else{
            ResponseSender.send(exchange,Response.forbidden());
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
