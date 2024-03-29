package com.itc209.assignment4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

public class SearchForFoodFragment extends DialogFragment {
    Dialog dialog = null;

    public SearchForFoodFragment() {
    }

    public static SearchForFoodFragment newInstance() {
        SearchForFoodFragment fragment = new SearchForFoodFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.fragment_search_for_food, null);
        alertDialogBuilder.setView(customLayout);
        dialog = alertDialogBuilder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // set action listener
        EditText editText = customLayout.findViewById(R.id.txt_enter_name);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            // If triggered by an enter key, this is the event; otherwise, this is null.
            if (event != null) {
                if (!event.isShiftPressed()) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
            dialog.dismiss();
            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).search(editText);
            }
            return true;
        });
        return dialog;
    }
}