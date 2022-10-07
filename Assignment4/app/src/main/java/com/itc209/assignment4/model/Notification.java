package com.itc209.assignment4.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Notification {

    private final static AtomicInteger c = new AtomicInteger(0);
    private String title;
    private String message;
    private Type type;

    public Notification(String title, String message, Type type) {
        super();
        this.title = title;
        this.message = message;
        this.type = type;
    }
    public Notification() {
        super();
    }

    public static int getID() {
        return c.incrementAndGet();
    }

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
