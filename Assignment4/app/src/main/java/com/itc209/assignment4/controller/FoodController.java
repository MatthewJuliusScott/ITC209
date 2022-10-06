package com.itc209.assignment4.controller;

import android.content.Context;

import com.itc209.assignment4.dao.FoodDao;
import com.itc209.assignment4.model.Food;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class FoodController {

    private final Context context;

    public FoodController(Context context) {
        this.context = context;
        setFoodDao(new FoodDao(context));
    }

    public FoodDao getFoodDao() {
        return foodDao;
    }

    public void setFoodDao(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    private FoodDao foodDao;

    public List<Food> findFoodsByKeyword(String keyword) {

        List<Food> foods = new ArrayList<Food>();

        // fetch any matching foods from the API
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://chomp-food-nutrition-database-v2.p.rapidapi.com/food/branded/search.php?page=1&country=Australia&keyword=" + keyword)
                    .get()
                    .addHeader("X-RapidAPI-Key", "9f55517a4fmshac05f95235696eep1e1860jsn4d810b98d8b6")
                    .addHeader("X-RapidAPI-Host", "chomp-food-nutrition-database-v2.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();

            JSONObject responseJson = new JSONObject(response.body().string());
            JSONArray jsonArray = responseJson.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Food food = new Food();
                food.setName(jsonObject.getString("name"));
                // get nutrients
                JSONArray nutrients = jsonObject.getJSONArray("nutrients");
                for (int j = 0; j < nutrients.length(); j++) {
                    JSONObject nutrient = nutrients.getJSONObject(j);
                    if (nutrient.getString("name").equals("Energy")) {
                        food.setCalories(jsonObject.getInt("Energy"));
                    }
                    if (nutrient.getString("name").equals("Fat")) {
                        food.setFat(jsonObject.getInt("Fat"));
                    }
                    if (nutrient.getString("name").equals("Carbohydrates")) {
                        food.setCarbohydrates(jsonObject.getInt("Carbohydrates"));
                    }
                    if (nutrient.getString("name").equals("Proteins")) {
                        food.setProtein(jsonObject.getInt("Proteins"));
                    }
                }
                foods.add(food);
            }
        } catch (IOException ioe) {
            // API unavailable
        } catch (JSONException jsone) {
            // response did not contain the food or food entry was incomplete
        }

        // add any matching foods from internal database
        foods.addAll(foodDao.findFoodsByKeyword(keyword));

        // return combined list of foods
        return foods;

    }

    public void deleteFood(Food food) {
        foodDao.deleteFoodByName(food.getName());
    }

    public void saveFood(Food food) {
        foodDao.saveFood(food);
    }
}
