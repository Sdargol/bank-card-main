package org.sdargol.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DTOMessage {
    @JsonProperty
    private String msg;

    public DTOMessage() {
    }

    public DTOMessage(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "DTOMessage{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
