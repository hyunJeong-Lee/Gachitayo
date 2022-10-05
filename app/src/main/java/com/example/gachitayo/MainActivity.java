package com.example.gachitayo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.gachitayo.map.MapActivity;
import com.example.gachitayo.vo.MatchingVo;
import com.example.gachitayo.vo.UserDto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

public class MainActivity<fianl> extends AppCompatActivity {
    TextView tv_time, tv_start, tv_end;
    Button btn_time, btn_start, btn_end, btn_matching;
    Intent intent;
    MatchingVo matchingVo = new MatchingVo();

    static final int REQUEST_CODE = 1;

    SharedPreferences appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        appData = getSharedPreferences("appData", Activity.MODE_PRIVATE);
        matchingVo.setId(appData.getString("id", null));
        Log.i("leehj", "appdata: "+appData.getString("name", null));
        Log.i("leehj", "appdata: "+appData.getString("id", null));
        Log.i("leehj", "appdata: "+String.valueOf(appData.getInt("uuid", 0)));

        //시간 설정 버튼 클릭
        btn_time = (Button) findViewById(R.id.btn_time);
        btn_time.setOnClickListener(click_timeBtn);

        //출발지 설정 버튼 클릭
        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(click_startBtn);

        //도착지 설정 버튼 클릭
        btn_end = (Button)findViewById(R.id.btn_end);
        btn_end.setOnClickListener(click_endBtn);

        //매칭 시작 버튼 클릭
        btn_matching = (Button) findViewById(R.id.btn_matching);
        btn_matching.setOnClickListener(matching_start);
    }

    //Set Time Button click event
    View.OnClickListener click_timeBtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            TimePickerDialog dialog = new TimePickerDialog(MainActivity.this, listener, 8, 0, true);
//            dialog.setTitle("시간을 설정해주세요");
//            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            dialog.show();
//        }
//
//        private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int hour, int min) {
//                String time = hour+":"+min;
//                tv_time = (TextView) findViewById(R.id.tv_time);
//                tv_time.setText(" "+hour+"시 "+min+"분");
//            }

            matchingVo.setTime("11:25");
            tv_time = (TextView)findViewById(R.id.tv_time);
            tv_time.setText(" "+matchingVo.getTime());
        };

    };

    //Set StartPoint Button click event
    View.OnClickListener click_startBtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final CharSequence[] items = {"천안역", "호서대학교 아산캠", "호서대학교 천안캠"};

            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
            dialog.setTitle("출발지를 선택해주세요 :)").setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String start = (String) items[i];
                    tv_start = (TextView)findViewById(R.id.tv_start);
                    Toast.makeText(getApplicationContext(), start+"을 출발지로 설정됐습니다.", Toast.LENGTH_LONG).show();
                    matchingVo.setStart(start);
                    tv_start.setText(" "+matchingVo.getStart());
                }
            }).show();
        }
    };

    //Set EndPoint Button click event
    View.OnClickListener click_endBtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            intent = new Intent(MainActivity.this, MapActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    };

    //Start Matching Button click event
    View.OnClickListener matching_start = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i("leehj", "btn_click");
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setMessage("입력한 내용이 맞나요?");
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(matchingVo.getStart() != null && matchingVo.getTime() != null && matchingVo.getPlace_name() != null){
                        //서버로 데이터 전송
                        intent = new Intent(MainActivity.this, MatchingActivity.class);
                        intent.putExtra("vo", matchingVo);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "조건 설정을 완료해주세요", Toast.LENGTH_SHORT).show();
                    }


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

    //intent result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String place_name = data.getStringExtra("place_name");
                String road_address = data.getStringExtra("road_address");
                double x = data.getDoubleExtra("x", 0);
                double y = data.getDoubleExtra("y", 0);

                matchingVo.setPlace_name(place_name);
                matchingVo.setX(x);
                matchingVo.setY(y);

                tv_end = (TextView) findViewById(R.id.tv_end);
                tv_end.setText(" "+matchingVo.getPlace_name());
                TextView tv_address = findViewById(R.id.tv_roadAddress);
                tv_address.setText(" "+road_address);
            }
        }
    }
}