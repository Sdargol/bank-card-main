package org.sdargol.dto;

import org.sdargol.dto.abstracts.ADTO;

public class DTOAccount extends ADTO {
    Long number;
    Integer money;

    public DTOAccount(){
        super();
    }

    public DTOAccount(Integer id, Long number, Integer money) {
        super(id);
        this.number = number;
        this.money = money;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "DTOAccount{" +
                "number=" + number +
                ", money=" + money +
                ", id=" + id +
                '}';
    }
}
