package com.itc209.assignment4;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itc209.assignment4.adapter.FoodAdapter;
import com.itc209.assignment4.model.Food;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsFragment extends DialogFragment {

    // Add RecyclerView member
    private RecyclerView recyclerView;
    public SearchResultsFragment() {
    }

    private ArrayList<Food> foods;

    private SearchResultsFragment(List<Food> foods) {
        this.foods = (ArrayList<Food>) foods;
    }

    public static SearchResultsFragment newInstance(List<Food> foods) {
        SearchResultsFragment fragment = new SearchResultsFragment(foods);
        return fragment;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
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
        recyclerView.setAdapter(new FoodAdapter(foods));

        return view;
    }
}