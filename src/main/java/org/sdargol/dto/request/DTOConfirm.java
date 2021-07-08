package org.sdargol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTOConfirm {
    @JsonProperty
    private Integer number;

    public DTOConfirm() {
    }

    public DTOConfirm(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
