package org.sdargol.db.h2;

import org.h2.jdbcx.JdbcConnectionPool;
import org.sdargol.utils.Log;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPool {
    private final static Logger LOGGER = Log.getLogger(ConnectionPool.class.getName());
    private final static JdbcConnectionPool connectionPool;

    static {
        try {
            Class.forName ("org.h2.Driver");
            LOGGER.info("Success load: org.h2.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "Error load org.h2.Driver", e);
        }

        connectionPool = JdbcConnectionPool.create("jdbc:h2:mem:","sa","");
        LOGGER.info("Create JdbcConnectionPool, max connections: "
                + connectionPool.getMaxConnections());
    }

    //не забывать закрывать
    public static Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    public static void dispose(){
        connectionPool.dispose();
    }
}
