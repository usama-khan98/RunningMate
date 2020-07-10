package com.chatapp.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    TextView showUser;
    Button addPost,showPost;
    ImageView userLogOut;
    TextView noPostFound;
    RecyclerView postFound;
    final webApiFunctions webApiFunctions = new webApiFunctions(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //webApiFunctions.checkNotification();

        String user = webApiFunctions.getLoggedInUser();
        showUser = findViewById(R.id.dashboard_userName);
        userLogOut = findViewById(R.id.imageView_logout);
        noPostFound = findViewById(R.id.tvNoPost);
        postFound = findViewById(R.id.rvPost);
        postFound.setLayoutManager(new LinearLayoutManager(this));
        webApiFunctions.fetchUserPost(noPostFound,postFound);

        userLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webApiFunctions.removeSharedPreferences();
            }
        });

    showUser.setText(user);

    addPost = findViewById(R.id.add_newPost);
    showPost = findViewById(R.id.show_chat);

    addPost.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent i = new Intent(DashboardActivity.this,AddPostActivity.class);
        startActivity(i);
        }
    });

    showPost.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i =new Intent(DashboardActivity.this,ChatScreen.class);
            startActivity(i);
        }
    });
    }


    @Override
    protected void onStart() {
        super.onStart();
        webApiFunctions.checkNotification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webApiFunctions.checkNotification();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }
}
