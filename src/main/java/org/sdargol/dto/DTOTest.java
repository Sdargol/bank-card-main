package org.sdargol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTOTest extends ADTOBase {
    @JsonProperty("info")
    private String info;

    public DTOTest() {
        super();
    }

    public DTOTest(Integer id, String info){
        super(id);
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
