package com.example.gachitayo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gachitayo.vo.MatchingVo;

public class MatchingActivity extends AppCompatActivity {
    ImageView gif;
    TextView time, start, end;
    Button recomm, cancel;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching);

        intent = getIntent();
        MatchingVo matchingVo = (MatchingVo) intent.getSerializableExtra("vo");
        Log.i("leehj", "matching vo get: "+matchingVo.getTime());
        time = (TextView)findViewById(R.id.tv_time_match);
        start = (TextView)findViewById(R.id.tv_start_match);
        end = (TextView)findViewById(R.id.tv_end_match);

        time.setText(" "+matchingVo.getTime());
        start.setText(" "+matchingVo.getStart());
        end.setText(" "+matchingVo.getPlace_name());

        gif=(ImageView) findViewById(R.id.gif_image);
        Glide.with(this).load(R.drawable.taxi_gif).into(gif);

//        recomm = (Button)findViewById(R.id.btn_recommend);
//        recomm.setOnClickListener(click_recomm);

        cancel = (Button) findViewById(R.id.btn_matchCancel);
        cancel.setOnClickListener(click_cancel);
    }
    //추천 리스트 버튼 클릭
//    View.OnClickListener click_recomm = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            intent = new Intent(MatchingActivity.this, RecommActivity.class);
//            startActivity(intent);
//        }
//    };


    //매칭 취소 버튼 클릭
    View.OnClickListener click_cancel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MatchingActivity.this);
            dialog.setMessage("매칭을 취소할까요?");
            dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //서버로 데이터 전송
                    Toast.makeText(MatchingActivity.this, "매칭을 취소합니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            dialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MatchingActivity.this, "매칭을 계속합니다.", Toast.LENGTH_LONG).show();
                }
            });
            dialog.create();
            dialog.show();
        }
    };
}