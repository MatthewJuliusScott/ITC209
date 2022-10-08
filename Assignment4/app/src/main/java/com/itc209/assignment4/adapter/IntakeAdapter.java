package com.itc209.assignment4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itc209.assignment4.R;
import com.itc209.assignment4.model.Intake;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.SimpleTimeZone;

public class IntakeAdapter extends
        RecyclerView.Adapter<IntakeAdapter.ViewHolder> {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
    private List<Intake> intakes;

    public IntakeAdapter(List<Intake> intakes) {
        this.intakes = intakes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_intake, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Intake intake = intakes.get(position);

        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(intake.getFood().getName());

        TextView timeTextView = holder.timeTextView;
        timeTextView.setText(sdf.format(intake.getDate()));

        TextView caloriesTextView = holder.caloriesTextView;
        caloriesTextView.setText(String.valueOf(intake.getFood().getCalories()));
    }

    @Override
    public int getItemCount() {
        return intakes.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView timeTextView;
        public TextView caloriesTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.intake_name);
            timeTextView = (TextView) itemView.findViewById(R.id.intake_time);
            caloriesTextView = (TextView) itemView.findViewById(R.id.intake_calories);
        }
    }
}
