package com.itc209.assignment4.controller;

import android.content.Context;

import com.itc209.assignment4.Utils;
import com.itc209.assignment4.dao.ConstraintsDao;
import com.itc209.assignment4.model.Constraints;
import com.itc209.assignment4.model.Food;
import com.itc209.assignment4.model.Notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConstraintsController {

    private final ConstraintsDao constraintDao;

    private final IntakeController intakeController;

    public ConstraintsController(Context context, IntakeController intakeController) {
        this.constraintDao = new ConstraintsDao(context);
        this.intakeController = intakeController;
    }

    public Constraints getConstraints() {
        return constraintDao.getConstraints();
    }

    public void saveConstraints(Constraints constraints) {
        constraintDao.saveConstraints(constraints);
    }

    public List<Notification> checkTriggers() throws Exception {
        List<Notification> notifications = new ArrayList<>();
        Constraints constraints = getConstraints();
        Food totalFood = intakeController.getTotalFood(Utils.dayStart(new Date()), Utils.dayEnd(new Date()));
        // check constraints
        if (totalFood.getCalories() > constraints.getCaloriesLimit() && constraints.getCaloriesLimit() > 0) {
            notifications.add(Notification.EXCEEDED_CALORIE_LIMIT);
        }
        if (totalFood.getFat() > constraints.getFatLimit() && constraints.getFatLimit() > 0) {
            notifications.add(Notification.EXCEEDED_FAT_LIMIT);
        }
        if (totalFood.getProtein() > constraints.getProteinLimit() && constraints.getProteinLimit() > 0) {
            notifications.add(Notification.EXCEEDED_PROTEIN_LIMIT);
        }
        if (totalFood.getCarbohydrates() > constraints.getCarbohydratesLimit() && constraints.getCarbohydratesLimit() > 0) {
            notifications.add(Notification.EXCEEDED_CARBOHYDRATES_LIMIT);
        }
        if (totalFood.getCalories() > constraints.getCaloriesGoal() && constraints.getCaloriesGoal() > 0) {
            notifications.add(Notification.REACHED_CALORIE_GOAL);
        }
        if (totalFood.getFat() > constraints.getFatGoal() && constraints.getFatGoal() > 0) {
            notifications.add(Notification.REACHED_FAT_GOAL);
        }
        if (totalFood.getProtein() > constraints.getProteinGoal() && constraints.getProteinGoal() > 0) {
            notifications.add(Notification.REACHED_PROTEIN_GOAL);
        }
        if (totalFood.getCarbohydrates() > constraints.getCarbohydratesGoal() && constraints.getCarbohydratesGoal() > 0) {
            notifications.add(Notification.REACHED_CARBOHYDRATES_GOAL);
        }
        return notifications;
    }
}
