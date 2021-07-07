package org.sdargol.http.server;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpServer;
import org.sdargol.http.filters.FilterCORS;
import org.sdargol.http.filters.FilterJSON;
import org.sdargol.http.filters.FilterJWT;
import org.sdargol.http.handlers.Auth;
import org.sdargol.http.handlers.EntryPoint;
import org.sdargol.utils.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Log.getLogger(Server.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public void start(){
        String address = HOST + ":" + PORT;
        try {
            HttpServer httpServer = HttpServer.create(
                    new InetSocketAddress(HOST, PORT), 0);

            Set<Filter> filters = new LinkedHashSet<>(Arrays.asList(
                    new FilterCORS(),
                    new FilterJSON(),
                    new FilterJWT()
            ));

            boolean bContext = httpServer.createContext("/", new EntryPoint())
                    .getFilters()
                    .addAll(filters);

            if(bContext){
                LOGGER.info("Context \" \\ \" created");
            }

            Set<Filter> authFilters = new LinkedHashSet<>(Arrays.asList(
                    new FilterCORS(),
                    new FilterJSON()
            ));

            httpServer.createContext("/auth", new Auth())
                    .getFilters()
                    .addAll(authFilters);

            //httpServer.createContext("/", new TestHandler());

            httpServer.setExecutor(executorService);
            httpServer.start();
            LOGGER.info("Server success started on " + address);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error start Server on " + address, e);
        }
    }
}
