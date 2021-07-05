package org.sdargol.controllers.core.request;


import com.sun.net.httpserver.HttpExchange;

public class BaseRequestEntity {
    private final HttpExchange exchange;

    public BaseRequestEntity(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public HttpExchange getExchange() {
        return exchange;
    }
}
