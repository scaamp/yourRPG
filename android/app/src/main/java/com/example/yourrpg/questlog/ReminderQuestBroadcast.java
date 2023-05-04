package com.example.yourrpg.questlog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.yourrpg.R;

import java.util.concurrent.ThreadLocalRandom;

public class ReminderQuestBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //int id = (int) System.currentTimeMillis();

        int id = (int) ThreadLocalRandom.current().nextInt();
        Bundle bundle = intent.getBundleExtra("bundle");
        String text = (String)bundle.getString("XD");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify").setSmallIcon(R.drawable.ic_heart)
                .setContentTitle("Hurry up!").setContentText("You have 1 day to complete your task: " + text).setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(id,builder.build());
    }
}
