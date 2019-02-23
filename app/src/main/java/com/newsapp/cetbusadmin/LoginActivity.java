package com.newsapp.cetbusadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText emailText,passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.emailTextLogin);
        passwordText = findViewById(R.id.passTextLogin);
    }

    public void doLogin(View view) {
        String admin_email = emailText.getText().toString();
        String admin_pass = passwordText.getText().toString();
        if(TextUtils.isEmpty(admin_email) || TextUtils.isEmpty(admin_pass)){
            Snackbar.make(view,"Fields cannot be left blank !!",Snackbar.LENGTH_LONG).show();
        }else{
            if(admin_email.equals("admin") && admin_pass.equals("admin001")){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }else{
                Snackbar.make(view,"Invalid Credentials",Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
