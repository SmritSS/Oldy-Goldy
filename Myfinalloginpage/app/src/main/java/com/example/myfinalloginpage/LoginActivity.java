package com.example.myfinalloginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    TextView btn;
    EditText inputEmail, inputPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn = findViewById(R.id.textViewSignUp);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredentials();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private void checkCredentials() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Email is not valid!");
        } else if (!isValidPassword(password)) {
            showError(inputPassword, "Password must be at least 8 characters long, contain a number, and a special character!");
        } else {
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            // Proceed with the login process (e.g., navigate to the next activity)
        }
    }

    private boolean isValidPassword(String password) {
        // Regular expression to check the password constraints
        String passwordPattern = "^(?=.*[0-9])(?=.*[!@#$%^&*()\\\\-_=+{};:,<.>]).{8,}$";
        return password.matches(passwordPattern);
    }

    private void showError(EditText input, String message) {
        input.setError(message);
        input.requestFocus();
    }
}
