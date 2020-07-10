package com.chatapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail,edtPassword;
    TextView tvSignUp;
    Button btnSignIn;
    TextView showError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    edtEmail = findViewById(R.id.email_edt);
    edtPassword = findViewById(R.id.password_edt);
    tvSignUp = findViewById(R.id.signup_tv);
    btnSignIn = findViewById(R.id.login_btn);
    showError = findViewById(R.id.tvError);

    final webApiFunctions webApiFunctions = new webApiFunctions(this);

    btnSignIn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (edtPassword.getText().toString().length() == 0 && edtEmail.getText().toString().length() == 0)
            {
                edtEmail.setError("Email Required");
                edtPassword.setError("Password Required");

            }

            else if (edtEmail.getText().toString().length() == 0)
            {
                edtEmail.setError("Email Required");
            }
            else if (edtPassword.getText().toString().length() == 0)
            {
                edtPassword.setError("Password Required");
            }
            else {

                webApiFunctions.checkLogin(edtEmail.getText().toString(), edtPassword.getText().toString(), showError);

            }
            }
    });



    tvSignUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(i);
        }
    });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }
}
