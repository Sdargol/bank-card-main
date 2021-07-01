package org.sdargol.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sdargol.dto.ADTOBase;

import java.io.IOException;

public class Converter <T extends ADTOBase> implements IConverter<T> {

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
