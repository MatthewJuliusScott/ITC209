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

        String create_food_table = "CREATE TABLE " + FoodDao.TABLE_NAME + " (" + FoodDao.NAME + " TEXT PRIMARY KEY, " + FoodDao.CALORIES + " INTEGER, " + FoodDao.FAT + " REAL, " + FoodDao.PROTEIN + " REAL, " + FoodDao.CARBOHYDRATES + " REAL);";
        String create_intake_table = "CREATE TABLE " + IntakeDao.TABLE_NAME + " (" + IntakeDao.FOOD_NAME + " NAME, " + IntakeDao.TIME + " DATETIME, PRIMARY KEY (" + IntakeDao.FOOD_NAME + ", " + IntakeDao.TIME + "));";
        String create_constraint_table = "CREATE TABLE " + ConstraintsDao.TABLE_NAME + " (" + ConstraintsDao.CALORIES_GOAL + " INTEGER, " + ConstraintsDao.FAT_GOAL + " REAL, " + ConstraintsDao.PROTEIN_GOAL + " REAL, " + ConstraintsDao.CARBOHYDRATES_GOAL + " REAL, " + ConstraintsDao.CALORIES_LIMIT + " INTEGER, " + ConstraintsDao.FAT_LIMIT + " REAL, " + ConstraintsDao.PROTEIN_LIMIT + " REAL, " + ConstraintsDao.CARBOHYDRATES_LIMIT + " REAL , PRIMARY KEY (" + ConstraintsDao.CALORIES_GOAL + ", " + ConstraintsDao.FAT_GOAL + ", " +  ConstraintsDao.PROTEIN_GOAL + ", " +  ConstraintsDao.CARBOHYDRATES_GOAL + ", " +  ConstraintsDao.CALORIES_LIMIT + ", " +  ConstraintsDao.FAT_LIMIT + ", " +  ConstraintsDao.PROTEIN_LIMIT + ", " +  ConstraintsDao.CARBOHYDRATES_LIMIT + "));";

        db.execSQL(create_food_table);
        db.execSQL(create_intake_table);
        db.execSQL(create_constraint_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
