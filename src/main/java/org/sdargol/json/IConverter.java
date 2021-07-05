package org.sdargol.json;

import java.io.Serializable;

public interface IConverter <T extends Serializable>{
    String toJSON(T dto);
    T toJavaObject(String json, Class<T> cls);
}
