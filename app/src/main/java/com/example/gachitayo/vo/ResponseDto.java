package com.example.gachitayo.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseDto implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("start")
    @Expose
    private String start;

    @SerializedName("placeName")
    @Expose
    private String place_name;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("sucess")
    @Expose
    private boolean sucess;

    public String getName() {
        return name;
    }

    public String getStart() {
        return start;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getTime() {
        return time;
    }

    public boolean getSucess() {
        return sucess;
    }

    @Override
    public String toString() {
        return "MatchingDto{" +
                "name='" + name + '\'' +
                ", start='" + start + '\'' +
                ", place_name='" + place_name + '\'' +
                ", time='" + time + '\'' +
                ", sucess='" + sucess + '\'' +
                '}';
    }
}
