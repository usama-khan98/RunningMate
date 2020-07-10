package com.chatapp.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Map;

public class ChatScreen extends AppCompatActivity {

    ListView listView;
    String user="";
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> chats = new ArrayList<>();
    webApiFunctions webApiFunctions = new webApiFunctions(this);
    TextView chatsTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);


        listView = findViewById(R.id.lv);
        chatsTextView = findViewById(R.id.tvChats);

        getChats();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ChatScreen.this,Chat.class);
                i.putExtra("node_name",chats.get(position));
                i.putExtra("user_name",user);
                startActivity(i);

            }

        });

    }
    public void getChats(){
        user = webApiFunctions.getLoggedInUser();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, webApis.GET_CHATS_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("no")){
                            listView.setVisibility(View.GONE);
                            chatsTextView.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            listView.setVisibility(View.VISIBLE);
                            chatsTextView.setVisibility(View.GONE);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("nodes");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    chats.add(object.getString("node_name"));
                                }
                                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,chats){
                                    @NonNull
                                    @Override
                                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                        View v = super.getView(position,convertView,parent);
                                        TextView tv = v.findViewById(android.R.id.text1);
                                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
                                        tv.setPadding(100,0,0,0);
                                    return v;
                                    }
                                };

                                listView.setAdapter(arrayAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("userEmail",user);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
