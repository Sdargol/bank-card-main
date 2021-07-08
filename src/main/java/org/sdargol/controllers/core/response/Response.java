package org.sdargol.controllers.core.response;

import org.sdargol.dto.response.DTOMessage;

public class Response {
    public static<T> ResponseEntity<T> ok(T obj){
        return new ResponseEntity<>(200,obj);
    }

    public static ResponseEntity<DTOMessage> unauthorized(){
        return new ResponseEntity<>(401,
                new DTOMessage("Unauthorized")
        );
    }

    public static ResponseEntity<DTOMessage> forbidden(){
        return new ResponseEntity<>(403,
                new DTOMessage("No access. No role or expired token")
        );
    }
}
