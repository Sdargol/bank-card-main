package org.sdargol.dto;

import org.sdargol.dto.abstracts.ADTO;

public class RolesDTO extends ADTO {
    private Integer userId;

    public RolesDTO() {
        super();
    }

    public RolesDTO(Integer id, Integer userId) {
        super(id);
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
