package com.itc209.assignment4.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "nutrition_helper";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_food_table = "CREATE TABLE " + FoodDao.TABLE_NAME + " (" + FoodDao.NAME + " TEXT PRIMARY KEY, " + FoodDao.CALORIES + " INT, " + FoodDao.FAT + " INT, " + FoodDao.PROTEIN + " FLOAT, " + FoodDao.CARBOHYDRATES + " FLOAT);";
        String create_intake_table = "CREATE TABLE " + IntakeDao.TABLE_NAME + " (" + IntakeDao.FOOD_NAME + " NAME, " + IntakeDao.TIME + " DATETIME, PRIMARY KEY (" + IntakeDao.FOOD_NAME + ", " + IntakeDao.TIME + "));";
        String create_constraint_table = "CREATE TABLE " + ConstraintDao.TABLE_NAME + " (id LONG PRIMARY KEY, " + ConstraintDao.IS_GOAL + " BOOLEAN, " + ConstraintDao.AMOUNT + " FLOAT, " + ConstraintDao.TYPE + " INT);";

        db.execSQL(create_food_table);
        db.execSQL(create_intake_table);
        db.execSQL(create_constraint_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
