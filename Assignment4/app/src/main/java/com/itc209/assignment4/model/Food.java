package com.itc209.assignment4.model;

public class Food {

    private String name;
    private int calories;
    private float fat;
    private float protein;
    private float carbohydrates;

    public Food() {
        super();
    }

    public Food(String name, int calories, float fat, float protein, float carbohydrates) {
        super();
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", calories=" + calories +
                ", fat=" + fat +
                ", protein=" + protein +
                ", carbohydrates=" + carbohydrates +
                '}';
    }
}
