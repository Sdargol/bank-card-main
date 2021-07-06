package org.sdargol.db.dao.api;

import org.sdargol.dto.DTORoles;
import org.sdargol.dto.DTOUser;
import org.sdargol.dto.response.DTOMessage;

public interface IDAORoles {
    DTORoles getRolesByUserId(int id);
    DTOMessage addRolesForUser(DTORoles roles);
}
