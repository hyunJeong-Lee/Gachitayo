package com.example.gachitayo.Retrofit_APIs;
import com.example.gachitayo.vo.UserDto;
import com.example.gachitayo.vo.UserVo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {

//    @POST("/login/signUp/idCheck")
//    Call<Integer> idCheck(@Body SignUpDto u_id);

//    @FormUrlEncoded
//    @Headers("Content-Type: application/json")
//    @POST("/login/signUp/nickCheck")
//    Call<Integer> nickCheck(@Body SignUpDto u_nick);
//
//    //SignUp 사용자 회원가입 정보 SignUpDto에 담아서 서버로 전송.
//    @POST("/login/signUp")
//    Call<Integer> signUp(@Body SignUpDto dto);

    //아이디 중복 확인
    @POST("/register/idCheck")
    Call<Integer> idCheck(@Body UserVo id);

    //회원가입
    @POST("/register")
    Call<String> signUp(@Body UserVo user);

    //로그인
    @POST("/login")
    Call<UserDto> signIn(@Body UserVo user);
}
