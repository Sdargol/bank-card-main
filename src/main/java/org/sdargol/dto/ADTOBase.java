package org.sdargol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ADTOBase {
    @JsonProperty
    protected Integer id;

    public ADTOBase(Integer id) {
        this.id = id;
    }

    public ADTOBase() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
