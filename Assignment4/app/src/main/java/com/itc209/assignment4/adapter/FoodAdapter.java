package com.itc209.assignment4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.itc209.assignment4.R;
import com.itc209.assignment4.SearchResultsFragment;
import com.itc209.assignment4.model.Food;

import java.text.DecimalFormat;
import java.util.List;

public class FoodAdapter extends
        RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    private final List<Food> foods;
    private int selected = -1;
    private SearchResultsFragment fragment;

    public FoodAdapter(List<Food> foods) {
        this.foods = foods;
    }

    public void setFragment(SearchResultsFragment fragment) {
        this.fragment = fragment;
    }

    public void select(int position) {
        if (selected == position) {
            selected = -1;
        } else {
            selected = position;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_food, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foods.get(position);

        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(food.getName());

        TextView caloriesTextView = holder.caloriesTextView;
        caloriesTextView.setText(food.getCalories() + "kj");

        TextView fatTextView = holder.fatTextView;
        fatTextView.setText(df.format(food.getFat()) + "g");

        TextView proteinTextView = holder.proteinTextView;
        proteinTextView.setText(df.format(food.getProtein()) + "g");

        TextView carbohydratesTextView = holder.carbohydratesTextView;
        carbohydratesTextView.setText(df.format(food.getCarbohydrates()) + "g");

        if (position == selected) {
            holder.itemView.setBackgroundColor(ResourcesCompat.getColor(nameTextView.getContext().getResources(), R.color.rowSelected, null));
        } else if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(ResourcesCompat.getColor(nameTextView.getContext().getResources(), R.color.row, null));
        } else {
            holder.itemView.setBackgroundColor(ResourcesCompat.getColor(nameTextView.getContext().getResources(), R.color.rowAlternate, null));
        }
    }

    @Override
    public int getItemCount() {
        if (foods != null) {
            return foods.size();
        } else {
            return 0;
        }
    }

    public int getSelected() {
        return selected;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView caloriesTextView;
        public TextView fatTextView;
        public TextView proteinTextView;
        public TextView carbohydratesTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = itemView.findViewById(R.id.food_name);
            caloriesTextView = itemView.findViewById(R.id.food_calories);
            fatTextView = itemView.findViewById(R.id.food_fat);
            proteinTextView = itemView.findViewById(R.id.food_protein);
            carbohydratesTextView = itemView.findViewById(R.id.food_carbohydrates);

            itemView.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                FoodAdapter.this.select(position);
                notifyDataSetChanged();
                if (fragment != null) {
                    try {
                        View fragmentView = fragment.getView();
                        if (fragmentView != null) {
                            fragment.resetButtons(fragmentView, selected);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
