package com.chatapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import afu.org.checkerframework.checker.nullness.qual.NonNull;

public class AddPostActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteAdapter adapter;
    PlacesClient placesClient;
    Spinner agePrefrences;
    String location;
    EditText edtPost;
    String age;
    RadioButton rdm,rdf;
    Button btnAdd;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        String apiKey = getString(R.string.api_key);
        if(apiKey.isEmpty()){
         //   responseView.setText(getString(R.string.error));
            return;
        }
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        placesClient = Places.createClient(this);
        initAutoCompleteTextView();
        final webApiFunctions webApiFunctions = new webApiFunctions(this);
        edtPost = findViewById(R.id.post_title);
        rdm = findViewById(R.id.rdMale);
        rdf = findViewById(R.id.rdFemale);
        btnAdd = findViewById(R.id.addPost_btn);

        agePrefrences = findViewById(R.id.agePref);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdm.isChecked())
                {
                    gender ="Male";
                    Log.e("Gender", gender);
                }
                else if (rdf.isChecked()){
                    gender = "Female";
                    Log.e("Gender", gender);
                }
                webApiFunctions.addPost(edtPost.getText().toString(),autoCompleteTextView.getText().toString(),gender,age);
            }
        });

        final ArrayList<String> list = new ArrayList<>();
        list.add("Select Age");
        list.add("18-23");
        list.add("24-29");
        list.add("30-35");
        list.add("36-41");
        list.add("42-47");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,list);

        agePrefrences.setAdapter(stringArrayAdapter);


        agePrefrences.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    private void initAutoCompleteTextView() {

        autoCompleteTextView = findViewById(R.id.auto);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener(autocompleteClickListener);
        adapter = new AutoCompleteAdapter(this, placesClient);
        autoCompleteTextView.setAdapter(adapter);
    }
    private AdapterView.OnItemClickListener autocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            try {
                final AutocompletePrediction item = adapter.getItem(i);
                String placeID = null;
                if (item != null) {
                    placeID = item.getPlaceId();
                }

//                To specify which data types to return, pass an array of Place.Fields in your FetchPlaceRequest
//                Use only those fields which are required.

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);

                FetchPlaceRequest request = null;
                if (placeID != null) {
                    request = FetchPlaceRequest.builder(placeID, placeFields)
                            .build();
                }

                if (request != null) {
                    placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(FetchPlaceResponse task) {
//                            responseView.setText(task.getPlace().getName() + "\n" + task.getPlace().getAddress());
                        //    Toast.makeText(getApplicationContext(),task.getPlace().getName(),Toast.LENGTH_LONG).show();
                            location = task.getPlace().getName() ;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
      //                      responseView.setText(e.getMessage());
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
}
