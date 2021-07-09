package org.sdargol.controllers.core;

import org.sdargol.http.url.URLParser;
import org.sdargol.utils.Log;

import java.util.logging.Logger;

public final class SControllerManager {
    private static final Logger LOGGER = Log.getLogger(SControllerManager.class.getName());
    private static final ControllerManager CONTROLLER_MANAGER;

    static {
        CONTROLLER_MANAGER = new ControllerManager();
        boolean bInit = CONTROLLER_MANAGER.init();

        if(bInit){
            LOGGER.info("SControllerManager success load");
        }else{
            LOGGER.info("SControllerManager error load");
        }
    }

    public static ControllerManager getInstance(){
        return CONTROLLER_MANAGER;
    }
}
