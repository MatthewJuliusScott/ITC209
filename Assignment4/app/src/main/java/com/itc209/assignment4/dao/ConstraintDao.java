package com.itc209.assignment4.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.itc209.assignment4.model.Constraint;

import java.util.ArrayList;
import java.util.List;

public class ConstraintDao {

    public static final String ID = "id";
    public static final String IS_GOAL = "isGoal";
    public static final String AMOUNT = "amount";
    public static final String TYPE = "type";
    public static final String TABLE_NAME = "food_constraint";

    private DatabaseHelper dbHelper;

    private final Context context;

    private SQLiteDatabase database;

    public ConstraintDao(Context context) {
        this.context = context;
    }

    public ConstraintDao open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Constraint findConstraintById(long id) throws Exception {
        try {
            open();
            String[] columns = new String[]{ID, IS_GOAL, AMOUNT, TYPE};
            try (Cursor cursor = database.query(TABLE_NAME, columns, ID + " = ?", new String[]{Long.toString(id)}, null, null, null)) {
                if (cursor != null) {
                    cursor.moveToFirst();
                    int idIndex = cursor.getColumnIndexOrThrow(ID);
                    int goalIndex = cursor.getColumnIndexOrThrow(IS_GOAL);
                    int amountIndex = cursor.getColumnIndexOrThrow(AMOUNT);
                    int typeIndex = cursor.getColumnIndexOrThrow(TYPE);
                    return new Constraint(cursor.getInt(idIndex), cursor.getInt(goalIndex) != 0, cursor.getFloat(amountIndex), Constraint.Type.values()[cursor.getInt(typeIndex)]);
                }
            }
            return null;
        } finally {
            close();
        }

    }

    public void deleteConstraint(long id) {
        try {
            open();
            database.delete(TABLE_NAME, ID + "= ?", new String[]{Long.toString(id)});
        } finally {
            close();
        }
    }

    public void saveConstraint(Constraint constraint) {
        try {
            open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(IS_GOAL, constraint.getId());
            contentValues.put(IS_GOAL, constraint.isGoal());
            contentValues.put(AMOUNT, constraint.getAmount());
            contentValues.put(TYPE, constraint.getType().ordinal());
            database.replace(TABLE_NAME, null, contentValues);
        } finally {
            close();
        }
    }

    public List<Constraint> getConstraints() {
        try {
            open();
            List<Constraint> constraints = new ArrayList<>();
            String[] columns = new String[]{ID, IS_GOAL, AMOUNT, TYPE};
            try (Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)) {
                if (cursor != null && cursor.getCount() >= 1) {
                    while (cursor.moveToNext()) {
                        int idIndex = cursor.getColumnIndexOrThrow(ID);
                        int goalIndex = cursor.getColumnIndexOrThrow(IS_GOAL);
                        int amountIndex = cursor.getColumnIndexOrThrow(AMOUNT);
                        int typeIndex = cursor.getColumnIndexOrThrow(TYPE);
                        constraints.add(new Constraint(cursor.getInt(idIndex), cursor.getInt(goalIndex) != 0, cursor.getFloat(amountIndex), Constraint.Type.values()[cursor.getInt(typeIndex)]));
                    }
                }
            }
            return constraints;
        } finally {
            close();
        }
    }
}
