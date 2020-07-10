package com.chatapp.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chatapp.app.App.CHANNEL;

public class webApiFunctions {
    Context context;
    RecyclerView.Adapter adapter;
    List<postModel> list;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    NotificationManagerCompat notificationManager;
    ArrayList<Integer> notify_ids;
    ArrayList<Integer> notify_count;
    String node_name = "";


    public webApiFunctions(Context context) {
        this.context = context;
    }

    public void registerUser(final String userName, final String userEmail, final String userPassword, final String userAge,
                             final String userGender, final Bitmap userImage, final String userDeviceToken, final EditText emailField)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Creating Your Account");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, webApis.REGISTER_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        if (response.trim().contains("exists"))
                        {
                        showMessage(emailField);
                        }
                        else if (response.trim().equals("done"))
                        {
                            Intent i = new Intent(context,LoginActivity.class);
                            context.startActivity(i);
                        }
                        else {
                            Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                String imageData = imageToString(userImage);

                map.put("userName",userName);
                map.put("useEmail",userEmail);
                map.put("usePassword",userPassword);
                map.put("userAge",userAge);
                map.put("userGender",userGender);
                map.put("userImage",imageData);
                map.put("userDeviceToken",userDeviceToken);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void checkLogin(final String userEmail, final String userPassword, final TextView errorText){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Logging In");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, webApis.LOGIN_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                if (response.trim().equals("ok"))
                {
                    generateSharedPreferences(userEmail);
                    Intent i = new Intent(context,DashboardActivity.class);
                    i.putExtra("user",userEmail);
                    context.startActivity(i);
                }
                else
                {
        errorText.setText("* Invalid Email Address Or Password.");
                }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
                Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("userEmail",userEmail);
                map.put("userPassword",userPassword);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void addPost(final String postTitle, final String postLocation, final String postGender, final String postAge)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Posting");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, webApis.ADD_POST_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                if (response.trim().equals("ok"))
                {
                   String completeAge = postAge;
                   String sepratedAge[] = completeAge.split("-");
                   String startAge = sepratedAge[0];
                   String endAge = sepratedAge[1];
                    addNotification(startAge,endAge,postGender);
                }
                else
                {
                    Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                String user = getLoggedInUser();
                map.put("postBy",user);
                map.put("postTitle",postTitle);
                map.put("postLocation",postLocation);
                map.put("postGender",postGender);
                map.put("postAge",postAge);
                return map;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void fetchUserPost(final TextView noPost, final RecyclerView posts){
        list = new ArrayList<>();
        list.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, webApis.FETCH_POST_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("no"))
                        {
                            noPost.setVisibility(View.VISIBLE);
                            posts.setVisibility(View.GONE);
                        }
                        else{
                            noPost.setVisibility(View.GONE);
                            posts.setVisibility(View.VISIBLE);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("posts");
                                for(int i = 0;i<jsonArray.length();i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    postModel postModel = new postModel(
                                            object.getString("postTitle"),
                                            object.getString("postLocation"),
                                            object.getString("postGender"),
                                            object.getString("postAge"),
                                            object.getString("postTime")
                                    );
                                    list.add(postModel);
                    adapter = new postAdapter(context,list);
                    posts.setAdapter(adapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                String user = getLoggedInUser();
                map.put("userEmail",user);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    public void addNotification(final String startAge, final String endAge, final String genderPref){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                webApis.SEND_NOTIFICATION_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("done"))
                        {
                            Intent i = new Intent(context,DashboardActivity.class);
                            context.startActivity(i);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String name = getLoggedInUser();
                Map<String,String> map = new HashMap<>();
                map.put("postBy",name);
                map.put("startAge",startAge);
                map.put("endAge",endAge);
                map.put("genderPref",genderPref);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void checkNotification(){
        notify_ids = new ArrayList<>();
        notify_count = new ArrayList<>();
        notify_ids.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                webApis.FETCH_NOTIFICATION_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("ItemCount" ,response );
                        if (response.trim().equals("no"))
                        {

                            context.stopService(new Intent(context,NotificationService.class));

                        }
                        else  {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("notifications");
                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    notify_ids.add(Integer.parseInt(jsonObject1.getString("notify_id")));
                                    getNotificationSendBy();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                String name = getLoggedInUser();
                map.put("getUserEmail",name);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    public void getNotificationSendBy(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, webApis.FETCH_NOTIFICATION_BY_API
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("no"))
                {

                }
                else
                {
                    try {
                        Object[] ids;
                                JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("notificationBy");
                        for (int i=0;i<notify_ids.size();i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.e("ID",String.valueOf(notify_ids.get(i)));
                            Log.e("ID Size",String.valueOf(notify_ids.size()));
                            ids = notify_ids.toArray();
                            showNotification(object.getString("notify_by"), (Integer) ids[i]);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                String name = getLoggedInUser();
                map.put("getUserEmail",name);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }



    public void showNotification(String user,int numbers){

        notificationManager = NotificationManagerCompat.from(context);

        Intent i = new Intent(context,AcceptNotification.class);
        i.putExtra("user_by",user);
        i.putExtra("notify_id",String.valueOf(numbers));
        Intent delNotify = new Intent(context,DeleteNotification.class);
        delNotify.putExtra("notify_id",String.valueOf(numbers));
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                numbers,i,0);
        PendingIntent closeNotification = PendingIntent.getActivity(context,numbers,delNotify,0);

        Notification notification = new NotificationCompat.Builder(context,CHANNEL)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("New Message Request")
                .setContentText(user+" is requesting for chat")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVibrate(new long[] { 1000, 1000})
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher_round,"Deny",closeNotification)
                .addAction(R.mipmap.ic_launcher_round,"Accept",pendingIntent)
                .setAutoCancel(true)
                .build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notificationManager.notify(numbers,notification);
    }




    public void clearNotification(int notifyID)
    {
        notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(notifyID);
    }


    public void addMysqliNode(final String from, final String to)
    {

        int from_position = from.indexOf("@");
        int to_position = to.indexOf("@");

        String user_from = from.substring(0,from_position);
        String user_to = to.substring(0,to_position);
        node_name = user_from+"_"+user_to;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, webApis.ADD_NODE_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("")) {

                        }
                        else
                        {
                            addNodeToFirebase(node_name);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("getBy",from);
                map.put("getTo",to);
                map.put("nodeName",node_name);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void notificationStatus(final int notify_id, final int notifyStatus)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, webApis.NOTIFICATION_STATUS_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("ok"))
                        {
                            clearNotification(notify_id);
                            Intent i = new Intent(context,DashboardActivity.class);
                            context.startActivity(i);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("notify_id",String.valueOf(notify_id));
                map.put("notify_status",String.valueOf(notifyStatus));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void generateSharedPreferences(String userEmail){

        SharedPreferences sharedPreferences = context.getSharedPreferences("userAuth",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userEmail",userEmail);
        editor.apply();

    }

    public void addNodeToFirebase(final String nodeName){

        final Map<String,Object> map = new HashMap<String, Object>();
        map.put(nodeName,"");

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(nodeName))
                {

                }
                else{
                    root.updateChildren(map);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void validateSharedPreferences(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userAuth",Context.MODE_PRIVATE);
        String checkEmail = sharedPreferences.getString("userEmail","");
        if (checkEmail.equals(""))
        {
            Intent intent = new Intent(context,LoginActivity.class);
            context.startActivity(intent);
        }
        else
        {

            Intent i = new Intent(context,DashboardActivity.class);
            context.startActivity(i);

        }
    }

    public String getLoggedInUser(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userAuth",Context.MODE_PRIVATE);
        String checkEmail = sharedPreferences.getString("userEmail","");
        return checkEmail;
    }

    public void removeSharedPreferences(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("userAuth",Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("userEmail").commit();
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);

    }


    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodeImage;


    }
    public void showMessage(EditText emailField){
        emailField.setError("Email Already Exist");
    }
}
