package org.sdargol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTORefill {
    @JsonProperty
    private Integer money;
    @JsonProperty
    private Integer id;

    public DTORefill() {
    }

    public DTORefill(Integer money, Integer id) {
        this.money = money;
        this.id = id;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
