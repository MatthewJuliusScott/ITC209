package com.itc209.assignment4.formatter;

import com.github.mikephil.charting.formatter.ValueFormatter;
import com.itc209.assignment4.model.Nutrient;

public class NutritionFormatter extends ValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        if (value == (float) Nutrient.PROTEIN.ordinal()) {
            return "Protein(g)";
        } else if (value == (float) Nutrient.FAT.ordinal()) {
            return "Fat(g)";
        } else if (value == (float) Nutrient.CARBOHYDRATE.ordinal()) {
            return "Carbs(g)";
        } else if (value == (float) Nutrient.CALORIES.ordinal()) {
            return "Cals(kj)";
        } else {
            return "";
        }
    }
}
