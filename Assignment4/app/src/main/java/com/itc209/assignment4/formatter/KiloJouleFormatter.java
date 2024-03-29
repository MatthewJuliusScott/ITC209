package com.itc209.assignment4.formatter;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class KiloJouleFormatter extends ValueFormatter {

    private static final DecimalFormat df = new DecimalFormat("0");

    @Override
    public String getFormattedValue(float value) {
        return df.format((value * 10.0f)) + "kj";
    }
}
