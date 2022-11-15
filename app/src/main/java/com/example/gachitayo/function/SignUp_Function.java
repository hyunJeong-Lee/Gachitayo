package com.example.gachitayo.function;

import android.text.InputFilter;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gachitayo.Retrofit_APIs.RetrofitClient;
import com.example.gachitayo.vo.UserVo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp_Function {

    TextView back;
    EditText name, id, pw, re_pw;
    TextView tv_name, tv_id, tv_pw;

    public SignUp_Function(TextView back, EditText name, EditText id, EditText pw, EditText re_pw, TextView tv_id, TextView tv_pw, TextView tv_name) {
        this.back = back;
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.re_pw = re_pw;
        this.tv_id = tv_id;
        this.tv_pw = tv_pw;
        this.tv_name = tv_name;
    }

    //글자수 제한
    public void MaxLength(){
        InputFilter[] filters1 = new InputFilter[]{
                new InputFilter.LengthFilter(20),
        };
        InputFilter[] filters2 = new InputFilter[]{
                new InputFilter.LengthFilter(10),
        };
        name.setFilters(filters1);
        id.setFilters(filters2);
        pw.setFilters(filters2);
        re_pw.setFilters(filters2);
    }

    //비밀번호 재확인
    public int check_pw() {
        int code_pw=0;
        String pass = pw.getText().toString();
        String pass_re = re_pw.getText().toString();
        if(pass.equals("")){
            tv_pw.setText("비밀번호를 입력해주세요");
        } else {
            if (pass.equals(pass_re)) {
                code_pw = 1;
                tv_pw.setText("비밀번호가 일치합니다.");
                Log.i("leehj", "비밀번호 일치 check code: " + code_pw);
            } else {
                code_pw = 0;
                tv_pw.setText("비밀번호가 일치하지 않습니다.");
                Log.i("leehj", "비밀번호 불일치 check code: " + code_pw);
            }
        }
        return code_pw;
    }

    //닉네임 유효성 확인
    public int check_nick(){
        int code_name = 0;
        String str_nick = name.getText().toString();
        if(str_nick.equals("")){
            tv_name.setText("이름을 입력해주세요");
        } else {
            tv_name.setText("멋진 이름이네요!");
            code_name = 1;
        }
        return code_name;
    }

    //회원가입 정보 서버로 전달
    public void signUp(){
        String str_name = name.getText().toString();
        String str_id = id.getText().toString();
        String str_pw = pw.getText().toString();
        UserVo user = new UserVo(-1, str_id, str_pw, str_name);
        Call<String> func = RetrofitClient.getApiService().signUp(user);
        func.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("leehj", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("leehj", t.getMessage());
            }
        });
    }
}
