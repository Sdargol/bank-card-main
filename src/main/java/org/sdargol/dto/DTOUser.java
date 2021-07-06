package org.sdargol.dto;

import org.sdargol.dto.abstracts.ADTO;

public class DTOUser extends ADTO {
    private String name;
    private String password;

    public DTOUser() {
        super();
    }

    public DTOUser(Integer id, String name, String password) {
        super(id);
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DTOUser{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
