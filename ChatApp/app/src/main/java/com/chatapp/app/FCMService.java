package com.chatapp.app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FCMService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    String type ="";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size()>0)
        {
            type = "json";
            sendNotification(remoteMessage.getData().toString());
        }
        if (remoteMessage.getNotification() !=null){
            type = "message";
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String messageBody) {
        String id="",message="",title="",user ="";
        if (type.equals("json")){
            try {
                JSONObject jsonObject = new JSONObject(messageBody);
                id = jsonObject.getString("id");
                message = jsonObject.getString("message");
                title = jsonObject.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("message")){
            message = messageBody;
        }

        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(getString(R.string.app_name));
        notificationBuilder.setContentText(message);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(soundUri);
        notificationBuilder.setSmallIcon(R.drawable.fcm_notification_vector);
        notificationBuilder.setAutoCancel(false);
        Vibrator v =(Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        v.vibrate(1000);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        webApiFunctions webApiFunctions = new webApiFunctions(this);
        user = webApiFunctions.getLoggedInUser();
        notificationManager.notify(0,notificationBuilder.build());


    }
}


