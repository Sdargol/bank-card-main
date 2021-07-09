package org.sdargol.db.dao.core;

import org.reflections.Reflections;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

public class ManagerDAO {
    private final HashMap<Class<?>, IDAO> storage = new HashMap<>();

    public boolean init(){
        Reflections r = new Reflections("org.sdargol.db.dao");
        Set<Class<? extends IDAO>> cDAO = r.getSubTypesOf(IDAO.class);
        AtomicBoolean status = new AtomicBoolean(true);

        cDAO.forEach(clss -> {
            try {
                IDAO dao = clss.getConstructor().newInstance();
                storage.put(clss, dao);
            } catch (InstantiationException |
                    IllegalAccessException |
                    InvocationTargetException |
                    NoSuchMethodException e) {
                //LOGGER.log(Level.WARNING, "Error loading controllers", e);
                status.set(false);
            }
        });
        return status.get();
    }

    public <T extends IDAO> T get(Class<T> clss){
        return clss.cast(storage.get(clss));
    }
}
