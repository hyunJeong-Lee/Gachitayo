package com.example.gachitayo.Retrofit_APIs;
import com.example.gachitayo.vo.MatchingDto;
import com.example.gachitayo.vo.MatchingVo;
import com.example.gachitayo.vo.ResponseDto;
import com.example.gachitayo.vo.UserDto;
import com.example.gachitayo.vo.UserVo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
    @POST("/matching/register/idCheck")
    Call<Integer> idCheck(@Body Map<String, String> id);

    //회원가입
    @POST("/matching/register")
    Call<String> signUp(@Body UserVo user);

    //로그인
    @POST("/matching/login")
    Call<UserDto> signIn(@Body UserVo user);


    //매칭 시작
    @POST("/api/match/start-matching")
    Call<String> startMatching(@Body MatchingVo matchingVo);

    //매칭 결과
    @POST("/api/match/group")
    Call<List<ResponseDto>> matching_Result(@Body Map<String, String> id);

    //매칭 취소
    @POST("/api/match/cancel-matching")
    Call<String> cancelMatching(@Body Map<String, String> id);

    //매칭중 인원수
    @POST("/api/match/count-people")
    Call<Integer> matchingCount(@Body Map<String, String> id);
}
