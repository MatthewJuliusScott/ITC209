package com.itc209.assignment4;

import com.itc209.assignment4.model.Food;

import java.util.List;

public interface DisplaySearchResultsCallBack {
    void displaySearchResults(List<Food> food);

    void cancelSearch();
}
