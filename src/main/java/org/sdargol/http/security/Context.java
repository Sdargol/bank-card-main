package org.sdargol.http.security;

import org.sdargol.dto.DTORoles;

public class Context {
    private final static ThreadLocal<Pair<String, DTORoles>> context = new ThreadLocal<>();

    public static ThreadLocal<Pair<String, DTORoles>> getContext(){
        return context;
    }
}
