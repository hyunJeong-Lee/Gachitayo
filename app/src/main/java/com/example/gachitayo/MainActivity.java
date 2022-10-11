package com.example.gachitayo;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.gachitayo.Retrofit_APIs.RetrofitClient;
import com.example.gachitayo.map.MapActivity;
import com.example.gachitayo.vo.MatchingVo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity<fianl> extends AppCompatActivity {
    TextView tv_time, tv_start, tv_end;
    Button btn_time, btn_start, btn_end, btn_matching;
    Intent intent;
    Dialog custom_dialog;
    MatchingVo matchingVo = new MatchingVo();

    static final int REQUEST_CODE = 1;

    SharedPreferences appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ver3);

        appData = getSharedPreferences("appData", Activity.MODE_PRIVATE);

        //로그인 성공 시 공유 프레퍼런스에 저장한 uuid, id를 matching vo에 set
        matchingVo.setId(appData.getString("id", null));
        matchingVo.setUuid(appData.getInt("uuid", 0));

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
            custom_dialog = new Dialog(MainActivity.this);
            custom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            custom_dialog.setContentView(R.layout.custom_dialog_timepicker);
            show_dialog();
        };

        private void show_dialog() {
            custom_dialog.show();
            custom_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            String hour, minute;
            TimePicker mytime = (TimePicker) custom_dialog.findViewById(R.id.timepicker);
            mytime.setIs24HourView(false);
            setTimePickerInterval(mytime);
            //SDK API23 기준으로 시간을 가져오는 메소드가 다름
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hour = mytime.getHour() + "";
            } else {
                hour = mytime.getCurrentHour() + "";
            }

            Button btn = custom_dialog.findViewById(R.id.timepicker_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    String h = mytime.getHour() + "";
                    Log.i("leehj", "hour: "+h);
                    int m = mytime.getMinute();
                    String min="00";
                    if (m == 1)
                        min = "30";
                    else if(m == 0){
                        min="00";
                    }

                    String time = h + ":" + min;
                    matchingVo.setTime(time);
                    tv_time = (TextView)findViewById(R.id.tv_time);
                    tv_time.setText(" "+matchingVo.getTime());
                    custom_dialog.dismiss();
                }
            });
        }
    };

    //timepicker 분단위 시간 간격 설정 함수
    private void setTimePickerInterval(TimePicker timePicker){
        try {
            int TIME_PICKER_INTERVAL = 30;
            NumberPicker minutePicker = (NumberPicker) timePicker.findViewById(Resources.getSystem().getIdentifier(
                    "minute", "id", "android"));
            minutePicker.setMaxValue((60 / TIME_PICKER_INTERVAL)-1);
            minutePicker.setMinValue(0);
            List<String> displayedValues = new ArrayList<>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%d", i));
            }
            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));
            minutePicker.setOnValueChangedListener(null);

        }catch (Exception e){

        }
    }

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
            Log.i("leehj", "mtching start btn_click");
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.custom_dialog); //dialog 연결
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView title = (TextView) dialog.findViewById(R.id.customDialog_tv); //title 설정
            title.setText("입력한 내용이 맞나요?");

            Button ok = (Button)dialog.findViewById(R.id.customDialog_ok); //ok 버튼 클릭 이벤트 처리
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(matchingVo.getStart() != null && matchingVo.getTime() != null && matchingVo.getPlace_name() != null){
                        //서버로 데이터 전송. matching vo 전송
                        Call<String> func = RetrofitClient.getApiService().startMatching(matchingVo);
                        func.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Log.i("leehj", "Matching vo 전송 post 성공");
                                Log.i("leehj", "response : "+response.body().toString());
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.i("leehj", "matching vo post 전송 실패: "+t);
                            }
                        });

                        Toast.makeText(MainActivity.this, "매칭을 시작합니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        intent = new Intent(MainActivity.this, MatchingActivity.class);
                        intent.putExtra("vo", matchingVo);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "조건 설정을 완료해주세요", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            Button cancel = (Button) dialog.findViewById(R.id.customDialog_cancel); //cancel 버튼 클릭 이벤트 처리
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "매칭을 시작하지 않습니다.", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });

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