package com.example.gachitayo.map;

//리스트뷰에 뿌려줄 데이터 정의
public class MyItem {
    String id; //장소 ID
    String place_name; //장소명, 업체명
    String category_group_name; // 중요 카테고리만 그룹핑한 카테고리 그룹명
    String phone; //전화번호
    String address_name; // 전체 지번 주소
    String road_address_name; //전체 도로명 주소
    String x; //x좌표값, 경위도인 경우 longitude (경도)
    String y; //y좌표값, 경위도인 경우 latitude (위도)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getCategory_group_name() {
        return category_group_name;
    }

    public void setCategory_group_name(String category_group_name) {
        this.category_group_name = category_group_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getRoad_address_name() {
        return road_address_name;
    }

    public void setRoad_address_name(String road_address_name) {
        this.road_address_name = road_address_name;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "id='" + id + '\'' +
                ", place_name='" + place_name + '\'' +
                ", category_group_name='" + category_group_name + '\'' +
                ", phone='" + phone + '\'' +
                ", address_name='" + address_name + '\'' +
                ", road_address_name='" + road_address_name + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
