package com.example.gachitayo.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gachitayo.MainActivity;
import com.example.gachitayo.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class Fragment extends androidx.fragment.app.Fragment {

    Intent intent;
    static final int RESULT_OK = -1;
    Button setEnd;
    String place_name;
    String road_address;
    double x, y;

    public Fragment(String place_name,String road_address, String x, String y){
        this.place_name = place_name;
        this.road_address = road_address;
        this.x=Double.parseDouble(x);
        this.y=Double.parseDouble(y);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        MapView mapView = new MapView(requireActivity());
        ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        Log.i("leehj", "fragment x: "+x);
        Log.i("leehj", "fragment y: "+y);


        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(y, x), true);
        mapView.setZoomLevel(3, true);

        MapPoint MARKER_PT = MapPoint.mapPointWithGeoCoord(y, x);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(place_name);
        marker.setTag(0);
        marker.setMapPoint(MARKER_PT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mapView.addPOIItem(marker);

        setEnd = (Button)view.findViewById(R.id.map_setLocation);
        setEnd.setOnClickListener(click_setEnd);

        return view;
    }

    View.OnClickListener click_setEnd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            intent = getActivity().getIntent();
            intent.putExtra("place_name", place_name);
            intent.putExtra("x", x);
            intent.putExtra("y", y);
            intent.putExtra("road_address", road_address);
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
        }
    };
}