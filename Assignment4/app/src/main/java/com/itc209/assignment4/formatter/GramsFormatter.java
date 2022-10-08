package com.itc209.assignment4.formatter;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class GramsFormatter extends ValueFormatter {

    private static final DecimalFormat df = new DecimalFormat("0");

    @Override
    public String getFormattedValue(float value) {
        return df.format(value) + "g";
    }
}
