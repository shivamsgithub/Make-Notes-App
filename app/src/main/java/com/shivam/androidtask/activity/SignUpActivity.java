package com.shivam.androidtask.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.shivam.androidtask.R;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp;
    TextView tvSignIn;
    EditText etName, etMobile, etEmail;
    TextInputLayout tilPassword, tilConfirmPassword ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tvSignIn = findViewById(R.id.tv_sign_in);
        btnSignUp = findViewById(R.id.btn_sign_up);
        etEmail = findViewById(R.id.et_email);
        etMobile = findViewById(R.id.et_mobile);
        tilPassword = findViewById(R.id.til_password);
        tilConfirmPassword = findViewById(R.id.til_confirm_password);

        tvSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnSignUp.setOnClickListener(view -> {
            Intent intentTwo = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intentTwo);
        });

    }
}