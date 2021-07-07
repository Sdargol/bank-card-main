package org.sdargol.controllers.core.response;

public class ResponseEntity {
    private final int status;
    private final String json;

    public ResponseEntity(int status, String json) {
        this.status = status;
        this.json = json;
    }

    public int getStatus() {
        return status;
    }

    public String getJson() {
        return json;
    }
}
