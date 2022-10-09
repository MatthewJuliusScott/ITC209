package com.itc209.assignment4.controller;

import android.content.Context;

import com.itc209.assignment4.dao.IntakeDao;
import com.itc209.assignment4.model.Food;
import com.itc209.assignment4.model.Intake;

import java.util.Date;
import java.util.List;

public class IntakeController {

    private final Context context;
    private final IntakeDao intakeDao;
    FoodController foodController;

    public IntakeController(Context context, FoodController foodController) {
        super();
        this.context = context;
        this.foodController = foodController;
        this.intakeDao = new IntakeDao(this.context, foodController.getFoodDao());
    }

    public List<Intake> getIntakesByTime(Date start, Date end) throws Exception {
        return intakeDao.findIntakesByTime(start, end);
    }

    public void deleteIntake(Date date, String foodName) {
        intakeDao.deleteIntake(date, foodName);
    }

    public void saveIntake(Intake intake) {
        intakeDao.saveIntake(intake);
    }

    public Food getTotalFood(Date start, Date end) throws Exception {
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
