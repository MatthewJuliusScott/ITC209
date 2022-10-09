package com.itc209.assignment4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.itc209.assignment4.MainActivity;
import com.itc209.assignment4.R;
import com.itc209.assignment4.model.Intake;

import java.text.SimpleDateFormat;
import java.util.List;

public class IntakeAdapter extends
        RecyclerView.Adapter<IntakeAdapter.ViewHolder> {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
    private final List<Intake> intakes;
    private int selected = -1;
    private Context context;

    public IntakeAdapter(List<Intake> intakes) {
        this.intakes = intakes;
    }

    public int select(int position) {
        if (selected == position) {
            selected = -1;
        } else {
            selected = position;
        }
        return selected;
    }

    public void add(Intake intake) {
        intakes.add(intake);
        notifyItemInserted(intakes.size());
    }

    public void removeSelected() {
        intakes.remove(selected);
        notifyItemRemoved(selected);
        selected = -1;
        notifyDataSetChanged();
    }

    public Intake getSelectedIntake() {
        if (selected > -1) {
            return intakes.get(selected);
        }
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_intake, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
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
        return intakes.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

            nameTextView = itemView.findViewById(R.id.intake_name);
            timeTextView = itemView.findViewById(R.id.intake_time);
            caloriesTextView = itemView.findViewById(R.id.intake_calories);

            itemView.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                IntakeAdapter.this.select(position);
                notifyDataSetChanged();
                if (context.getClass().equals(MainActivity.class)) {
                    try {
                        ((MainActivity) context).resetButtons();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
