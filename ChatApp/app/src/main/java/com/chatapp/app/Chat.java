package com.chatapp.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.collect.Iterators;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dagger.internal.MapFactory;

public class Chat extends AppCompatActivity {

    EditText msgBox;
    ImageButton btnMsg;
    TextView msgView;
    private DatabaseReference root;
    private String temp_key;
    String loggedInUser="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        msgBox = findViewById(R.id.edtMsg);
        btnMsg = findViewById(R.id.btnSend);
        msgView = findViewById(R.id.msgTV);

        webApiFunctions webApiFunctions = new webApiFunctions(getApplicationContext());
        loggedInUser = webApiFunctions.getLoggedInUser();
        Intent i = getIntent();

        final String userName = i.getStringExtra("user_name");
        final String nodeName = i.getStringExtra("node_name");

        setTitle(nodeName);

        root = FirebaseDatabase.getInstance().getReference().child(nodeName);



        btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map = new HashMap<String,Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String,Object>();
                map2.put("name",userName);
                map2.put("msg",msgBox.getText().toString());
                msgBox.setText("");
                message_root.updateChildren(map2);

            }
        });


        root.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                append_chat_conversation(dataSnapshot);
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    String chat_msg,chat_user;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void append_chat_conversation(DataSnapshot dataSnapshot) {
        Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator();

        while(i.hasNext()){

            chat_msg = (String)((DataSnapshot)i.next()).getValue();
            chat_user = (String)((DataSnapshot)i.next()).getValue();

            if (chat_user.equals(loggedInUser))
            {
                msgView.append("You : "+chat_msg+"\n");

            }
            else{
                msgView.append("Person : "+chat_msg+"\n");
            }

        }

    }
}
