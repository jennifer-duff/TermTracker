package com.jbd.termtracker.UI;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.jbd.termtracker.R;

public class MyReceiver extends BroadcastReceiver {
    String channel_id = "my channel";
    static int notificationId;

    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        createNotificationChannel(context, channel_id);
        Notification notification = new NotificationCompat.Builder(context,channel_id)
            .setSmallIcon(R.drawable.termtrackericon_bw)
                .setContentText(intent.getStringExtra("contentText"))
                .setContentTitle(intent.getStringExtra("contentTitle")).build();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId++, notification);
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID){
        CharSequence name = context.getResources().getString(R.string.channel_name);
        String description = context.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        // Register the channel with the system; you can't change the importance or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}