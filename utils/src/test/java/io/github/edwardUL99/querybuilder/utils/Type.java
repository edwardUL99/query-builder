package io.github.edwardUL99.querybuilder.utils;

public class Type {
    public final String s;

    public Type(String s) {
        this.s = s;
    }

    public static Type instantiate(String s) {
        return new Type(s);
    }

    public static String invalid(String s) {
        return s;
    }

    public Type nonStatic(String s) {
        return new Type(s);
    }
}