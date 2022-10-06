package com.itc209.assignment4.controller;

import android.content.Context;

import com.itc209.assignment4.dao.ConstraintDao;
import com.itc209.assignment4.model.Constraint;

public class ConstraintController {

    private Context context;

    public ConstraintController(Context context) {
        this.context = context;
        constraintDao = new ConstraintDao(context);
    }

    private ConstraintDao constraintDao;

    public Constraint findConstraintById(long id) throws Exception {
        return constraintDao.findConstraintById(id);
    }

    public void deleteConstraint(Constraint constraint) {
        if (constraint.getId() > 0) {
            constraintDao.deleteConstraint(constraint.getId());
        }
    }

    public void saveConstraint(Constraint constraint) {
        constraintDao.saveConstraint(constraint);
    }

    public Iterable<Constraint> checkTriggers() {
        // TODO implement
        return null;
    }
}
