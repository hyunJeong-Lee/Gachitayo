package com.example.gachitayo.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//로그인, 회원가입 시에 서버로 보낼 UserVo 객체

public class UserVo {
    @SerializedName("uuid")
    @Expose
    private int uuid;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("pw")
    @Expose
    private String pw;

    @SerializedName("name")
    @Expose
    private String name;

    public UserVo(String id){
        this.id = id;
    }

    public UserVo(String id, String pw){
        this.id=id;
        this.pw =pw;
    }

    public UserVo(int uuid, String id, String pw, String name) {
        this.uuid = uuid;
        this.id = id;
        this.pw = pw;
        this.name = name;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "uuid=" + uuid +
                ", id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
