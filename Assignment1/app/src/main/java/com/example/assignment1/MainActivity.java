package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set edit text input limit without limiting the placeholder text
        EditText editText = (EditText) findViewById(R.id.employee_id);
        editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(7)});
    }

    public boolean isBlank(String field) {
        if (field != null && field.length() > 0) return false;
        return true;
    }

    public void validateForm(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        String message = "";
        EditText editText;
        String regex;

        int[] fields = {R.id.first_name, R.id.last_name, R.id.employee_id, R.id.email_address};

        // validate all required fields are present
        for (int field : fields) {
            editText = (EditText) findViewById(field);
            if (isBlank(editText.getText().toString())) {
                message += editText.getHint() + " is a mandatory field." + System.lineSeparator();
            }
        }

        // validate first and last name only contain the alphabet, ‘.’, ‘-‘, and blank spaces
        fields = new int[] {R.id.first_name, R.id.last_name};
        regex = "(\\p{IsAlphabetic}|\\.|\\-|\\s)*";
        for (int field : fields) {
            editText = (EditText) findViewById(field);
            if (!Pattern.matches(regex, editText.getText().toString())) {
                message += editText.getHint() + " can only contain the alphabet, ‘.’, ‘-‘, and blank spaces." + System.lineSeparator();
            }
        }

        // validate employee ID should be exactly a 7-digit number and cannot start with zero
        regex = "(\\d)*";
        editText = (EditText) findViewById(R.id.employee_id);
        if (!Pattern.matches(regex, editText.getText().toString())) {
            message += editText.getHint() + " must be a 7-digit number." + System.lineSeparator();
        } else if (editText.getText().toString().length() != 7) {
            message += editText.getHint() + " must be a 7-digit number." + System.lineSeparator();
        } else if (editText.getText().toString().startsWith("0")) {
            message += editText.getHint() + " must not start with a zero." + System.lineSeparator();
        }

        // validate email should be a valid email pattern
        regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        editText = (EditText) findViewById(R.id.email_address);
        if (!Pattern.matches(regex, editText.getText().toString())) {
            message += editText.getHint() + " must be valid." + System.lineSeparator();
        }

        // failure
        builder.setTitle("Error");
        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        alert.dismiss();
                    }
                });

        // success
        if (message.length() == 0) {
            message = "You entered: " + System.lineSeparator();
            fields = new int[] {R.id.first_name, R.id.last_name, R.id.employee_id, R.id.email_address};
            for (int field : fields) {
                editText = (EditText) findViewById(field);
                message += editText.getHint() + ": " + editText.getText().toString() + System.lineSeparator();
            }
            builder.setTitle("Success!");
            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            alert.dismiss();
                        }
                    });
        }

        builder.setMessage(message);
        builder.setCancelable(true);
        alert = builder.create();
        alert.show();
    }
}