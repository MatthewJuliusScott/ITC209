package com.itc209.assignment4;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.itc209.assignment4.controller.MainController;
import com.itc209.assignment4.model.Intake;
import com.itc209.assignment4.model.Notification;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "9b7494ad-b68a-4863-b191-c13e981d3b78";
    MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainController = new MainController(getApplicationContext());
        try {
            resetButtons();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetButtons() throws Exception {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // reset button max width relative to screen size
        Button button;
        button = findViewById(R.id.button_add_food_to_intake);
        button.setMaxWidth(displayMetrics.widthPixels / 4);

        button = findViewById(R.id.button_remove_food_from_intake);
        button.setMaxWidth(displayMetrics.widthPixels / 4);

        // grey out remove food from intake if there is no intake for today
        List<Intake> intakes = mainController.getIntakeController().getIntakesByTime(Utils.dayStart(new Date()), Utils.dayEnd(new Date()));
        if (intakes.size() > 0) {
            button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.negative, null)));
            button.setEnabled(true);
        } else {
            button.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.disabled, null)));
            button.setEnabled(false);
        }

        button = findViewById(R.id.button_set_daily_goals);
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
        // TODO implement
    }

    public void setDailyGoals(View view) {
        Intent setDailyGoals = new Intent(MainActivity.this,SetDailyGoals.class);
        startActivity(setDailyGoals);
        finish();
    }
}