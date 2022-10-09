package com.itc209.assignment4.controller;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.itc209.assignment4.DisplaySearchResultsCallBack;
import com.itc209.assignment4.dao.FoodDao;
import com.itc209.assignment4.model.Food;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FoodController {

    private final Context context;
    private FoodDao foodDao;
    private DisplaySearchResultsCallBack mainClass;

    public FoodController(Context context, DisplaySearchResultsCallBack mainClass) {
        this.context = context;
        this.mainClass = mainClass;
        setFoodDao(new FoodDao(this.context));
    }

    public FoodDao getFoodDao() {
        return foodDao;
    }

    public void setFoodDao(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    public void findFoodsByKeyword(String keyword, Handler mHandler, Context mContext) {

        Set<Food> foods = new HashSet<>();

        // fetch any matching foods from the API
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://chomp-food-nutrition-database-v2.p.rapidapi.com/food/branded/search.php?page=1&country=Australia&keyword=" + keyword)
                    .get()
                    .addHeader("X-RapidAPI-Key", "9f55517a4fmshac05f95235696eep1e1860jsn4d810b98d8b6")
                    .addHeader("X-RapidAPI-Host", "chomp-food-nutrition-database-v2.p.rapidapi.com")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Request request, final IOException e) {
                    mHandler.post(() -> {
                        Toast.makeText(mContext, "Error communicating with server.", Toast.LENGTH_SHORT).show();
                        mainClass.cancelSearch();
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    final String message = response.body().string();
                    mHandler.post(() -> {
                        try {
                            JSONObject responseJson = new JSONObject(message);
                            JSONArray jsonArray = responseJson.getJSONArray("items");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Food food = new Food();
                                    food.setName(jsonObject.getString("name"));

                                    JSONObject packageGrams = jsonObject.getJSONObject("package");
                                    int quantity = packageGrams.getInt("quantity");
                                    float amount = quantity / 100.0f;

                                    // get nutrients
                                    JSONArray nutrients = jsonObject.getJSONArray("nutrients");
                                    boolean energyFound = false;
                                    boolean fatFound = false;
                                    boolean carbohydratesFound = false;
                                    boolean proteinsFound = false;
                                    for (int j = 0; j < nutrients.length(); j++) {
                                        JSONObject nutrient = nutrients.getJSONObject(j);
                                        if (nutrient.getString("name").equals("Energy")) {
                                            food.setCalories((int) (nutrient.getInt("per_100g") * amount));
                                            energyFound = true;
                                        }
                                        if (nutrient.getString("name").equals("Fat")) {
                                            food.setFat(nutrient.getInt("per_100g") * amount);
                                            fatFound = true;
                                        }
                                        if (nutrient.getString("name").equals("Carbohydrates")) {
                                            food.setCarbohydrates(nutrient.getInt("per_100g") * amount);
                                            carbohydratesFound = true;
                                        }
                                        if (nutrient.getString("name").equals("Proteins")) {
                                            food.setProtein(nutrient.getInt("per_100g") * amount);
                                            proteinsFound = true;
                                        }
                                    }
                                    if (energyFound && fatFound && carbohydratesFound && proteinsFound) {
                                        System.out.println(food);
                                        foods.add(food);
                                    }
                                } catch (JSONException jsone) {
                                    // response did not contain the food or food entry was incomplete
                                }
                            }
                        } catch (JSONException jsone) {
                            jsone.printStackTrace();
                            // response was invalid json
                        }

                        // add any matching foods from internal database
                        foods.addAll(foodDao.findFoodsByKeyword(keyword));

                        mainClass.displaySearchResults(new ArrayList<>(foods));
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        public void deleteFood (Food food){
            foodDao.deleteFoodByName(food.getName());
        }

        public void saveFood (Food food){
            foodDao.saveFood(food);
        }
    }
