package com.itc209.assignment4.formatter;

import com.github.mikephil.charting.formatter.ValueFormatter;
import com.itc209.assignment4.model.Nutrient;

public class NutritionFormatter extends ValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        if (value == (float)Nutrient.PROTEIN.ordinal()) {
            return "Protein";
        } else if (value == (float)Nutrient.FAT.ordinal()) {
            return "Fat";
        } else if (value == (float)Nutrient.CARBOHYDRATE.ordinal()) {
            return "Carbs";
        } else {
            return "";
        }
    }
}
