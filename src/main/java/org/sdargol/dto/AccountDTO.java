package org.sdargol.dto;

import org.sdargol.dto.abstracts.ADTO;

public class AccountDTO extends ADTO {
    Long number;
    Integer money;
    Integer userId;

    public AccountDTO(){
        super();
    }

    public AccountDTO(Integer id, Long number, Integer money, Integer userId) {
        super(id);
        this.number = number;
        this.money = money;
        this.userId = userId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
