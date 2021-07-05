package org.sdargol.dto.abstracts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public abstract class ADTO implements Serializable {
    @JsonProperty("id")
    protected Integer id;

    public ADTO(Integer id) {
        this.id = id;
    }

    public ADTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
