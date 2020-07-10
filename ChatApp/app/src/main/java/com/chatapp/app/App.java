package com.chatapp.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL = "channelChats";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
    }

    public void createNotification(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
        {
            NotificationChannel ch1 = new NotificationChannel(
                    CHANNEL,
                    "DM Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            ch1.setDescription("Testing");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(ch1);

        }
    }

}
