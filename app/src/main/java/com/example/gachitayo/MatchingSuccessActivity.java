package com.example.gachitayo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gachitayo.vo.MatchingDto;
import com.example.gachitayo.vo.ResponseDto;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchingSuccessActivity extends AppCompatActivity {

    Intent intent;
    TextView tv_time, tv_start, tv_end, person1, person2, person3, person4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_success);

        intent = getIntent();
        ResponseDto zero_dto = (ResponseDto) intent.getSerializableExtra("result");
        ArrayList<String> names = intent.getStringArrayListExtra("names");

        Log.i("leehj", "MATCHING SUCCESS_ResponseDTO: "+zero_dto.toString());
        Log.i("leehj", "MATCHING SUCCESS_names ArrayList: "+names.toString());

        String time = zero_dto.getTime();
        String start = zero_dto.getStart();
        String end = zero_dto.getPlace_name();

        tv_time = (TextView) findViewById(R.id.tv_time_match);
        tv_time.setText(" "+time);

        tv_start = (TextView) findViewById(R.id.tv_start_match);
        tv_start.setText(" "+start);

        tv_end = (TextView) findViewById(R.id.tv_end_match);
        tv_end.setText(" "+end);
//
        person1 = (TextView) findViewById(R.id.tv_person1);
        person2 = (TextView) findViewById(R.id.tv_person2);
        person3 = (TextView) findViewById(R.id.tv_person3);
        person4 = (TextView) findViewById(R.id.tv_person4);
        int p_count = names.size();
        switch (p_count){
            case 3: person1.setText(names.get(0)); person2.setText(names.get(1)); person3.setText(names.get(2));break;
            case 4: person1.setText(names.get(0)); person2.setText(names.get(1)); person3.setText(names.get(2)); person4.setText(names.get(3)); break;
        }
//
//        //people_id 배열의 값을 보여주기. id? name 아닌가?
//        //출발지, 목적지, 시간도 통합적으로 보여주기
    }
}