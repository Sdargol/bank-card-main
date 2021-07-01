package org.sdargol.json;

import org.sdargol.dto.ADTOBase;

public interface IConverter <T extends ADTOBase>{
    String toJSON(T dto);
    T toJavaObject(String json, Class<T> cls);
}
