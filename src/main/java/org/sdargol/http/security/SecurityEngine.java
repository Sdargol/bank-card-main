package org.sdargol.http.security;

import org.sdargol.controllers.core.ControllerManager;
import org.sdargol.controllers.core.api.IController;
import org.sdargol.controllers.core.SControllerManager;
import org.sdargol.dto.DTORoles;
import org.sdargol.http.security.annotation.Security;
import org.sdargol.utils.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

//c - class, u - user, a - annotation
public class SecurityEngine {
    private final static Logger LOGGER = Log.getLogger(SecurityEngine.class.getName());
    private static final ControllerManager CONTROLLER_MANAGER = SControllerManager.getInstance();

    public boolean checkRoles(final String requestUrl, final DTORoles uRoles){
        Class<? extends IController> cController = CONTROLLER_MANAGER.getController(requestUrl).getClass();

        Security aSecurity = cController.getDeclaredAnnotation(Security.class);

        Set<Roles> cRoles = new HashSet<>(Arrays.asList(aSecurity.hasRoles()));

        return cRoles.stream().anyMatch(r -> uRoles.getRoles().contains(r));
    }

    public boolean checkSecurity(String requestUrl){
        //если есть контроллер соответствующий URL, то достаем его и проверяем, есть ли аннотация
        if(CONTROLLER_MANAGER.contains(requestUrl)){
            IController controller = CONTROLLER_MANAGER.getController(requestUrl);
            boolean aPresent = controller.getClass().isAnnotationPresent(Security.class);
            if(!aPresent){
                LOGGER.info("Найден контроллер: " + controller.getClass().getName() +
                        " аннотация Security отсутствует, передаем запрос дальше");
                return false;
            }
        }
        return true;
    }
}
