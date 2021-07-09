package org.sdargol.controllers.core;

import org.reflections.Reflections;
import org.sdargol.controllers.annotation.RestController;
import org.sdargol.controllers.core.api.IController;
import org.sdargol.utils.Log;
import org.sdargol.utils.Props;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ControllerManager {
    private final Map<String, IController> storageControllers;
    private final Set<String> urls;

    private static final Logger LOGGER = Log.getLogger(ControllerManager.class.getName());

    public ControllerManager() {
        storageControllers = new HashMap<>();
        urls = new HashSet<>();
    }

    public final boolean init(){
        Reflections reflections = new Reflections("org.sdargol.controllers");
        Set<Class<? extends IController>> controllerClasses = reflections.getSubTypesOf(IController.class);

        AtomicBoolean status = new AtomicBoolean(true);

        controllerClasses.forEach(clss -> {
            try {
                IController controller = clss.getConstructor().newInstance();
                String urlPath = controller.getClass()
                        .getAnnotation(RestController.class)
                        .url();
                storageControllers.put(urlPath, controller);
                urls.add(urlPath);
            } catch (InstantiationException |
                    IllegalAccessException |
                    InvocationTargetException |
                    NoSuchMethodException e) {
                LOGGER.log(Level.WARNING, "Error loading controllers", e);
                status.set(false);
            }
        });
        return status.get();
    }

    public final IController getController(final String requestUrl){
        String url = urls.stream()
                .filter(requestUrl::contains)
                .findFirst()
                .orElse(Props.getProperty("controller.default"));

        return storageControllers.get(url);
    }

    public final boolean contains(final String requestUrl){
        return urls.stream().anyMatch(requestUrl::contains);
    }
}
