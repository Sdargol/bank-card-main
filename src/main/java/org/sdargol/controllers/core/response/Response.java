package org.sdargol.controllers.core.response;

public class Response {
    public static ResponseEntity ok(String json){
        return new ResponseEntity(200,json);
    }
}
