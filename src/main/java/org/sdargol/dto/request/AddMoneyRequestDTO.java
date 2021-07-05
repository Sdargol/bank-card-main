package org.sdargol.dto.request;

public class AddMoneyRequestDTO {
    private Integer money;
    private String number;

    public AddMoneyRequestDTO() {
    }

    public AddMoneyRequestDTO(Integer money, String number) {
        this.money = money;
        this.number = number;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
