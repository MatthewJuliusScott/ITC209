package com.itc209.assignment4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.itc209.assignment4.adapter.IntakeAdapter;
import com.itc209.assignment4.controller.ConstraintsController;
import com.itc209.assignment4.controller.FoodController;
import com.itc209.assignment4.controller.IntakeController;
import com.itc209.assignment4.formatter.GramsFormatter;
import com.itc209.assignment4.formatter.KiloJouleFormatter;
import com.itc209.assignment4.formatter.NutritionFormatter;
import com.itc209.assignment4.model.Constraints;
import com.itc209.assignment4.model.Food;
import com.itc209.assignment4.model.Intake;
import com.itc209.assignment4.model.Notification;
import com.itc209.assignment4.model.Nutrient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DisplaySearchResultsCallBack {

    private static final String CHANNEL_ID = "9b7494ad-b68a-4863-b191-c13e981d3b78";
    private FoodController foodController;
    private IntakeController intakeController;
    private ConstraintsController constraintsController;
    private BarChart chart;
    private ProgressBar progressSpinner;

    private void setGraphData() throws Exception {

        Food totalFood = intakeController.getTotalFood(Utils.dayStart(new Date()), Utils.dayEnd(new Date()));
        ArrayList<BarEntry> values = new ArrayList<>();
        // add the total food nutrition values
        values.add(new BarEntry(Nutrient.PROTEIN.ordinal(), totalFood.getProtein()));
        values.add(new BarEntry(Nutrient.FAT.ordinal(), totalFood.getFat()));
        values.add(new BarEntry(Nutrient.CARBOHYDRATE.ordinal(), totalFood.getCarbohydrates()));
        values.add(new BarEntry(Nutrient.CALORIES.ordinal(), totalFood.getCalories() / 10f));

        BarDataSet dataSet;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();
        } else {
            dataSet = new BarDataSet(values, null);
            dataSet.setDrawIcons(false);

            dataSet.setColors(ContextCompat.getColor(getApplicationContext(), R.color.protein), ContextCompat.getColor(getApplicationContext(), R.color.fat), ContextCompat.getColor(getApplicationContext(), R.color.carbohydrates), ContextCompat.getColor(getApplicationContext(), R.color.calories));

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            BarData data = new BarData(dataSets);
            data.setDrawValues(false);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            chart.setData(data);
        }
    }

    public void displaySearch(View view) {
        FragmentManager fm = getSupportFragmentManager();
        SearchForFoodFragment fragment = SearchForFoodFragment.newInstance();
        fragment.show(fm, "fragment_search_for_food");
    }

    public void search(View view) {
        if (view instanceof TextView) {
            String keyword = ((TextView) view).getText().toString();
            progressSpinner.setVisibility(View.VISIBLE);
            Handler mHandler = new Handler();
            foodController.findFoodsByKeyword(keyword, mHandler, getApplicationContext());
        }
    }

    @Override
    public void displaySearchResults(List<Food> foods) {
        progressSpinner.setVisibility(View.GONE);
        FragmentManager fm = getSupportFragmentManager();
        SearchResultsFragment fragment = SearchResultsFragment.newInstance(foods);
        fragment.show(fm, "fragment_search_results");
    }

    @Override
    public void cancelSearch() {
        progressSpinner.setVisibility(View.GONE);
    }

    public void editFood(Food food) {
        FragmentManager fm = getSupportFragmentManager();
        CreateEditFoodFragment fragment = CreateEditFoodFragment.newInstance(food);
        fragment.show(fm, "fragment_create_edit_food");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressSpinner = findViewById(R.id.progressSpinner);
        progressSpinner.setVisibility(View.GONE);

        // allow networking on the main thread
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        foodController = new FoodController(getApplicationContext(), this);
        intakeController = new IntakeController(getApplicationContext(), foodController);
        constraintsController = new ConstraintsController(getApplicationContext(), intakeController);

        try {
            drawGraph();
        } catch (Exception e) {
            System.err.println("Error rendering graph: " + System.lineSeparator() + e.getMessage());
            e.printStackTrace();
        }

        createNotificationChannel();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        try {
            fillRemoveIntakeRecyclerView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            resetButtons();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawGraph() throws Exception {
        setupGraph();
        setGraphData();
    }

    private void setupGraph() {
        chart = findViewById(R.id.chart_intake);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(Integer.MAX_VALUE);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(4);
        xAxis.setValueFormatter(new NutritionFormatter());

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new GramsFormatter());
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setValueFormatter(new KiloJouleFormatter());
        rightAxis.setLabelCount(8, false);
        rightAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0);

        Legend legend = chart.getLegend();
        legend.setEnabled(false);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        chart.setMinimumHeight(displayMetrics.heightPixels - 600);
    }

    public void fillRemoveIntakeRecyclerView() throws Exception {
        RecyclerView removeIntakeRecyclerView = findViewById(R.id.removeIntakeRecyclerView);
        List<Intake> intakes = intakeController.getIntakesByTime(Utils.dayStart(new Date()), Utils.dayEnd(new Date()));
        IntakeAdapter adapter = new IntakeAdapter(intakes);
        adapter.setContext(MainActivity.this);
        removeIntakeRecyclerView.setAdapter(adapter);
        removeIntakeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void resetButtons() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // reset button max width relative to screen size
        Button button;
        button = findViewById(R.id.buttonDisplayAddFoodToIntake);
        button.setMaxWidth(displayMetrics.widthPixels / 4);

        button = findViewById(R.id.buttonRemoveFoodFromIntake);
        button.setMaxWidth(displayMetrics.widthPixels / 4);

        // grey out remove food from intake if there is no intake selected
        RecyclerView removeIntakeRecyclerView = findViewById(R.id.removeIntakeRecyclerView);
        IntakeAdapter intakeAdapter = (IntakeAdapter) removeIntakeRecyclerView.getAdapter();
        if (intakeAdapter != null) {
            Intake intake = intakeAdapter.getSelectedIntake();
            if (intake != null) {
                button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.negative, null)));
                button.setEnabled(true);
            } else {
                button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.disabled, null)));
                button.setEnabled(false);
            }
        }

        button = findViewById(R.id.buttonSetDailyGoals);
        button.setMaxWidth(displayMetrics.widthPixels / 4);
    }

    public void sendNotification(Notification notification) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getMessage())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notification.getMessage()))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (notification.getType().ordinal() == Notification.Type.POSITIVE.ordinal()) {
            builder.setSmallIcon(R.drawable.positive);
        } else if (notification.getType().ordinal() == Notification.Type.NEGATIVE.ordinal()) {
            builder.setSmallIcon(R.drawable.negative);
        } else {
            builder.setSmallIcon(R.drawable.neutral);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(Notification.getID(), builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void displayAddFoodToIntake(View view) {
        FragmentManager fm = getSupportFragmentManager();
        AddFoodToIntakeFragment fragment = AddFoodToIntakeFragment.newInstance();
        fragment.show(fm, "fragment_add_food_to_intake");
    }

    public void removeFoodFromIntake(View view) throws Exception {
        RecyclerView removeIntakeRecyclerView = findViewById(R.id.removeIntakeRecyclerView);
        IntakeAdapter intakeAdapter = (IntakeAdapter) removeIntakeRecyclerView.getAdapter();
        if (intakeAdapter != null) {
            Intake intake = intakeAdapter.getSelectedIntake();
            if (intake != null) {
                // delete selected intake and then reset the selection and remove button
                intakeController.deleteIntake(intake.getDate(), intake.getFood().getName());
                intakeAdapter.removeSelected();
                Button button = findViewById(R.id.buttonRemoveFoodFromIntake);
                button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.disabled, null)));
                button.setEnabled(false);
                setGraphData();
            }
        }
    }

    public void addFoodToIntake(Food food) throws Exception {
        foodController.saveFood(food);
        Intake intake = new Intake(new Date(), food);
        intakeController.saveIntake(intake);
        // add the new intake to the view
        RecyclerView removeIntakeRecyclerView = findViewById(R.id.removeIntakeRecyclerView);
        IntakeAdapter intakeAdapter = (IntakeAdapter) removeIntakeRecyclerView.getAdapter();
        if (intakeAdapter != null) {
            intakeAdapter.add(intake);
        }
        setGraphData();

        List<Notification> notifications = constraintsController.checkTriggers();
        for (Notification notification : notifications) {
            sendNotification(notification);
        }
    }

    public void setDailyGoals(View view) {
        FragmentManager fm = getSupportFragmentManager();
        DailyGoalsLimitsFragment fragment = DailyGoalsLimitsFragment.newInstance(constraintsController.getConstraints());
        fragment.show(fm, "fragment_set_daily_goals_limits");
    }

    public void saveConstraints(Constraints constraints) throws Exception {
        constraintsController.saveConstraints(constraints);
        List<Notification> notifications = constraintsController.checkTriggers();
        for (Notification notification : notifications) {
            sendNotification(notification);
        }
    }
}