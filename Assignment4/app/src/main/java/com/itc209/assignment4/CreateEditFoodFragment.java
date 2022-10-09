package com.itc209.assignment4;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.itc209.assignment4.model.Food;

import java.util.ArrayList;

public class CreateEditFoodFragment extends DialogFragment {

    private Food food;

    private PieChart chart;

    public CreateEditFoodFragment() {
    }

    private CreateEditFoodFragment(Food food) {
        this.food = food;
    }

    public static CreateEditFoodFragment newInstance(Food food) {
        CreateEditFoodFragment fragment = new CreateEditFoodFragment(food);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("food", food);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_edit_food, container, false);

        // pre-fill the form with food details
        EditText name = view.findViewById(R.id.txt_name);
        EditText calories = view.findViewById(R.id.txt_calories);
        EditText protein = view.findViewById(R.id.txt_protein);
        EditText carbohydrates = view.findViewById(R.id.txt_carbohydrates);
        EditText fat = view.findViewById(R.id.txt_fat);

        // set orientation of layout
        LinearLayout layout = view.findViewById(R.id.create_edit_food_container);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layout.setOrientation(LinearLayout.HORIZONTAL);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            layout.setOrientation(LinearLayout.VERTICAL);
        }

        if (savedInstanceState != null) {
            food = savedInstanceState.getParcelable("food");
        } else {
            if (food != null) {
                name.setText(food.getName());
                calories.setText(String.valueOf(food.getCalories()));
                protein.setText(String.valueOf(food.getProtein()));
                carbohydrates.setText(String.valueOf(food.getCarbohydrates()));
                fat.setText(String.valueOf(food.getFat()));
            }
        }
        resetButtons(view);

        // check food validity and reset buttons on edit
        name.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                resetButtons(getView());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        calories.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                resetButtons(getView());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        protein.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                resetButtons(getView());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        carbohydrates.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                resetButtons(getView());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        fat.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                resetButtons(getView());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }

    public void resetButtons(View view) {
        Button addButton = view.findViewById(R.id.button_create_edit_food_save);
        Button cancelButton = view.findViewById(R.id.button_create_edit_food_cancel);

        String name = ((EditText) view.findViewById(R.id.txt_name)).getText().toString();
        String caloriesStr = ((EditText) view.findViewById(R.id.txt_calories)).getText().toString();
        String proteinStr = ((EditText) view.findViewById(R.id.txt_protein)).getText().toString();
        String carbohydratesStr = ((EditText) view.findViewById(R.id.txt_carbohydrates)).getText().toString();
        String fatStr = ((EditText) view.findViewById(R.id.txt_fat)).getText().toString();

        // if food details have been filled in
        if (Utils.isNotBlank(name) && Utils.isNotBlank(caloriesStr) && Utils.isNotBlank(proteinStr) && Utils.isNotBlank(carbohydratesStr) && Utils.isNotBlank(fatStr)) {

            food = new Food(name, Integer.parseInt(caloriesStr), Float.parseFloat(fatStr), Float.parseFloat(proteinStr), Float.parseFloat(carbohydratesStr) );

            // create the pie chart of the food's nutrition profile
            chart = view.findViewById(R.id.chart_food);
            chart.setUsePercentValues(true);
            chart.getDescription().setEnabled(false);
            chart.setDrawHoleEnabled(false);
            chart.setEntryLabelTextSize(18f);

            ArrayList<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(food.getCarbohydrates(), "Carbs"));
            entries.add(new PieEntry(food.getFat(), "Fat"));
            entries.add(new PieEntry(food.getProtein(), "Protein"));
            chart.setEntryLabelColor(Color.BLACK);

            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(ContextCompat.getColor(getContext(), R.color.protein), ContextCompat.getColor(getContext(), R.color.fat), ContextCompat.getColor(getContext(), R.color.carbohydrates));
            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(16f);
            data.setValueTextColor(Color.BLACK);
            chart.animateY(1400, Easing.EaseInOutQuad);

            Legend legend = chart.getLegend();
            legend.setEnabled(false);

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                chart.setExtraOffsets(5, 20, 5, 20);
                chart.setMinimumHeight(displayMetrics.heightPixels - 300);
            } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                chart.setExtraOffsets(5, 0, 5, 150);
                chart.setMinimumHeight(displayMetrics.heightPixels - 50);
            }

            chart.setData(data);
            chart.invalidate();

            // save food button enabled
            addButton.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.positive, null)));
            addButton.setEnabled(true);
            addButton.setOnClickListener(v -> {
                FragmentActivity activity = getActivity();
                if (activity instanceof MainActivity) {
                    try {
                        ((MainActivity) activity).addFoodToIntake(food);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getDialog().dismiss();
                }
            });
        } else {
            // save button disabled
            addButton.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.disabled, null)));
            addButton.setEnabled(false);
        }

        // cancel button
        cancelButton.setOnClickListener(v -> getDialog().dismiss());
    }
}