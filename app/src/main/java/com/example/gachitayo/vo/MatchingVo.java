package com.example.gachitayo.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//매칭서버로 보낼 데이터
//intent로 vo 객체를 보내기 위해 Serializable을 implements
public class MatchingVo implements Serializable {
    @SerializedName("userId")
    @Expose
    private String id;

    @SerializedName("userUuid")
    @Expose
    private int uuid;

    @SerializedName("start")
    @Expose
    private String start;

    @SerializedName("placeName")
    @Expose
    private String place_name;

    @SerializedName("endNx")
    @Expose
    private double x;

    @SerializedName("endNy")
    @Expose
    private double y;

    @SerializedName("time")
    @Expose
    private String time;


    public MatchingVo() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUuid() { return uuid; }

    public void setUuid(int uuid) { this.uuid = uuid; }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
