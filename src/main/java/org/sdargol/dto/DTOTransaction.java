package org.sdargol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sdargol.dto.abstracts.ADTO;

public class DTOTransaction extends ADTO {
    @JsonProperty
    private Integer fromAccountId;
    @JsonProperty
    private Integer toAccountId;
    @JsonProperty
    private Integer count;
    @JsonProperty
    private boolean status;

    public DTOTransaction() {
        super();
    }

    public DTOTransaction(Integer id, Integer fromUserId, Integer toUserId, Integer count, boolean status) {
        super(id);
        this.fromAccountId = fromUserId;
        this.toAccountId = toUserId;
        this.count = count;
        this.status = status;
    }

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Integer fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Integer getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Integer toAccountId) {
        this.toAccountId = toAccountId;
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
                "fromAccountId=" + fromAccountId +
                ", toAccountId=" + toAccountId +
                ", count=" + count +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
