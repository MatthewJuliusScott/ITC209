package com.itc209.assignment4.controller;

import android.content.Context;

import com.itc209.assignment4.Utils;
import com.itc209.assignment4.dao.ConstraintDao;
import com.itc209.assignment4.model.Constraint;
import com.itc209.assignment4.model.Food;

import java.util.ArrayList;
import java.util.Date;
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
        Food totalFood = intakeController.getTotalFood(Utils.dayStart(new Date()), Utils.dayEnd(new Date()));
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
