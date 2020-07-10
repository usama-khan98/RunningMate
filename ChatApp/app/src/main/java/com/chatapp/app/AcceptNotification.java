package com.chatapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AcceptNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_notification);
        webApiFunctions webApiFunctions = new webApiFunctions(this);
        Intent i = getIntent();
        String from_user = i.getStringExtra("user_by");
        String to_user = webApiFunctions.getLoggedInUser();





        String notificationID = i.getStringExtra("notify_id");
        webApiFunctions.notificationStatus(Integer.parseInt(notificationID),1);
        webApiFunctions.addMysqliNode(from_user,to_user);
    }



}
