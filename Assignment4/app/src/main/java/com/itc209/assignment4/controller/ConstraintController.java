package com.itc209.assignment4.controller;

import android.content.Context;

import com.itc209.assignment4.dao.ConstraintDao;
import com.itc209.assignment4.model.Constraint;
import com.itc209.assignment4.model.Food;
import com.itc209.assignment4.model.Intake;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ConstraintController {

    private final Context context;

    private final ConstraintDao constraintDao;

    private final IntakeController intakeController;

    public ConstraintController(Context context, IntakeController intakeController) {
        this.context = context;
        this.constraintDao = new ConstraintDao(this.context);
        this.intakeController = intakeController;
    }

    public Constraint findConstraintById(long id) throws Exception {
        return constraintDao.findConstraintById(id);
    }

    public List<Constraint> getConstraints() {
        return constraintDao.getConstraints();
    }

    public void deleteConstraint(Constraint constraint) {
        if (constraint.getId() > 0) {
            constraintDao.deleteConstraint(constraint.getId());
        }
    }

    public void saveConstraint(Constraint constraint) {
        constraintDao.saveConstraint(constraint);
    }

    public List<Constraint> checkTriggers() throws Exception {
        List<Constraint> triggeredConstraints = new ArrayList<>();
        List<Constraint> constraints = getConstraints();
        // today
        Calendar today = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        // next day
        Calendar tomorrow = (GregorianCalendar) today.clone();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        Food totalFood = intakeController.getTotalFood(today.getTime(), tomorrow.getTime());
        for (Constraint constraint : constraints) {
            if (constraint.getType().ordinal() == Constraint.Type.CARBOHYDRATE.ordinal()) {
                if (totalFood.getCarbohydrates() > constraint.getAmount()) {
                    triggeredConstraints.add(constraint);
                }
            } else if (constraint.getType().ordinal() == Constraint.Type.FAT.ordinal()) {
                if (totalFood.getFat() > constraint.getAmount()) {
                    triggeredConstraints.add(constraint);
                }
            } else if (constraint.getType().ordinal() == Constraint.Type.CALORIE.ordinal()) {
                if (totalFood.getCalories() > constraint.getAmount()) {
                    triggeredConstraints.add(constraint);
                }
            } else if (constraint.getType().ordinal() == Constraint.Type.PROTEIN.ordinal()) {
                if (totalFood.getProtein() > constraint.getAmount()) {
                    triggeredConstraints.add(constraint);
                }
            }
        }
        return triggeredConstraints;
    }
}
