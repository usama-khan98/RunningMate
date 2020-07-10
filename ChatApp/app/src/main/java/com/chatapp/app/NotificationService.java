package com.chatapp.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chatapp.app.App.CHANNEL;

public class NotificationService extends Service {

    webApiFunctions webApiFunctions ;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        webApiFunctions = new webApiFunctions(getApplicationContext());
        super.onCreate();
     //   getNotificationSendBy();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

   //     getNotificationSendBy();

        onTaskRemoved(intent);
        return START_STICKY;

    }



    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restart = new Intent(getApplicationContext(),this.getClass());
        restart.setPackage(getPackageName());
        startService(restart);

        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(getApplicationContext(),DashboardActivity.class);
        stopSelf();
        startActivity(i);
    }


}
