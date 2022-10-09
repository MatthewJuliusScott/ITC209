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

    public final static Notification EXCEEDED_CALORIE_LIMIT = new Notification("Warning!", "You exceeded your calorie limit for today. Try and eat less calorie dense food or exercise more and increase your limit.", Type.NEGATIVE);
    public final static Notification EXCEEDED_FAT_LIMIT = new Notification("Warning!", "You exceeded your fat limit for today. Try and eat less fatty foods.", Type.NEGATIVE);
    public final static Notification EXCEEDED_PROTEIN_LIMIT = new Notification("Warning!", "You exceeded your protein limit for today. Try and eat less protein rich foods.", Type.NEGATIVE);
    public final static Notification EXCEEDED_CARBOHYDRATES_LIMIT = new Notification("Warning!", "You exceeded your carbohydrate limit for today. Try and eat less carbohydrate dense foods.", Type.NEGATIVE);

    public final static Notification REACHED_CALORIE_GOAL = new Notification("Congratulations!", "You reached your calorie goal for today! Keep it up!", Type.POSITIVE);
    public final static Notification REACHED_FAT_GOAL = new Notification("Congratulations!", "You reached your fat goal for today! Keep it up!", Type.POSITIVE);
    public final static Notification REACHED_PROTEIN_GOAL = new Notification("Congratulations!", "You reached your protein goal for today! Keep it up!", Type.POSITIVE);
    public final static Notification REACHED_CARBOHYDRATES_GOAL = new Notification("Congratulations!", "You reached your carbohydrates goal for today! Keep it up!", Type.POSITIVE);
}

