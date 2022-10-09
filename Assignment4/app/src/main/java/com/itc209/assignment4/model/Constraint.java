package com.itc209.assignment4.model;

public class Constraint {

    private int id;
    private boolean isGoal;
    private float amount;
    private Type type;
    public Constraint() {
        super();
    }
    public Constraint(int id, boolean isGoal, float amount, Type type) {
        super();
        this.id = id;
        this.isGoal = isGoal;
        this.amount = amount;
        this.type = type;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public void setIsGoal(boolean isGoal) {
        this.isGoal = isGoal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public enum Type {
        PROTEIN,
        FAT,
        CARBOHYDRATE,
        CALORIE
    }
}
