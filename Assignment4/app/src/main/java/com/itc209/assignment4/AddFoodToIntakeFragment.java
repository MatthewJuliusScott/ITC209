package com.itc209.assignment4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;

public class AddFoodToIntakeFragment extends DialogFragment {

    Dialog dialog = null;

    public AddFoodToIntakeFragment() {
    }

    public static AddFoodToIntakeFragment newInstance() {
        AddFoodToIntakeFragment fragment = new AddFoodToIntakeFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.fragment_add_food_to_intake, null);
        alertDialogBuilder.setView(customLayout);
        dialog = alertDialogBuilder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // search
        MaterialButton searchButton = customLayout.findViewById(R.id.button_search);
        searchButton.setOnClickListener(view -> {
            dialog.dismiss();
            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).displaySearch(view);
            }
        });

        // enter new food
        MaterialButton enterNewFoodButton = customLayout.findViewById(R.id.button_enter_new_food);
        enterNewFoodButton.setOnClickListener(view -> {
            dialog.dismiss();
            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).editFood(null);
            }
        });


        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        EditText mEditText = view.findViewById(R.id.txt_enter_name);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}