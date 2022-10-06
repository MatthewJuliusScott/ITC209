package com.itc209.assignment4.controller;

import android.content.Context;

import com.itc209.assignment4.dao.FoodDao;
import com.itc209.assignment4.dao.IntakeDao;

public class IntakeController {

    private Context context;

    FoodController foodController;

    private IntakeDao intakeDao = new IntakeDao(context, foodController.getFoodDao());

    public IntakeController(Context context, FoodController foodController) {
        super();
        this.context = context;
        this.foodController = foodController;
    }
}
