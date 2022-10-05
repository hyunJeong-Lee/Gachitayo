package com.example.gachitayo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gachitayo.Retrofit_APIs.RetrofitClient;
import com.example.gachitayo.function.SignUp_Function;
import com.example.gachitayo.vo.UserVo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    TextView back;
    EditText name, id, pw, re_pw;
    TextView tv_id, tv_pw, tv_name;
    Button signUp, idCheck;

    SignUp_Function sign;

    static int code_click_id = 0;
    static int code_id = 2;
    static int code_pw = 0;
    static int code_name = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        //이전 화면인 로그인 화면으로 back.
        back = findViewById(R.id.signUp_back);
        back.setOnClickListener(click_back);

        //id 선언
        name = (EditText) findViewById(R.id.signUp_etName);
        id = (EditText) findViewById(R.id.signUp_etId);
        pw = (EditText) findViewById(R.id.signUp_etPw);
        re_pw = (EditText) findViewById(R.id.signUp_etPwRe);

        tv_id = (TextView) findViewById(R.id.signUp_tvid);
        tv_pw = (TextView) findViewById(R.id.signUp_tvPw);
        tv_name = (TextView) findViewById(R.id.signUp_tvname);

        signUp = (Button) findViewById(R.id.signUp_btnSignUp);
        idCheck = (Button) findViewById(R.id.SignUp_btnIdCheck);

        //SignFuction 생성
        sign = new SignUp_Function(back, name, id, pw, re_pw, tv_id, tv_pw, tv_name);

        //회원가입 구현=================================================================

        //입력 글자수 제한
        sign.MaxLength();

        //id 중복 확인
        idCheck.setOnClickListener(click_checkid);

        //회원가입 버튼 클릭
        signUp.setOnClickListener(click_signUp);
    }

    View.OnClickListener click_checkid = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            code_click_id=1;
            CheckId();
        }
    };

    View.OnClickListener click_back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    View.OnClickListener click_signUp = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            code_pw = sign.check_pw(); //비밀번호 유효성 확인
            code_name = sign.check_nick(); //닉네임 유효성 확인

            if(code_pw == 1 && code_name ==1 && code_id==1 && code_click_id==1){ //재확인 비밀번호 일치 시, 회원정보 서버로 전송. LoginActivity로 이동.
                    finish();
                    //회원가입 서버로 데이터 전송. 토스트 메시지 출력.
                    sign.signUp();
                    Toast.makeText(SignUpActivity.this, "회원가입 성공!!", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(SignUpActivity.this, "입력 내용을 다시 확인해주세요", Toast.LENGTH_SHORT).show();
            }
        }
    };

    //아이디 중복 확인
    public void check_id(String id) {
        UserVo uservo = new UserVo(id);

        Call<Integer> func = RetrofitClient.getApiService().idCheck(uservo);
        func.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int res = response.body();
                code_id = res;
                if (code_id == 1) {
                    Log.i("leehj", "id 중복 아님__code: " + code_id);
                } else {
                    Log.i("leehj", "id 중복__code: " + code_id);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i("leehj", "id 중복 post error__" + t.getMessage());
            }
        });
    }

    //아이디 유효성 검사
    public void CheckId(){
        String str_id = id.getText().toString();
        if(str_id.equals("")){ // id 공백
            tv_id.setText("아이디를 입력하세요");
        }else{
            if(str_id.length()<4){ //id 4자리 이상
                tv_id.setText("아이디는 4자 이상 입력해야 합니다.");
            }else{
                check_id(str_id);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(code_id == 1){ //유효성 검사 통과.
                            tv_id.setText("사용 가능한 닉네임 입니다.");
                        } else { //유효성 검사 불통과
                            tv_id.setText("이미 사용중인 닉네임 입니다.");
                        }
                    }
                }, 500); //0.5초 딜레이 준 후 실행
            }
        }
    }
}