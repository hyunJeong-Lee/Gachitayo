package com.example.gachitayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    TextView back;
    EditText name, id, pw, rePw;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        back = findViewById(R.id.signUp_back);
        back.setOnClickListener(click_back);
    }

    View.OnClickListener click_back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

}