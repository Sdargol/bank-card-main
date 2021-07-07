package org.sdargol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTORefill {
    @JsonProperty
    private Integer money;
    @JsonProperty
    private Long number;

    public DTORefill() {
    }

    public DTORefill(Integer money, Long number) {
        this.money = money;
        this.number = number;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }


    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "DTORefill{" +
                "money=" + money +
                ", number=" + number +
                '}';
    }
}
