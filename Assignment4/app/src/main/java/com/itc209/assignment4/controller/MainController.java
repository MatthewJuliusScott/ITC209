package com.itc209.assignment4.controller;

import android.content.Context;

import com.itc209.assignment4.dao.IntakeDao;
import com.itc209.assignment4.model.Food;
import com.itc209.assignment4.model.Intake;
import com.itc209.assignment4.model.Notification;

import java.sql.Time;

public class MainController {

    private Context context;
    private final FoodController foodController = new FoodController(context);
    private final IntakeController intakeController = new IntakeController(context, foodController);
    private final ConstraintController constraintController = new ConstraintController(context, intakeController);

    public void sendNotification(Notification notification) {
        // TODO implement
    }
}
