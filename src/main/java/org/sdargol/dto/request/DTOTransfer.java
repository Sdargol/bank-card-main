package org.sdargol.dto.request;

public class DTOTransfer {
    private Integer from;
    private Integer to;
    private Integer count;

    public DTOTransfer() {
    }

    public DTOTransfer(Integer from, Integer to, Integer count) {
        this.from = from;
        this.to = to;
        this.count = count;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
