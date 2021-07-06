package org.sdargol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sdargol.dto.abstracts.ADTO;

public class DTOTransaction extends ADTO {
    @JsonProperty
    private Integer fromUserId;
    @JsonProperty
    private Integer toUserId;
    @JsonProperty
    private Integer count;
    @JsonProperty
    private boolean status;

    public DTOTransaction() {
        super();
    }

    public DTOTransaction(Integer id, Integer fromUserId, Integer toUserId, Integer count, boolean status) {
        super(id);
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.count = count;
        this.status = status;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DTOTransaction{" +
                "fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", count=" + count +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
