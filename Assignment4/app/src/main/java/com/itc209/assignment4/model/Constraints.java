package com.itc209.assignment4.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Constraints implements Parcelable {

    public static final Parcelable.Creator<Constraints> CREATOR
            = new Parcelable.Creator<Constraints>() {

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Constraints createFromParcel(Parcel in) {
            return new Constraints(in);
        }

        @Override
        public Constraints[] newArray(int size) {
            return new Constraints[size];
        }
    };

    private int caloriesGoal;
    private float fatGoal;
    private float proteinGoal;
    private float carbohydratesGoal;

    private int caloriesLimit;
    private float fatLimit;
    private float proteinLimit;
    private float carbohydratesLimit;


    public Constraints() {
        super();
    }

    public Constraints(int caloriesGoal, float fatGoal, float proteinGoal, float carbohydratesGoal, int caloriesLimit, float fatLimit, float proteinLimit, float carbohydratesLimit) {
        this.caloriesGoal = caloriesGoal;
        this.fatGoal = fatGoal;
        this.proteinGoal = proteinGoal;
        this.carbohydratesGoal = carbohydratesGoal;
        this.caloriesLimit = caloriesLimit;
        this.fatLimit = fatLimit;
        this.proteinLimit = proteinLimit;
        this.carbohydratesLimit = carbohydratesLimit;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private Constraints(Parcel in) {
        this.caloriesGoal = in.readInt();
        this.fatGoal = in.readFloat();
        this.proteinGoal = in.readFloat();
        this.carbohydratesGoal = in.readFloat();
        this.caloriesLimit = in.readInt();
        this.fatLimit = in.readFloat();
        this.proteinLimit = in.readFloat();
        this.carbohydratesLimit = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(caloriesGoal);
        out.writeFloat(fatGoal);
        out.writeFloat(proteinGoal);
        out.writeFloat(carbohydratesGoal);
        out.writeInt(caloriesLimit);
        out.writeFloat(fatLimit);
        out.writeFloat(proteinLimit);
        out.writeFloat(carbohydratesLimit);
    }

    public float getFatGoal() {
        return fatGoal;
    }

    public void setFatGoal(float fatGoal) {
        this.fatGoal = fatGoal;
    }

    public float getProteinGoal() {
        return proteinGoal;
    }

    public void setProteinGoal(float proteinGoal) {
        this.proteinGoal = proteinGoal;
    }

    public float getCarbohydratesGoal() {
        return carbohydratesGoal;
    }

    public void setCarbohydratesGoal(float carbohydratesGoal) {
        this.carbohydratesGoal = carbohydratesGoal;
    }

    public float getFatLimit() {
        return fatLimit;
    }

    public void setFatLimit(float fatLimit) {
        this.fatLimit = fatLimit;
    }

    public float getProteinLimit() {
        return proteinLimit;
    }

    public void setProteinLimit(float proteinLimit) {
        this.proteinLimit = proteinLimit;
    }

    public float getCarbohydratesLimit() {
        return carbohydratesLimit;
    }

    public void setCarbohydratesLimit(float carbohydratesLimit) {
        this.carbohydratesLimit = carbohydratesLimit;
    }

    public int getCaloriesGoal() {
        return caloriesGoal;
    }

    public void setCaloriesGoal(int caloriesGoal) {
        this.caloriesGoal = caloriesGoal;
    }

    public int getCaloriesLimit() {
        return caloriesLimit;
    }

    public void setCaloriesLimit(int caloriesLimit) {
        this.caloriesLimit = caloriesLimit;
    }
}
