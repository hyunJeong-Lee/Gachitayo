package com.example.gachitayo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gachitayo.Retrofit_APIs.RetrofitClient;
import com.example.gachitayo.vo.UserDto;
import com.example.gachitayo.vo.UserVo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText id, pw;
    Button login;
    TextView signup;
    Intent intent;

    //로그인 정보 저장
    private SharedPreferences appData;

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.login);
        //회원가입 클릭
        signup = (TextView) findViewById(R.id.login_tvSignUp);
        signup.setOnClickListener(click_signUp);
        //로그인 버튼 클릭
        login = (Button) findViewById(R.id.login_btnLogin);
        login.setOnClickListener(click_login);
    }

    View.OnClickListener click_signUp = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    };

    //로그인 버튼 클릭 이벤트 처리
    View.OnClickListener click_login = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            id = (EditText) findViewById(R.id.login_etId);
            pw = (EditText) findViewById(R.id.login_etPw);
            String str_id = id.getText().toString();
            String str_pw = pw.getText().toString();

            UserVo user = new UserVo(str_id, str_pw);
            Call<UserDto> func = RetrofitClient.getApiService().signIn(user);
            func.enqueue(new Callback<UserDto>() {
                @Override
                public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                    Log.i("leehj", "POST 성공");
                    UserDto respon = response.body();
                    if (response.isSuccessful()) {
                        if (respon.isSuccess() == true) {
                            Log.i("leehj", "로그인 성공");
                            Toast.makeText(LoginActivity.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            save(respon);
                        } else{
                            Log.i("leehj", "로그인 실패");
                            if(respon.getErrorId() == 404){
                                Toast.makeText(LoginActivity.this, "존재하지 않는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                Log.i("leehj", "아이디 없음.");
                            }else if(respon.getErrorPw() == 404){
                                Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                Log.i("leehj", "비밀번호 없음.");
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "다시 입력해주세요.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<UserDto> call, Throwable t) {
                    Log.i("leehj", "POST 실패__"+t.getMessage());
                }
            });
        }
    };

    private void save(UserDto user){
        appData = getSharedPreferences("appData", Activity.MODE_PRIVATE);
        //데이터를 저장하기 위해 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        //데이터 저장
        editor.putInt("uuid", user.getUuid());
        editor.putString("name", user.getName());
        editor.putString("id", user.getId());

        //변경 내용 적용
        editor.apply();
    }
}