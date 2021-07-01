package ord.sdargol.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Log {
    private static Logger LOGGER;

    static {
        InputStream is = Log.class.getClassLoader()
                .getResourceAsStream("logger.properties");
        try {
            LogManager.getLogManager().readConfiguration(is);
            LOGGER = Logger.getLogger(Log.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger(String className){
        return Logger.getLogger(className);
    }

    public static void info(String msg){
        LOGGER.info(msg);
    }
}
