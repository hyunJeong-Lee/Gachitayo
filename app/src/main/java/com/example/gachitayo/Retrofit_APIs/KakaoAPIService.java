package com.example.gachitayo.Retrofit_APIs;

import com.example.gachitayo.vo.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoAPIService {

    //키워드로 장소 검색하기
    @GET("/v2/local/search/keyword.json") //keyword.json의 정보를 받아와요
    Call<SearchResult> getSearchKeword(
            @Header("Authorization") String key,//카카오 api 인증키
            @Query("query") String query); //검색을 원하는 질의어
//            @Body ResultSearchKeyword resultSearch); //받아온 정보를 ResultSea..클래스 구조에 담감

}
