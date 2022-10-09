package com.itc209.assignment4;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itc209.assignment4.adapter.FoodAdapter;
import com.itc209.assignment4.model.Food;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private ArrayList<Food> foods;

    public SearchResultsFragment() {
    }

    private SearchResultsFragment(List<Food> foods) {
        this.foods = (ArrayList<Food>) foods;
    }

    public static SearchResultsFragment newInstance(List<Food> foods) {
        SearchResultsFragment fragment = new SearchResultsFragment(foods);
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
        outState.putParcelableArrayList("foods", foods);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        if (savedInstanceState != null) {
            foods = savedInstanceState.getParcelableArrayList("foods");
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        // Add the following lines to create RecyclerView
        recyclerView = view.findViewById(R.id.searchResultsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        FoodAdapter foodAdapter = new FoodAdapter(foods);
        recyclerView.setAdapter(foodAdapter);
        foodAdapter.setContext(getContext());
        foodAdapter.setFragment(this);
        resetButtons(view, foodAdapter.getSelected());
        return view;
    }

    public void resetButtons(View view, int selected) {

        Button addButton = view.findViewById(R.id.button_button_search_results_add);
        Button editButton = view.findViewById(R.id.button_search_results_edit);
        Button cancelButton = view.findViewById(R.id.button_search_results_cancel);

        // search result selected
        if (selected > -1) {
            // add search result to intake button
            addButton.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.positive, null)));
            addButton.setEnabled(true);
            addButton.setOnClickListener(v -> {
                FragmentActivity activity = getActivity();
                if (activity instanceof MainActivity) {
                    try {
                        ((MainActivity) activity).addFoodToIntake(foods.get(selected));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getDialog().dismiss();
                }
            });

            // edit food button enabled
            editButton.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.neutral, null)));
            editButton.setEnabled(true);
            editButton.setOnClickListener(v -> {
                FragmentActivity activity = getActivity();
                if (activity instanceof MainActivity) {
                    try {
                        ((MainActivity) activity).editFood(foods.get(selected));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getDialog().dismiss();
                }
            });
        } else {
            // no search result selected, edit and add food button disabled
            addButton.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.disabled, null)));
            addButton.setEnabled(false);

            editButton.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.disabled, null)));
            editButton.setEnabled(false);
        }

        // cancel button
        cancelButton.setOnClickListener(v -> getDialog().dismiss());
    }
}