package org.sdargol.db.dao.core;

import org.sdargol.utils.Log;

import java.util.logging.Logger;

public class SManagerDAO {
    private static final Logger LOGGER = Log.getLogger(SManagerDAO.class.getName());
    private final static ManagerDAO MANAGER_DAO = new ManagerDAO();

    static {
        boolean bInit = MANAGER_DAO.init();

        if(bInit){
            LOGGER.info("SManagerDAO success load");
        }else{
            LOGGER.info("SManagerDAO error load");
        }
    }

    public static ManagerDAO getInstance(){
        return MANAGER_DAO;
    }
}
