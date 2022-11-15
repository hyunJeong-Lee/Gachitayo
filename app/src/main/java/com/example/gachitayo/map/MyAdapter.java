package com.example.gachitayo.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gachitayo.R;
import com.example.gachitayo.vo.PlaceInfo;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<PlaceInfo> myItems = new ArrayList<PlaceInfo>();

    public MyAdapter(List<PlaceInfo> result) {
        this.myItems = result;
    }

    @Override
    public int getCount() {
        return myItems.size();
    }

    @Override
    public PlaceInfo getItem(int i) {
        return myItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();

        //custom_listView Layout을 inflate해 view 참조
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_listview, viewGroup, false);
        }

        // custom_listView에 정의된 위젯 참조
        TextView placeName = view.findViewById(R.id.list_placeName);
        TextView cate_groupName = view.findViewById(R.id.list_cateGroupName);
        TextView road_addressName = view.findViewById(R.id.list_road_address);
        TextView addressName = view.findViewById(R.id.list_address);

        //각 리스트에 뿌려줄 아이템 받아오기
        PlaceInfo mItem = getItem(i);

        //각 위젯에 아이템 세팅
        placeName.setText(mItem.getPlace_name());
        cate_groupName.setText(mItem.getCategory_group_name());
        road_addressName.setText(mItem.getRoad_address_name());
        addressName.setText(mItem.getAddress_name());

        return view;
    }
}
