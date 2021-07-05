package org.sdargol.dto;

import org.sdargol.dto.abstracts.ADTO;

public class UserDTO extends ADTO {
    private String name;
    private String password;

    public UserDTO() {
        super();
    }

    public UserDTO(Integer id, String name, String password) {
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
}
