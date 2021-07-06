package org.sdargol.dto;

import org.sdargol.dto.abstracts.ADTO;
import org.sdargol.http.security.Roles;

import java.util.Set;

public class DTORoles extends ADTO {
    private Set<Roles> roles;
    private Integer userId;

    public DTORoles() {
        super();
    }

    public DTORoles(Integer id, Integer userId, Set<Roles> roles) {
        super(id);
        this.userId = userId;
        this.roles = roles;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "DTORoles{" +
                "roles=" + roles +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }
}
