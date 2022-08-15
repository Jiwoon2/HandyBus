package com.khackaton.handybus.Direction;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.khackaton.handybus.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.overlay.PathOverlay;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import kotlin.jvm.internal.markers.KMutableList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/*프래그먼트로 변경!*/
public class DirectionActivity extends Fragment {
    public NaverMap naverMap;
    public View view;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.direction_fragment, container, false);

        //네이버 API(맵, 길찾기 등등)인증 정보
        String naver_client_id = getString(R.string.NAVER_CLIENT_ID);
        String naver_client = getString(R.string.NAVER_CLIENT_SECRET);

        //네이버 맵
        NaverMapSdk.getInstance(getContext()).setClient(
                new NaverMapSdk.NaverCloudPlatformClient(naver_client_id)
        );

//        //네이버 길찾기 -> 레트로 핏 사용 필요
//        Retrofit retrofit = new Retrofit.Builder().
//                baseUrl("https://naveropenapi.apigw.ntruss.com/map-direction/").
//                addConverterFactory(GsonConverterFactory.create()).build();
//        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
//        Call<ResultPath> path = retrofitAPI.getPath(naver_client_id, naver_client, "129.089441, 35.231100", "129.084454, 35.228982");
//
//        /*그림으로 그리기*/
//        path.enqueue(new Callback<ResultPath>() {
//            @Override
//            public void onResponse(Call<ResultPath> call, Response<ResultPath> response) {
//                Result_TraOptimal[] path_cords_list = response.body().getRoute().getTraOptimal();
//                PathOverlay pathOverlay = new PathOverlay();
//                ArrayList<LatLng> path_container = new ArrayList<>();
//                for(Result_TraOptimal path_cords : path_cords_list){
//                    for(Double[] path_cords_xy : path_cords.getPath()){
//                        path_container.add(new LatLng(path_cords_xy[1],path_cords_xy[0]));
//                    }
//                }
//                pathOverlay.setCoords(path_container);
//                pathOverlay.setColor(Color.BLUE);
//                pathOverlay.setPassedColor(Color.GRAY);
//                pathOverlay.setWidth(30);
//                pathOverlay.setProgress(0.5);
//                pathOverlay.setMap(naverMap);
//
//
//                //카메라 위치 조정
//                if(pathOverlay.getCoords() != null){
//                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(pathOverlay.getCoords().get(0))
//                            .animate(CameraAnimation.Fly, 3000);
//                    naverMap.moveCamera(cameraUpdate);
//                    Toast.makeText(getContext(), "경로 안내를 시작합니다.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResultPath> call, Throwable t) {
//                Log.v("error",t.getMessage());
//            }
//        });

        return view;
    }
}