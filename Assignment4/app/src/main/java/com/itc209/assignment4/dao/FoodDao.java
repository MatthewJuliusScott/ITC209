package com.itc209.assignment4.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.itc209.assignment4.model.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FoodDao {

    public static final String NAME = "name";
    public static final String CALORIES = "calories";
    public static final String FAT = "fat";
    public static final String PROTEIN = "protein";
    public static final String CARBOHYDRATES = "carbohydrates";
    public static final String TABLE_NAME = "food";

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public FoodDao(Context context) {
        context = this.context;
    }

    public FoodDao open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Food findFoodByName(String name) {
        String[] columns = new String[]{NAME, CALORIES, FAT, PROTEIN, CARBOHYDRATES};
        try (Cursor cursor = database.query(TABLE_NAME, columns, NAME + " = ?", new String[]{name}, null, null, null)) {
            if (cursor != null) {
                cursor.moveToFirst();
                int nameIndex = cursor.getColumnIndexOrThrow(NAME);
                int caloriesIndex = cursor.getColumnIndexOrThrow(CALORIES);
                int fatIndex = cursor.getColumnIndexOrThrow(FAT);
                int proteinIndex = cursor.getColumnIndexOrThrow(PROTEIN);
                int carbohydratesIndex = cursor.getColumnIndexOrThrow(CARBOHYDRATES);
                return new Food(cursor.getString(nameIndex), cursor.getInt(caloriesIndex), cursor.getFloat(fatIndex), cursor.getFloat(proteinIndex), cursor.getFloat(carbohydratesIndex));
            }
        }
        return null;
    }

    public void deleteFoodByName(String name) {
        database.delete(TABLE_NAME, NAME + "= ?", new String[]{name});
    }

    public void saveFood(Food food) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, food.getName());
        contentValues.put(CALORIES, food.getCalories());
        contentValues.put(FAT, food.getFat());
        contentValues.put(PROTEIN, food.getProtein());
        contentValues.put(CARBOHYDRATES, food.getCarbohydrates());
        database.replace(TABLE_NAME, null, contentValues);
    }

    public List<Food> findFoodsByKeyword(String keyword) {
        List<Food> foods = new ArrayList<>();
        String[] columns = new String[]{NAME, CALORIES, FAT, PROTEIN, CARBOHYDRATES};
        try (Cursor cursor = database.query(TABLE_NAME, columns, NAME + " LIKE ?", new String[]{"%" + keyword + "%"}, null, null, null)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int nameIndex = cursor.getColumnIndexOrThrow(NAME);
                    int caloriesIndex = cursor.getColumnIndexOrThrow(CALORIES);
                    int fatIndex = cursor.getColumnIndexOrThrow(FAT);
                    int proteinIndex = cursor.getColumnIndexOrThrow(PROTEIN);
                    int carbohydratesIndex = cursor.getColumnIndexOrThrow(CARBOHYDRATES);
                    foods.add(new Food(cursor.getString(nameIndex), cursor.getInt(caloriesIndex), cursor.getFloat(fatIndex), cursor.getFloat(proteinIndex), cursor.getFloat(carbohydratesIndex)));
                }
            }
        }
        return foods;
    }
}
