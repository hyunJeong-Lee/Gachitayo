package com.example.gachitayo.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//로그인 시에 서버에서 받아온 데이터를 담을 객체

public class UserDto {
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

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("errorId")
    @Expose
    private int errorId;

    @SerializedName("errorPw")
    @Expose
    private int errorPw;

    public UserDto(int uuid, String id, String pw, String name, boolean success, int errorId, int errorPw) {
        this.uuid = uuid;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.success = success;
        this.errorId = errorId;
        this.errorPw = errorPw;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public int getErrorPw() {
        return errorPw;
    }

    public void setErrorPw(int errorPw) {
        this.errorPw = errorPw;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "uuid=" + uuid +
                ", id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", success=" + success +
                ", errorId=" + errorId +
                ", errorPw=" + errorPw +
                '}';
    }
}
