package org.sdargol.controllers.core.request;

import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

public class RequestEntity extends BaseRequestEntity{
    private final Map<String, String> urlVariable;

    public RequestEntity(HttpExchange exchange, Map<String, String> urlVariable) {
        super(exchange);
        this.urlVariable = urlVariable;
    }

    public Map<String, String> getUrlVariable() {
        return urlVariable;
    }
}
