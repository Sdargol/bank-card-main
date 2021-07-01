package ord.sdargol.http.server;

import com.sun.net.httpserver.HttpServer;
import ord.sdargol.http.handlers.TestHandler;
import ord.sdargol.utils.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Log.getLogger(Server.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private final ExecutorService executorService = Executors.newFixedThreadPool(7);

    public void start(){
        String address = HOST + ":" + PORT;

        try {
            HttpServer httpServer = HttpServer.create(
                    new InetSocketAddress(HOST, PORT), 0);
            httpServer.createContext("/test", new TestHandler());
            httpServer.setExecutor(executorService);
            httpServer.start();
            LOGGER.info("Server success started on " + address);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error start Server on " + address, e);
        }
    }
}
