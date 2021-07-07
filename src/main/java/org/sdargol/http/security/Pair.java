package org.sdargol.http.security;

public class Pair <F, S>{
    private final F first;
    private final S second;

    private Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public static<F,S> Pair<F,S> create(F first,S second){
        return new Pair<>(first,second);
    }
}