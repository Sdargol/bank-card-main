package org.sdargol.controllers.core.response;

import org.sdargol.json.JSONConverter;

public final class ResponseEntity<T> {
    private final int status;
    private final T obj;

    public ResponseEntity(int status, T obj) {
        this.status = status;
        this.obj = obj;
    }

    public int getStatus() {
        return status;
    }

    public String getJson() {
        return JSONConverter.toJSON(obj);
    }
}
