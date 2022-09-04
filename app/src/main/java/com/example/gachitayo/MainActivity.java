package com.example.gachitayo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tv_time, tv_start, tv_end;
    Button btn_time, btn_start, btn_end, btn_matching;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btn_matching = (Button) findViewById(R.id.btn_matching);
        btn_matching.setOnClickListener(matching_start);
    }
    View.OnClickListener matching_start = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i("leehj", "btn_click");
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setMessage("입력한 내용이 맞나요?");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    intent = new Intent(MainActivity.this, MatchingActivity.class);
                    startActivity(intent);
                }
            });
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, "조건을 다시 입력해주세요", Toast.LENGTH_LONG).show();
                }
            });
            dialog.create();
            dialog.show();
        }
    };
}