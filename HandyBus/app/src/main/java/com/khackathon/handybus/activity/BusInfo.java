package com.khackathon.handybus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khackathon.handybus.R;
import com.khackathon.handybus.adapter.BusListAdapter;
import com.khackathon.handybus.adapter.StationRouteAdapter;
import com.khackathon.handybus.databinding.ActivityBusInfoBinding;
import com.khackathon.handybus.model.BusList_Item;
import com.khackathon.handybus.model.RouteInfo_Item;
import com.khackathon.handybus.model.RouteInfoRepository;
import com.khackathon.handybus.model.StationRouteRepository;
import com.khackathon.handybus.model.StationRoute_Item;
import com.khackathon.handybus.viewmodel.RouteInfoViewmodel;
import com.khackathon.handybus.viewmodel.StationRouteViewmodel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Observer;

public class BusInfo extends AppCompatActivity {

    Intent intent;
    String busRouteId;//노선 ID
    String busRouteNm;//노선명
    Button booking_btn;//예약버튼

    RouteInfoViewmodel routeInfoViewmodel;
    RouteInfo_Item item = new RouteInfo_Item();

    RecyclerView mRecyclerView = null ;
    StationRouteAdapter mAdapter = null ;
    StationRouteViewmodel stationRouteViewmodel;
    ArrayList<StationRoute_Item> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent=getIntent();
        busRouteId=intent.getStringExtra("busRouteId"); //api에 넣을용

        ActivityBusInfoBinding activityBusInfoBinding= DataBindingUtil.setContentView(this, R.layout.activity_bus_info);
        routeInfoViewmodel = new RouteInfoViewmodel(RouteInfoRepository.getRouteInfoFirst());
        activityBusInfoBinding.setBusRouteInfo(routeInfoViewmodel);

        item= routeInfoViewmodel.getBusRouteInfoRepository(); //없어도 상관 x

        try {
            item= routeInfoViewmodel.useRouteInfo(this.getApplicationContext(),busRouteId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //끊임없이 갱신..
        routeInfoViewmodel.getRouteInfoCurrentItem().observe(this, new androidx.lifecycle.Observer<RouteInfo_Item>() {
            @Override
            public void onChanged(RouteInfo_Item item) {
                routeInfoViewmodel.updateRouteInfo(item);
                busRouteNm=item.busRouteNm; //예약페이지로 넘기는 용
                activityBusInfoBinding.setItem(item); //아이템 전부 넣어줌

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
        activityBusInfoBinding.setStRouteviewmodel(stationRouteViewmodel);

        mRecyclerView= activityBusInfoBinding.containerStRoute;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new StationRouteAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        try {
            mList= stationRouteViewmodel.useStationRoute(this.getApplicationContext(),busRouteId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        stationRouteViewmodel.getStationRouteCurrentItem().observe(this, new androidx.lifecycle.Observer<ArrayList<StationRoute_Item>>() {
            @Override
            public void onChanged(ArrayList<StationRoute_Item> item) {
                mAdapter.setItemsList((ArrayList<StationRoute_Item>) item);
            }
        });

    }

    //이전화면으로 이동시 텀 없애기
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}