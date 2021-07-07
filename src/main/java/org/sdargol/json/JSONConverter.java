package org.sdargol.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JSONConverter {
    private final static ObjectMapper mapper = new ObjectMapper();

    public static <T> String toJSON(T dto){
        String json= "{}";
        try {
            json = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static <T> T toJavaObject(InputStream is, Class<T> cls){
        try {
            return mapper.readValue(is, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T toJavaObject(String str, Class<T> cls){
        try {
            return mapper.readValue(str, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
