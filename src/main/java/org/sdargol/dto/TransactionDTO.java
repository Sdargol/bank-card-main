package org.sdargol.dto;

import org.sdargol.dto.abstracts.ADTO;

public class TransactionDTO extends ADTO {
    private Integer fromUserId;
    private Integer toUserId;
    private Integer count;

    public TransactionDTO() {
        super();
    }

    public TransactionDTO(Integer id, Integer fromUserId, Integer toUserId, Integer count) {
        super(id);
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.count = count;
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
}
