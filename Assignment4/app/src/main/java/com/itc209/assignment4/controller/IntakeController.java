package com.itc209.assignment4.controller;

import android.content.Context;

import com.itc209.assignment4.database.IntakeManager;
import com.itc209.assignment4.model.Food;
import com.itc209.assignment4.model.Intake;

import java.util.Date;
import java.util.List;

public class IntakeController {

    private final Context context;
    private final IntakeManager intakeManager;
    FoodController foodController;

    public IntakeController(Context context, FoodController foodController) {
        super();
        this.context = context;
        this.foodController = foodController;
        this.intakeManager = new IntakeManager(this.context, foodController.getFoodDao());
    }

    public List<Intake> getIntakesByTime(Date start, Date end) {
        return intakeManager.findIntakesByTime(start, end);
    }

    public void deleteIntake(Date date, String foodName) {
        intakeManager.deleteIntake(date, foodName);
    }

    public void saveIntake(Intake intake) {
        intakeManager.saveIntake(intake);
    }

    public Food getTotalFood(Date start, Date end) {
        List<Intake> intakes = getIntakesByTime(start, end);
        Food total = new Food();
        total.setName("total");
        for (Intake intake : intakes) {
            total.setProtein(total.getProtein() + intake.getFood().getProtein());
            total.setCarbohydrates(total.getCarbohydrates() + intake.getFood().getCarbohydrates());
            total.setFat(total.getFat() + intake.getFood().getFat());
            total.setCalories(total.getCalories() + intake.getFood().getCalories());
        }
        return total;
    }

}
