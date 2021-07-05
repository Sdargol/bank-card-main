package org.sdargol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sdargol.dto.abstracts.ADTO;

public class CardDTO extends ADTO {
    @JsonProperty("number")
    private Long number;
    private boolean status;

    public CardDTO() {
        super();
    }

    public CardDTO(Integer id, Long number, boolean status){
        super(id);
        this.number = number;
        this.status = status;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CardDTO{" +
                "number=" + number +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
