package com.itc209.assignment4;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.itc209.assignment4.model.Constraints;

public class DailyGoalsLimitsFragment extends DialogFragment {

    Constraints constraints;

    public DailyGoalsLimitsFragment() {
    }

    private DailyGoalsLimitsFragment(Constraints constraints) {
        this.constraints = constraints;
    }

    public static DailyGoalsLimitsFragment newInstance(Constraints constraints) {
        return new DailyGoalsLimitsFragment(constraints);
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
        outState.putParcelable("constraints", constraints);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_goals_limits, container, false);

        // pre-fill the form with goals and limits details
        EditText caloriesGoals = view.findViewById(R.id.txt_goals_calories);
        EditText proteinGoals = view.findViewById(R.id.txt_goals_protein);
        EditText carbohydratesGoals = view.findViewById(R.id.txt_goals_carbohydrates);
        EditText fatGoals = view.findViewById(R.id.txt_goals_fat);

        EditText caloriesLimits = view.findViewById(R.id.txt_limits_calories);
        EditText proteinLimits = view.findViewById(R.id.txt_limits_protein);
        EditText carbohydratesLimits = view.findViewById(R.id.txt_limits_carbohydrates);
        EditText fatLimits = view.findViewById(R.id.txt_limits_fat);

        if (savedInstanceState != null) {
            constraints = savedInstanceState.getParcelable("constraints");
        } else {
            if (constraints != null) {
                caloriesGoals.setText(String.valueOf(constraints.getCaloriesGoal()));
                fatGoals.setText(String.valueOf(constraints.getFatGoal()));
                proteinGoals.setText(String.valueOf(constraints.getProteinGoal()));
                carbohydratesGoals.setText(String.valueOf(constraints.getCarbohydratesGoal()));

                caloriesLimits.setText(String.valueOf(constraints.getCaloriesLimit()));
                fatLimits.setText(String.valueOf(constraints.getFatLimit()));
                proteinLimits.setText(String.valueOf(constraints.getProteinLimit()));
                carbohydratesLimits.setText(String.valueOf(constraints.getCarbohydratesLimit()));

            }
        }

        // set button actions
        Button saveButton = view.findViewById(R.id.button_daily_goals_limits_save);
        Button cancelButton = view.findViewById(R.id.button_daily_goals_limits_cancel);

        saveButton.setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                try {

                    int caloriesGoal = Integer.parseInt(caloriesGoals.getText().toString());
                    float fatGoal = Float.parseFloat(fatGoals.getText().toString());
                    float proteinGoal = Float.parseFloat(proteinGoals.getText().toString());
                    float carbohydratesGoal = Float.parseFloat(carbohydratesGoals.getText().toString());

                    int caloriesLimit = Integer.parseInt(caloriesLimits.getText().toString());
                    float fatLimit = Float.parseFloat(fatLimits.getText().toString());
                    float proteinLimit = Float.parseFloat(proteinLimits.getText().toString());
                    float carbohydratesLimit = Float.parseFloat(carbohydratesLimits.getText().toString());

                    constraints = new Constraints(caloriesGoal, fatGoal, proteinGoal, carbohydratesGoal, caloriesLimit, fatLimit, proteinLimit, carbohydratesLimit);

                    ((MainActivity) activity).saveConstraints(constraints);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Dialog dialog = getDialog();
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // cancel button
        cancelButton.setOnClickListener(v -> {
            Dialog dialog = getDialog();
            if (dialog != null) {
                dialog.dismiss();
            }
        });

        return view;
    }
}