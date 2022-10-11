package com.itc209.assignment4.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {


    private static final String DB_NAME = "nutrition_helper";
    private static final int DB_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_food_table = "CREATE TABLE " + FoodManager.TABLE_NAME + " (" + FoodManager.NAME + " TEXT PRIMARY KEY, " + FoodManager.CALORIES + " INTEGER, " + FoodManager.FAT + " REAL, " + FoodManager.PROTEIN + " REAL, " + FoodManager.CARBOHYDRATES + " REAL);";
        String create_intake_table = "CREATE TABLE " + IntakeManager.TABLE_NAME + " (" + IntakeManager.FOOD_NAME + " NAME, " + IntakeManager.TIME + " DATETIME, PRIMARY KEY (" + IntakeManager.FOOD_NAME + ", " + IntakeManager.TIME + "));";
        String create_constraint_table = "CREATE TABLE " + ConstraintsManager.TABLE_NAME + " (" + ConstraintsManager.CALORIES_GOAL + " INTEGER, " + ConstraintsManager.FAT_GOAL + " REAL, " + ConstraintsManager.PROTEIN_GOAL + " REAL, " + ConstraintsManager.CARBOHYDRATES_GOAL + " REAL, " + ConstraintsManager.CALORIES_LIMIT + " INTEGER, " + ConstraintsManager.FAT_LIMIT + " REAL, " + ConstraintsManager.PROTEIN_LIMIT + " REAL, " + ConstraintsManager.CARBOHYDRATES_LIMIT + " REAL);";

        db.execSQL(create_food_table);
        db.execSQL(create_intake_table);
        db.execSQL(create_constraint_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
