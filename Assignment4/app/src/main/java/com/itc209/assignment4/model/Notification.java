package com.itc209.assignment4.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Notification {

    private final static AtomicInteger c = new AtomicInteger(0);

    public static int getID() {
        return c.incrementAndGet();
    }

    private String title;
    private String message;
    private Type type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        POSITIVE,
        NEGATIVE,
        NEUTRAL
    }
}
