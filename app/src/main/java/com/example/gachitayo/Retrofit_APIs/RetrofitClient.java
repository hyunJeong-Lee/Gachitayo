package com.example.gachitayo.Retrofit_APIs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static RetrofitService getApiService(){
        return getInstance().create(RetrofitService.class);
    }

    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl("http://192.168.0.191:80")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

}