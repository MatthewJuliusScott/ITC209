package com.itc209.assignment4.model;

import java.util.Date;

public class Intake {

    public Intake() {
        super();
    }

    public Intake(Date date, Food food, int grams) {
        super();
        this.date = date;
        this.food = food;
        this.grams = grams;
    }

    private Date date;
    private Food food;
    private int grams;

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

}
