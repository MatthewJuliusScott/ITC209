package com.itc209.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.itc209.assignment3.model.Constraint;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constraint diet_calorie_limit = new Constraint();
        diet_calorie_limit.setIsGoal(false);
        diet_calorie_limit.setAmount(1000);
        diet_calorie_limit.setType(Constraint.Type.CALORIE);
        diet_calorie_limit.setId(1);

        float currentCalorieAmount = 895;

        Log.e();

        if (isOverLimit(diet_calorie_limit, currentCalorieAmount)) {
            System.out.println("You are over your " + diet_calorie_limit.getType().name() + " limit. Fat bitch.");
        } else {
            System.out.println("You are under your" + diet_calorie_limit.getType().name() + " limit. Good job!");
        }

    }

    public boolean isOverLimit(Constraint constraint, float currentAmount) {
        if (currentAmount > constraint.getAmount()) {
            return true;
        } else {
            return false;
        }
    }
}