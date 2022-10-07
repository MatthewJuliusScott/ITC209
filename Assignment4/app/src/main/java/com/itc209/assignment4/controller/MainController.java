package com.itc209.assignment4.controller;

import static android.provider.Settings.System.getString;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.itc209.assignment4.dao.IntakeDao;
import com.itc209.assignment4.model.Food;
import com.itc209.assignment4.model.Intake;
import com.itc209.assignment4.R;
import com.itc209.assignment4.model.Notification;

import java.sql.Time;

public class MainController {

    private final Context context;
    private final FoodController foodController;
    private final IntakeController intakeController;
    private final ConstraintController constraintController;

    public MainController(Context context) {
        this.context = context;
        foodController = new FoodController(context);
        intakeController = new IntakeController(context, foodController);
        constraintController = new ConstraintController(context, intakeController);
    }

    public FoodController getFoodController() {
        return foodController;
    }

    public IntakeController getIntakeController() {
        return intakeController;
    }

    public ConstraintController getConstraintController() {
        return constraintController;
    }
}
