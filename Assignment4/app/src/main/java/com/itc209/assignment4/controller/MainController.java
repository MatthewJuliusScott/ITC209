package com.itc209.assignment4.controller;

import android.content.Context;

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
