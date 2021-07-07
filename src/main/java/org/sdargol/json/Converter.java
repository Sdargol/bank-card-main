package org.sdargol.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

public class Converter <T extends Serializable> implements IConverter<T> {

    @Deprecated
    public String toJSON(T dto){
        ObjectMapper mapper = new ObjectMapper();
        String json= "{}";

        try {
            json = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    @Deprecated
    public T toJavaObject(String json, Class<T> cls){
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
