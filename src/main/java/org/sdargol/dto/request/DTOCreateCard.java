package org.sdargol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTOCreateCard {
    @JsonProperty
    private Long number;

    public DTOCreateCard() {
    }

    public DTOCreateCard(Long number) {
        this.number = number;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "DTOCreateCard{" +
                "number=" + number +
                '}';
    }
}
