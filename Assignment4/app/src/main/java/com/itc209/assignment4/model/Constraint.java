package com.itc209.assignment4.model;

public class Constraint {

    public Constraint() {
        super();
    }
    public Constraint(long id, boolean isGoal, float amount, Type type) {
        super();
        this.id = id;
        this.isGoal = isGoal;
        this.amount = amount;
        this.type = type;
    }

    private long id;
    private boolean isGoal;
    private float amount;
    private Type type;

    public enum Type {
        PROTEIN,
        FAT,
        CARBOHYDRATE,
        CALORIE
    }

    public boolean isGoal() {
        return isGoal;
    }

    public void setIsGoal(boolean isGoal) {
        this.isGoal = isGoal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
