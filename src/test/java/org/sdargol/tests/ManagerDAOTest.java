package org.sdargol.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sdargol.db.dao.DAOCard;
import org.sdargol.db.dao.core.ManagerDAO;

public class ManagerDAOTest {
    @Test
    void initTest(){
        ManagerDAO m = new ManagerDAO();

        Assertions.assertTrue(m.init());
        Assertions.assertNotNull(m.get(DAOCard.class));
    }
}
