package com.example.gachitayo;

import static android.util.Log.i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gachitayo.Retrofit_APIs.RetrofitClient;
import com.example.gachitayo.vo.MatchingDto;
import com.example.gachitayo.vo.MatchingVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchingActivity extends AppCompatActivity {
    ImageView gif;
    TextView time, start, end, count;
    Button recomm, cancel;
    Intent intent;
    Handler handler;
    Map<String, String> request_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching);

        intent = getIntent();
        MatchingVo matchingVo = (MatchingVo) intent.getSerializableExtra("vo");
        i("leehj", "matching vo get: " + matchingVo.getTime());
        time = (TextView) findViewById(R.id.tv_time_match);
        start = (TextView) findViewById(R.id.tv_start_match);
        end = (TextView) findViewById(R.id.tv_end_match);

        time.setText(" " + matchingVo.getTime());
        start.setText(" " + matchingVo.getStart());
        end.setText(" " + matchingVo.getPlace_name());

        request_id = new HashMap<>();
        request_id.put("userId", matchingVo.getId());

        gif = (ImageView) findViewById(R.id.gif_image);
        Glide.with(this).load(R.drawable.taxi_gif).into(gif);

//        recomm = (Button)findViewById(R.id.btn_recommend);
//        recomm.setOnClickListener(click_recomm);

        peoplecountTimer.start();
        matchingcountTimer.start();

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

    CountDownTimer peoplecountTimer = new CountDownTimer(1000 * 60 * 5, 1000) { //1000 * 60 * 5
        @Override
        public void onTick(long l) {
            //반복 실행 구문
            Call<Integer> func = RetrofitClient.getApiService().matchingCount(request_id);
            func.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    count = (TextView) findViewById(R.id.tv_matching_count);
                    count.setText(response.body() + "명이 모였습니다.");
                    Log.i("leehj", "count: " + response.body());
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.i("leehj", "매칭 count post 실패: " + t);
                }
            });
        }

        @Override
        public void onFinish() {
            //마지막 실행 구문

        }
    };

    CountDownTimer matchingcountTimer = new CountDownTimer(1000 * 60 * 5, 1000) { //1000 * 60 * 5
        @Override
        public void onTick(long l) {
            //반복 실행 구문
            Call<Map<String, String>> func = RetrofitClient.getApiService().matching_Result(request_id);
            func.enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    String result = response.body().get("result");
                    Log.i("leehj", "matching result : " + result);

                    //매칭에 성공하면
                    if (result.equals("Matching Success!")) {
                        peoplecountTimer.cancel();
                        matchingcountTimer.cancel();
                        Toast.makeText(MatchingActivity.this, "매칭에 성공했습니다!", Toast.LENGTH_SHORT).show();
                        String group = response.body().get("group");

                        try {
                            List<MatchingDto> mapping_response = objectMapping(group);
                            intent = new Intent(MatchingActivity.this, MatchingSuccessActivity.class);
                            intent.putExtra("group", (Serializable) mapping_response);
                            startActivity(intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Log.i("leehj", "matching result : " + t);
                }
            });
        }

        @Override
        public void onFinish() {
            //마지막 실행 구문. 매칭 실패 다이얼로그 출력
            timeover();
        }
    };

    private List<MatchingDto> objectMapping(String jsonStr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<MatchingDto> result = mapper.readValue(jsonStr, new TypeReference<List<MatchingDto>>(){});
        return result;
    }

    //countTimer가 종료 될 실행되는 함수. 매칭 실패. 다시 매칭할지 선택 dialog
    private void timeover() {
        Dialog dialog = new Dialog(MatchingActivity.this);
        dialog.setContentView(R.layout.custom_dialog); //dialog 연결
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title1 = (TextView) dialog.findViewById(R.id.customDialog_tv2); //title 설정
        title1.setVisibility(View.VISIBLE);
        title1.setText("같이 탈 친구를 찾지 못했습니다! 🥲");
        TextView title2 = (TextView) dialog.findViewById(R.id.customDialog_tv); //title 설정
        title2.setText("매칭을 다시 시도할까요?");

        Button ok = (Button) dialog.findViewById(R.id.customDialog_ok); //ok 버튼 클릭 이벤트 처리
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(intent);
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.customDialog_cancel); //cancel 버튼 클릭 이벤트 처리
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //서버로 데이터 전송
                Call<String> func = RetrofitClient.getApiService().cancelMatching(request_id);
                func.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        i("leehj", "매칭 취소 post 성공: " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        i("leehj", "매칭 취소 post 실패: " + t);
                    }
                });
                peoplecountTimer.cancel();
                matchingcountTimer.cancel();
                Toast.makeText(MatchingActivity.this, "매칭을 취소합니다.", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                finish();
            }
    });
        dialog.show();
}

    //매칭 취소 버튼 클릭
    View.OnClickListener click_cancel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Dialog dialog = new Dialog(MatchingActivity.this);
            dialog.setContentView(R.layout.custom_dialog); //dialog 연결
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView title = (TextView) dialog.findViewById(R.id.customDialog_tv); //title 설정
            title.setText("매칭을 취소할까요?");

            Button ok = (Button) dialog.findViewById(R.id.customDialog_ok); //ok 버튼 클릭 이벤트 처리
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //서버로 데이터 전송
                    Call<String> func = RetrofitClient.getApiService().cancelMatching(request_id);
                    func.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            i("leehj", "매칭 취소 post 성공: " + response.body().toString());
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            i("leehj", "매칭 취소 post 실패: " + t);
                        }
                    });
                    peoplecountTimer.cancel();
                    matchingcountTimer.cancel();
                    Toast.makeText(MatchingActivity.this, "매칭을 취소합니다.", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    finish();
                }
            });

            Button cancel = (Button) dialog.findViewById(R.id.customDialog_cancel); //cancel 버튼 클릭 이벤트 처리
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MatchingActivity.this, "매칭을 계속합니다.", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    };
}