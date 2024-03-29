package com.itc209.assignment4;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.github.mikephil.charting.animation.Easing;
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

    public CreateEditFoodFragment() {
    }

    private CreateEditFoodFragment(Food food) {
        this.food = food;
    }

    public static CreateEditFoodFragment newInstance(Food food) {
        return new CreateEditFoodFragment(food);
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                View view = getView();
                if (view != null) {
                    resetButtons(view);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        calories.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                View view = getView();
                if (view != null) {
                    resetButtons(view);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        protein.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                View view = getView();
                if (view != null) {
                    resetButtons(view);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        carbohydrates.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                View view = getView();
                if (view != null) {
                    resetButtons(view);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        fat.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                View view = getView();
                if (view != null) {
                    resetButtons(view);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }

    /**
     * Resets the save and cancel button based on the user's input, including the onClick
     * @param view the fragment main view
     */
    public void resetButtons(View view) {
        Button saveButton = view.findViewById(R.id.button_create_edit_food_save);
        Button cancelButton = view.findViewById(R.id.button_create_edit_food_cancel);

        String name = ((EditText) view.findViewById(R.id.txt_name)).getText().toString();
        String caloriesStr = ((EditText) view.findViewById(R.id.txt_calories)).getText().toString();
        String proteinStr = ((EditText) view.findViewById(R.id.txt_protein)).getText().toString();
        String carbohydratesStr = ((EditText) view.findViewById(R.id.txt_carbohydrates)).getText().toString();
        String fatStr = ((EditText) view.findViewById(R.id.txt_fat)).getText().toString();

        // if food details have been filled in
        if (Utils.isNotBlank(name) && Utils.isNotBlank(caloriesStr) && Utils.isNotBlank(proteinStr) && Utils.isNotBlank(carbohydratesStr) && Utils.isNotBlank(fatStr)) {

            food = new Food(name, Integer.parseInt(caloriesStr), Float.parseFloat(fatStr), Float.parseFloat(proteinStr), Float.parseFloat(carbohydratesStr));

            // create the pie chart of the food's nutrition profile
            PieChart chart = view.findViewById(R.id.chart_food);
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
            Context context = getContext();
            if (context != null) {
                dataSet.setColors(ContextCompat.getColor(context, R.color.protein), ContextCompat.getColor(context, R.color.fat), ContextCompat.getColor(context, R.color.carbohydrates));
            }
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
            saveButton.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.positive, null)));
            saveButton.setEnabled(true);
            saveButton.setOnClickListener(v -> {
                FragmentActivity activity = getActivity();
                if (activity instanceof MainActivity) {
                    try {
                        ((MainActivity) activity).addFoodToIntake(food);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Dialog dialog = getDialog();
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });
        } else {
            // save button disabled
            saveButton.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.disabled, null)));
            saveButton.setEnabled(false);
        }

        // cancel button
        cancelButton.setOnClickListener(v -> {
            Dialog dialog = getDialog();
            if (dialog != null) {
                dialog.dismiss();
            }
        });
    }
}