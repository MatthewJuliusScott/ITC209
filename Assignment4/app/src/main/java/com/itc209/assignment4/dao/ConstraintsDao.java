package com.itc209.assignment4.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.itc209.assignment4.model.Constraints;

public class ConstraintsDao {

    public static final String CALORIES_GOAL = "caloriesGoal";
    public static final String FAT_GOAL = "fatGoal";
    public static final String PROTEIN_GOAL = "proteinGoal";
    public static final String CARBOHYDRATES_GOAL = "carbohydratesGoal";
    public static final String CALORIES_LIMIT = "caloriesLimit";
    public static final String FAT_LIMIT = "fatLimit";
    public static final String PROTEIN_LIMIT = "proteinLimit";
    public static final String CARBOHYDRATES_LIMIT = "carbohydratesLimit";

    public static final String TABLE_NAME = "constraints";

    private final Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public ConstraintsDao(Context context) {
        this.context = context;
    }

    public ConstraintsDao open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void saveConstraints(Constraints constraints) {
        try {
            open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CALORIES_GOAL, constraints.getCaloriesGoal());
            contentValues.put(FAT_GOAL, constraints.getFatGoal());
            contentValues.put(PROTEIN_GOAL, constraints.getProteinGoal());
            contentValues.put(CARBOHYDRATES_GOAL, constraints.getCarbohydratesGoal());
            contentValues.put(CALORIES_LIMIT, constraints.getCaloriesLimit());
            contentValues.put(FAT_LIMIT, constraints.getFatLimit());
            contentValues.put(PROTEIN_LIMIT, constraints.getProteinLimit());
            contentValues.put(CARBOHYDRATES_LIMIT, constraints.getCarbohydratesLimit());
            database.replace(TABLE_NAME, null, contentValues);
        } finally {
            close();
        }
    }

    public Constraints getConstraints() {
        try {
            open();
            Constraints constraints = new Constraints();
            String[] columns = new String[]{CALORIES_GOAL, FAT_GOAL, PROTEIN_GOAL, CARBOHYDRATES_GOAL, CALORIES_LIMIT, FAT_LIMIT, PROTEIN_LIMIT, CARBOHYDRATES_LIMIT};
            try (Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)) {
                if (cursor != null && cursor.getCount() >= 1) {
                    cursor.moveToFirst();
                    int caloriesGoalIndex = cursor.getColumnIndexOrThrow(CALORIES_GOAL);
                    int fatGoalIndex = cursor.getColumnIndexOrThrow(FAT_GOAL);
                    int proteinGoalIndex = cursor.getColumnIndexOrThrow(PROTEIN_GOAL);
                    int carbohydratesGoalIndex = cursor.getColumnIndexOrThrow(CARBOHYDRATES_GOAL);
                    int caloriesLimitIndex = cursor.getColumnIndexOrThrow(CALORIES_LIMIT);
                    int fatLimitIndex = cursor.getColumnIndexOrThrow(FAT_LIMIT);
                    int proteinLimitIndex = cursor.getColumnIndexOrThrow(PROTEIN_LIMIT);
                    int carbohydratesLimitIndex = cursor.getColumnIndexOrThrow(CARBOHYDRATES_LIMIT);
                    constraints = new Constraints(cursor.getInt(caloriesGoalIndex), cursor.getFloat(fatGoalIndex), cursor.getFloat(proteinGoalIndex), cursor.getFloat(carbohydratesGoalIndex), cursor.getInt(caloriesLimitIndex), cursor.getFloat(fatLimitIndex), cursor.getFloat(proteinLimitIndex), cursor.getFloat(carbohydratesLimitIndex));
                }
            }
            return constraints;
        } finally {
            close();
        }
    }
}
