package com.itc209.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.itc209.assignment4.adapter.IntakeAdapter;
import com.itc209.assignment4.controller.ConstraintController;
import com.itc209.assignment4.controller.FoodController;
import com.itc209.assignment4.controller.IntakeController;
import com.itc209.assignment4.model.Food;
import com.itc209.assignment4.model.Intake;
import com.itc209.assignment4.model.Notification;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "9b7494ad-b68a-4863-b191-c13e981d3b78";
    FoodController foodController;
    IntakeController intakeController;
    ConstraintController constraintController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodController = new FoodController(getApplicationContext());
        intakeController = new IntakeController(getApplicationContext(), foodController);
        constraintController = new ConstraintController(getApplicationContext(), intakeController);

        // TEST DATA
        Food food1 = new Food("food1", 100, 0.1f, 0.2f, 0.2f);
        Food food2 = new Food("food2", 110, 0.15f, 0.25f, 0.15f);
        foodController.saveFood(food1);
        foodController.saveFood(food2);
        intakeController.saveIntake(new Intake(new Date(), food1));
        intakeController.saveIntake(new Intake(new Date(), food2));

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

    public void fillRemoveIntakeRecyclerView() throws Exception {
        RecyclerView removeIntakeRecyclerView = findViewById(R.id.removeIntakeRecyclerView);
        List<Intake> intakes = intakeController.getIntakesByTime(Utils.dayStart(new Date()), Utils.dayEnd(new Date()));
        IntakeAdapter adapter = new IntakeAdapter(intakes);
        adapter.addContext(MainActivity.this);
        removeIntakeRecyclerView.setAdapter(adapter);
        removeIntakeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void resetButtons() throws Exception {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // reset button max width relative to screen size
        Button button;
        button = findViewById(R.id.buttonAddFoodToIntake);
        button.setMaxWidth(displayMetrics.widthPixels / 4);

        button = findViewById(R.id.buttonRemoveFoodFromIntake);
        button.setMaxWidth(displayMetrics.widthPixels / 4);

        // grey out remove food from intake if there is no intake selected
        RecyclerView removeIntakeRecyclerView = findViewById(R.id.removeIntakeRecyclerView);
        IntakeAdapter intakeAdapter = (IntakeAdapter)removeIntakeRecyclerView.getAdapter();
        Intake intake = intakeAdapter.getSelectedIntake();
        if (intake != null) {
            button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.negative, null)));
            button.setEnabled(true);
        } else {
            button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.disabled, null)));
            button.setEnabled(false);
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

    public void addFoodToIntake(View view) {
        Intent addFoodToIntake = new Intent(MainActivity.this,AddFoodToIntake.class);
        startActivity(addFoodToIntake);
        finish();
    }

    public void removeFoodFromIntake(View view) {
        RecyclerView removeIntakeRecyclerView = findViewById(R.id.removeIntakeRecyclerView);
        IntakeAdapter intakeAdapter = (IntakeAdapter)removeIntakeRecyclerView.getAdapter();
        Intake intake = intakeAdapter.getSelectedIntake();
        if (intake != null) {
            // delete selected intake and then reset the selection and remove button
            intakeController.deleteIntake(intake.getDate(), intake.getFood().getName());
            intakeAdapter.removeSelected();
            Button button = findViewById(R.id.buttonRemoveFoodFromIntake);
            button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.disabled, null)));
            button.setEnabled(false);
        }
    }

    public void setDailyGoals(View view) {
        Intent setDailyGoals = new Intent(MainActivity.this,SetDailyGoals.class);
        startActivity(setDailyGoals);
        finish();
    }
}