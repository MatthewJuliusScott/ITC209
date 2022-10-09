package com.itc209.assignment4.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Food implements Parcelable {

    public static final Parcelable.Creator<Food> CREATOR
            = new Parcelable.Creator<Food>() {

        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };
    private String name;
    private int calories;
    private float fat;
    private float protein;
    private float carbohydrates;

    public Food() {
        super();
    }

    private Food(Parcel in) {
        this.name = in.readString();
        this.calories = in.readInt();
        this.fat = in.readFloat();
        this.protein = in.readFloat();
        this.carbohydrates = in.readFloat();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeInt(calories);
        out.writeFloat(fat);
        out.writeFloat(protein);
        out.writeFloat(carbohydrates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return calories == food.calories && Float.compare(food.fat, fat) == 0 && Float.compare(food.protein, protein) == 0 && Float.compare(food.carbohydrates, carbohydrates) == 0 && name.equals(food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
