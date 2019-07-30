package com.myapplicationdev.android.p12taskmanagerwear;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;

public class MyReceiver extends BroadcastReceiver {

    int reqCode = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new
                    NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);

        }

        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("id", intent.getIntExtra("id", 0));
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context,
                        reqCode, i, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Action action = new
                NotificationCompat.Action.Builder(
                        R.mipmap.ic_launcher,
                "Click to view",
                pendingIntent).build();

        RemoteInput ri = new RemoteInput.Builder("status")
                .setLabel("Status report")
                .setChoices(new String [] {"Completed"})
                .build();

        NotificationCompat.Action action2 = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Reply",
                pendingIntent)
                .addRemoteInput(ri)
                .build();

        NotificationCompat.WearableExtender extender = new
                NotificationCompat.WearableExtender();

        extender.addAction(action);
        extender.addAction(action2);

        // Build Notification
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(context,"default");
        builder.setContentTitle("New Task Added");
        builder.setContentText(intent.getStringExtra("name") + " - " + intent.getStringExtra("desc"));
        builder.setSmallIcon(android.R.drawable.btn_star_big_off);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.extend(extender);

        Notification n = builder.build();
        notificationManager.notify(123, n);
    }
}
