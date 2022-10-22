package com.handy.handybus.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.handy.handybus.ui.adapter.StationRouteAdapter;
import com.handy.handybus.data.repository.RouteInfoRepository;
import com.handy.handybus.data.model.RouteInfo_Item;
import com.handy.handybus.data.repository.StationRouteRepository;
import com.handy.handybus.data.model.StationRoute_Item;
import com.handy.handybus.databinding.ActivityBusInfoBinding;
import com.handy.handybus.viewmodel.RouteInfoViewmodel;
import com.handy.handybus.viewmodel.StationRouteViewmodel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class BusInfoActivity extends AppCompatActivity {

    private ActivityBusInfoBinding binding;

    private String busStName;
    private String busNumber;
    private String busRouteId;//노선 ID

    private String busRouteNm;//노선명

    RouteInfoViewmodel routeInfoViewmodel;
    RouteInfo_Item item = new RouteInfo_Item();

    StationRouteAdapter adapter = null;
    StationRouteViewmodel stationRouteViewmodel;
    ArrayList<StationRoute_Item> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        busStName = getIntent().getStringExtra("busStName");
        busNumber = getIntent().getStringExtra("busNumber");
        busRouteId = getIntent().getStringExtra("busRouteId");

        if (savedInstanceState != null) {
            busStName = savedInstanceState.getString("busStName");
            busNumber = savedInstanceState.getString("busNumber");
            busRouteId = savedInstanceState.getString("busRouteId");
        }

        if (busStName == null || busNumber == null || busRouteId == null) {
            finish();
            return;
        }

        binding = ActivityBusInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setTitle(busNumber);
        binding.toolbar.setSubtitle(busStName);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        routeInfoViewmodel = new RouteInfoViewmodel(RouteInfoRepository.getRouteInfoFirst());

        item = routeInfoViewmodel.getBusRouteInfoRepository(); //없어도 상관 x

        try {
            item = routeInfoViewmodel.useRouteInfo(this.getApplicationContext(), busRouteId);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 끊임없이 갱신..
        routeInfoViewmodel.getRouteInfoCurrentItem().observe(this, new androidx.lifecycle.Observer<RouteInfo_Item>() {
            @Override
            public void onChanged(RouteInfo_Item item) {
                routeInfoViewmodel.updateRouteInfo(item);
                busRouteNm = item.busRouteNm; //예약페이지로 넘기는 용

                binding.setItem(item);
            }
        });

//        booking_btn= activityBusInfoBinding.bookingBtn; //예약하기
//        booking_btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                intent= new Intent(v.getContext(), ReserveActivity.class);
//                intent.putExtra("busRouteNm",busRouteNm); //노선명 전달
//                startActivity(intent);
//
//                //화면 전환시 텀 없애기
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                ((Activity)v.getContext()).overridePendingTransition(0,0);
//            }
//        });

        //경유 정류소
        stationRouteViewmodel = new StationRouteViewmodel(StationRouteRepository.getStationRouteFirst());

        adapter = new StationRouteAdapter(list);
        binding.recyclerView.setAdapter(adapter);

        try {
            list = stationRouteViewmodel.useStationRoute(this.getApplicationContext(), busRouteId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        stationRouteViewmodel.getStationRouteCurrentItem().observe(this, new androidx.lifecycle.Observer<ArrayList<StationRoute_Item>>() {
            @Override
            public void onChanged(ArrayList<StationRoute_Item> item) {
                adapter.setItemsList((ArrayList<StationRoute_Item>) item);
            }
        });
    }

    //이전화면으로 이동시 텀 없애기
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("busStName", busStName);
        outState.putString("busNumber", busNumber);
        outState.putString("busRouteId", busRouteId);
    }
}