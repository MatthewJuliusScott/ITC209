package com.itc209.assignment4.controller;

import android.content.Context;

import com.itc209.assignment4.dao.IntakeDao;
import com.itc209.assignment4.model.Food;
import com.itc209.assignment4.model.Intake;
import com.itc209.assignment4.model.Notification;

import java.sql.Time;

public class MainController {

    private Context context;

    private ConstraintController constraintController = new ConstraintController(context);
    private FoodController foodController = new FoodController(context);
    private IntakeController intakeController = new IntakeController(context, foodController);

    public void sendNotification(Notification notification) {
        // TODO implement
    }

    public void addToIntake(Food food, Time time) {
        // TODO implement
    }

    public void removeFromIntake(Food food, Time time) {
        // TODO implement
    }

    public Iterable<Intake> getIntakes(Time start, Time end) {
        // TODO implement
        return null;
    }
}
