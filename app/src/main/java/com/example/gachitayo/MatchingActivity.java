package com.example.gachitayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MatchingActivity extends AppCompatActivity {
    ImageView gif;
    TextView time, start, end;
    Button recomm, cancel;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching);

        gif=(ImageView) findViewById(R.id.gif_image);
        Glide.with(this).load(R.drawable.gif_img).into(gif);

        recomm = (Button)findViewById(R.id.btn_recommend);
        recomm.setOnClickListener(click_recomm);

        cancel = (Button) findViewById(R.id.btn_matchCancel);
        cancel.setOnClickListener(click_cancel);
    }
    //추천 리스트 버튼 클릭
    View.OnClickListener click_recomm = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            intent = new Intent(MatchingActivity.this, RecommActivity.class);
            startActivity(intent);
        }
    };
    //매칭 취소 버튼 클릭
    View.OnClickListener click_cancel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}