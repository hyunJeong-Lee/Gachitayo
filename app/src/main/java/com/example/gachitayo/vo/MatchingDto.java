package com.example.gachitayo.vo;

import java.util.List;

public class MatchingDto {
    List<ResponseDto> group;

    public List<ResponseDto> getGroup() {
        return group;
    }

    public void setGroup(List<ResponseDto> group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "MatchingDto{" +
                "group=" + group.get(0) +
                '}';
    }
}
