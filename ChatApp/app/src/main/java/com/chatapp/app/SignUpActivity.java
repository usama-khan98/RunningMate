package com.chatapp.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.InputStream;

public class SignUpActivity extends AppCompatActivity {

    TextView signIn;
    ImageButton imageButton;
    private static final int IMAGE_REQUEST_CODE = 3;
    private Bitmap bitmap;
    Button btnSignUp;
    EditText edtSignUpEmail,edtSignUpName,edtSignUpPassword,edtSignUpAge;
    RadioButton rdm,rdf;
    String gender="";
    TextView imageError,genderError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseApp.initializeApp(getApplicationContext());


        final webApiFunctions webApiFunctions = new webApiFunctions(this);

        signIn = findViewById(R.id.signin_tv);

        imageButton = findViewById(R.id.pickImage);
        btnSignUp = findViewById(R.id.signUp_Button);
        edtSignUpName = findViewById(R.id.signUp_Name);
        edtSignUpEmail = findViewById(R.id.signUp_Email);
        edtSignUpPassword = findViewById(R.id.signUp_Password);
        edtSignUpAge = findViewById(R.id.signUp_Age);
        rdm = findViewById(R.id.rdM);
        rdf = findViewById(R.id.rdF);
        imageError= findViewById(R.id.imgError);
        genderError = findViewById(R.id.gndrError);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bit = String.valueOf(bitmap);
                if (edtSignUpName.getText().toString().length()<1){
                    edtSignUpName.setError("Name Required");
                }
                else if (bit.equals("null"))
                {
                    imageError.setText("* Image Required");
                    imageError.setVisibility(View.VISIBLE);
                }
                else if (!bit.equals("null"))
                {
                    imageError.setVisibility(View.GONE);
                }
                 if (edtSignUpEmail.getText().toString().length()<1){
                    edtSignUpEmail.setError("Email Required");
                }
                 if (edtSignUpPassword.getText().toString().length()<1){
                     edtSignUpPassword.setError("Password Required");
                 }
                 if (edtSignUpAge.getText().toString().length()<1){
                    edtSignUpAge.setError("Age Required");
                }
                else if (edtSignUpAge.getText().toString().length()>0)
                {
                    int a = Integer.parseInt(edtSignUpAge.getText().toString());
                        if (a <=17)
                        {
                            edtSignUpAge.setError("Age Must Be Greater Than 17");
                        }
                }
                if (rdm.isChecked())
                {
                    gender ="Male";
                    Log.e("Gender", gender);
                    genderError.setVisibility(View.GONE);
                }
                else if (rdf.isChecked()){
                    gender = "Female";
                    Log.e("Gender", gender);
                    genderError.setVisibility(View.GONE);

                }
                else if (gender.equals(""))
                {
                    genderError.setText("* Gender Required");
                    genderError.setVisibility(View.VISIBLE);
                }
                    String token = FirebaseInstanceId.getInstance().getToken();
                 //   Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();
                    webApiFunctions.registerUser(edtSignUpName.getText().toString(),edtSignUpEmail.getText().toString(),
                            edtSignUpPassword.getText().toString(),edtSignUpAge.getText().toString(),gender,bitmap,token,
                            edtSignUpEmail
                            );





            }
        });


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

       imageButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent();
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);

           }
       });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);

                imageButton.setImageBitmap(bitmap);

            }
            catch (Exception e)
            {

            }

        }
    }


}
