package com.itc209.assignment4.model;

import java.util.Date;

public class Intake {

    private Date date;
    private Food food;

    public Intake() {
        super();
    }

    public Intake(Date date, Food food) {
        super();
        this.date = date;
        this.food = food;
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
