package com.example.gachitayo;

import static android.util.Log.e;
import static android.util.Log.i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gachitayo.Retrofit_APIs.RetrofitClient;
import com.example.gachitayo.vo.MatchingDto;
import com.example.gachitayo.vo.ResponseDto;
import com.example.gachitayo.vo.MatchingVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
//        matchingcountTimer.start();

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

    CountDownTimer peoplecountTimer = new CountDownTimer(1000 * 60 * 3, 1000) { //1000 * 60 * 5
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


                    //3명이 모이면
                    if (response.body() == 3) {
                        peoplecountTimer.cancel(); //타이머 종료
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Call<List<ResponseDto>> func = RetrofitClient.getApiService().matching_Result(request_id);
                                func.enqueue(new Callback<List<ResponseDto>>() {
                                    @Override
                                    public void onResponse(Call<List<ResponseDto>> call, Response<List<ResponseDto>> response) {
                                        List<ResponseDto> result_list = response.body();
                                        ResponseDto result_dto = result_list.get(0);

                                        Log.e("leehj", "0번째 result dto :" + result_dto.toString());

                                        ArrayList<String> name_array = new ArrayList<>();
                                        for (int i = 0; i < result_list.size(); i++) {
                                            name_array.add(result_list.get(i).getName());
                                        }

                                        intent = new Intent(MatchingActivity.this, MatchingSuccessActivity.class);
                                        intent.putExtra("result", result_dto); //출발지, 목적지, 시간을 입력하기 위함
                                        intent.putExtra("names", name_array); //매칭 사용자 이름으르 배열로 넘겨주기
                                        Toast.makeText(MatchingActivity.this, "매칭에 성공했습니다!", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<List<ResponseDto>> call, Throwable t) {

                                    }
                                });

                            }
                        }, 1000 * 10); //10초 딜레이 준 후 실행

                    }


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
            timeover();
        }
    };

    //매칭 설공 시 매칭 정보 요청
    public void matchingSuccessRequest(Map<String, String> request_id) {

    }


//    CountDownTimer matchingcountTimer = new CountDownTimer(1000 * 60 * 3, 1000) { //1000 * 60 * 5
//        @Override
//        public void onTick(long l) {
//            MatchingDto matchingDto = new MatchingDto();
//            //반복 실행 구문
//            Call<List<ResponseDto>> func = RetrofitClient.getApiService().matching_Result(request_id);
//            func.enqueue(new Callback<List<ResponseDto>>() {
//                @Override
//                public void onResponse(Call<List<ResponseDto>> call, Response<List<ResponseDto>> response) {
//                    boolean result =response.body().get(0).getSucess();
//                    Log.i("leehj", "matching result (getSuccess) : " + result);
//
//                    //매칭에 성공하면
//                    if (result) {
//                        Toast.makeText(MatchingActivity.this, "매칭에 성공했습니다!", Toast.LENGTH_SHORT).show();
//                        peoplecountTimer.cancel();
//                        matchingcountTimer.cancel();
//
//                        ArrayList<String> name_array = new ArrayList<>();
//                        List<ResponseDto> dto_list = response.body();
//                        for(int i=0; i<dto_list.size();i++){
//                            name_array.add(dto_list.get(i).getName());
//                        }
//
//                        intent = new Intent(MatchingActivity.this, MatchingSuccessActivity.class);
//                        intent.putExtra("result", response.body().get(0)); //출발지, 목적지, 시간을 입력하기 위함
//                        intent.putExtra("names", name_array); //매칭 사용자 이름으르 배열로 넘겨주기
//                        startActivity(intent);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<ResponseDto>> call, Throwable t) {
//                    Log.i("leehj", "matching result : " + t);
//                }
//            });
//        }
//
//        @Override
//        public void onFinish() {
//            //마지막 실행 구문. 매칭 실패 다이얼로그 출력
//            timeover();
//        }
//    };

    private List<ResponseDto> objectMapping(String jsonStr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ResponseDto> result = mapper.readValue(jsonStr, new TypeReference<List<ResponseDto>>() {
        });
        Log.i("leehj", "-------------------mapping result : " + result.toString());
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
//                matchingcountTimer.cancel();
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
//                    matchingcountTimer.cancel();
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