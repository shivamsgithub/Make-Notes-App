package com.shivam.androidtask.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shivam.androidtask.R;

public class LoginActivity extends AppCompatActivity {

    TextView tvForgetPassword, tvSignUp;
    TextInputEditText etEmail;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout lEmailTextInput = findViewById(R.id.til_email_sign_in);
        String lEmail = lEmailTextInput.getEditText().getText().toString();

        TextInputLayout lPasswordTextView = findViewById(R.id.til_password_sign_in);
        String lPassword = lPasswordTextView.getEditText().getText().toString();
//        baseClass.hideKeyboard();

        btnSignIn = findViewById(R.id.btn_sign_in);
        tvSignUp = findViewById(R.id.tv_sign_up);
        tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        btnSignIn.setOnClickListener(view -> {
            Intent intentTwo = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intentTwo);
        });

        String emailRegex = "^(.+)@(.+)$";


        if (!(lEmail.length() >= 7 && lEmail.length() <= 15)) {
            Toast.makeText(LoginActivity.this, "Enter valid Phone Number", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!(lEmail.matches(emailRegex))) {
                Toast.makeText(LoginActivity.this, "Enter valid Email Id", Toast.LENGTH_SHORT).show();
                return;
            }

        }

        if (!(lPassword.length() > 0)) {
            Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}