package org.sdargol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTOUserTransfer {
    @JsonProperty
    private String loginTo;
    @JsonProperty
    private String loginFrom;
    @JsonProperty
    private Integer count;

    public DTOUserTransfer() {
    }

    public DTOUserTransfer(String loginTo, String loginFrom, Integer count) {
        this.loginTo = loginTo;
        this.loginFrom = loginFrom;
        this.count = count;
    }

    public String getLoginTo() {
        return loginTo;
    }

    public void setLoginTo(String loginTo) {
        this.loginTo = loginTo;
    }

    public String getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(String loginFrom) {
        this.loginFrom = loginFrom;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
