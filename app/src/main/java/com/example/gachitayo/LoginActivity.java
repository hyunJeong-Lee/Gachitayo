package com.example.gachitayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText id, pw;
    Button login;
    TextView sign;
    Intent intent;

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.login);
        //회원가입
        sign = (TextView) findViewById(R.id.login_tvSignUp);
        sign.setOnClickListener(click_signUp);
        //로그인
        login = (Button) findViewById(R.id.login_btnLogin);
        login.setOnClickListener(click_login);
    }

    View.OnClickListener click_signUp = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener click_login = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };
}