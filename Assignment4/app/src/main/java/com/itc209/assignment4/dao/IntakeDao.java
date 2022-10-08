package com.itc209.assignment4.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.itc209.assignment4.model.Intake;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IntakeDao {

    FoodDao foodDao;

    public static final String TIME = "time";
    public static final String FOOD_NAME = "foodName";
    public static final String TABLE_NAME = "intake";

    private DatabaseHelper dbHelper;

    private final Context context;

    private SQLiteDatabase database;

    public IntakeDao(Context context, FoodDao foodDao) {
        this.context = context;
        this.foodDao = foodDao;
    }

    public IntakeDao open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public List<Intake> findIntakesByTime(Date start, Date end) throws Exception {
        try {
            open();
            List<Intake> intakes = new ArrayList<>();
            String[] columns = new String[]{TIME, FOOD_NAME};
            Cursor cursor = database.query(TABLE_NAME, columns, TIME + " > ? AND " + TIME + " < ?", new String[]{Long.toString(start.getTime()), Long.toString(end.getTime())}, null, null, null);
            if (cursor != null && cursor.getCount() >= 1) {
                while (cursor.moveToNext()) {
                    int timeIndex = cursor.getColumnIndexOrThrow(TIME);
                    int foodNameIndex = cursor.getColumnIndexOrThrow(FOOD_NAME);
                    intakes.add(new Intake(new Date(cursor.getLong(timeIndex)), foodDao.findFoodByName(cursor.getString(foodNameIndex))));
                }
            }
            return intakes;
        } finally {
            close();
        }
    }

    public void deleteIntake(Date date, String foodName) {
        try {
            open();
            database.delete(TABLE_NAME, TIME + "= ? AND " + FOOD_NAME + " = ?", new String[]{Long.toString(date.getTime()), foodName});
        } finally {
            close();
        }

    }

    public void saveIntake(Intake intake) {
        try {
            open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TIME, intake.getDate().getTime());
            contentValues.put(FOOD_NAME, intake.getFood().getName());
            database.replace(TABLE_NAME, null, contentValues);
        } finally {
            close();
        }
    }
}
