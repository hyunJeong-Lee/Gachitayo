package com.example.gachitayo.map;

import static android.graphics.Color.*;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.gachitayo.R;
import com.example.gachitayo.Retrofit_APIs.KakaoAPIClient;
import com.example.gachitayo.vo.PlaceInfo;
import com.example.gachitayo.vo.SearchResult;

import net.daum.mf.map.api.MapView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity {

    EditText et_search;
    MapView mapView;
    ListView mapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
//
//        mapView = new MapView(this);
//
//        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
//        mapViewContainer.addView(mapView);

        //editText에 텍스트 변화 감지
        et_search = (EditText) findViewById(R.id.map_etSearch);
        et_search.addTextChangedListener(textWatcher);
    }

    //editText 텍스트 입력 변화 감지 이벤트 처리
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //변화와 동시에 처리. 입력하기 전에 호출.

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //입력이 끝날을 때 처리. EditText에 변화가 있을 때
            String str = charSequence.toString();
            //null object 참조 방지
            if(et_search.getText().toString().length() >0){
                searchKeyword(str);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //입력해 변화가 생기기 전에 처리. 입력이 끝났을 때
        }
    };

    //키워드 검색 함수
    private void searchKeyword(String keyword){
        String API_KEY = "KakaoAK c7710d11a052a2a4bfe43a51531c3219";
        Call<SearchResult> func = KakaoAPIClient.getApiService().getSearchKeword(API_KEY, keyword);
        func.enqueue(new Callback<SearchResult>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
//                Log.e("leehj", "Raw: "+response.raw());
//                Log.e("leehj", "Body: "+response.body().getDocuments().toString());
                List<PlaceInfo> result = response.body().getDocuments();
                MyAdapter mAdapter = new MyAdapter(result);
                mapList = (ListView) findViewById(R.id.map_ListView);
                mapList.setAdapter(mAdapter);

                mapList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //선택한 장소명 가져오기
                        String place_name = result.get(i).getPlace_name();
                        String road_address = result.get(i).getRoad_address_name();
                        //선택한 장소 좌표값 가져오기
                        String x = result.get(i).getX();
                        String y = result.get(i).getY();
                        Log.e("leehj", "x: "+x);
                        Log.e("leehj", "y: "+y);

                        //해당 좌표값을 지도로 보여주기
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        Fragment fragment = new Fragment(place_name,road_address, x, y);
                        transaction.replace(R.id.map_frame, fragment);
                        Log.i("leehj", "fragment 교체");
                        transaction.commit();
                    }
                });
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.w("leehj", "통신 실패 : "+t.getMessage());
            }
        });
    }
}