package org.sdargol.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Props {
    private final static Properties property = new Properties();
    private final static Logger LOGGER = Log.getLogger(Props.class.getName());

    static {
        InputStream is = Log.class.getClassLoader()
                .getResourceAsStream("application.properties");
        try {
            property.load(is);
            LOGGER.info("Props success load");
        } catch (IOException e) {
            LOGGER.log(Level.WARNING,"Props error load", e);
        }
    }

    public static String getProperty(String key){
        return property.getProperty(key);
    }

    public static int getPropertyInt(String key){
        return Integer.parseInt(property.getProperty(key));
    }
}
