package com.khackathon.handybus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khackathon.handybus.R;
import com.khackathon.handybus.adapter.BusListAdapter;
import com.khackathon.handybus.databinding.ActivityBusListBinding;
import com.khackathon.handybus.model.BusList_Item;
import com.khackathon.handybus.model.BusListRepository;
import com.khackathon.handybus.viewmodel.BusListViewmodel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class BusList extends AppCompatActivity {

    public static Activity BusListActivity;

    Intent intent;
    String busStID;
    BusList_Item item;

    RecyclerView mRecyclerView = null ;
    BusListAdapter mAdapter = null ;
    ArrayList<BusList_Item> mList = new ArrayList<>();

    private BusListViewmodel buslistViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //데이터 바인딩
        ActivityBusListBinding activityBusListBinding= DataBindingUtil.setContentView(this, R.layout.activity_bus_list);
        buslistViewmodel= new BusListViewmodel(BusListRepository.getBusListFirst());
        activityBusListBinding.setBuslistviewmodel(buslistViewmodel);

        mRecyclerView= activityBusListBinding.containerBusList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setHasFixedSize(true);//옵션

        //mList= buslistViewmodel.getBusListRepository(); //없어도 상관 x

        mAdapter = new BusListAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        intent= getIntent();
        busStID= intent.getStringExtra("busStID"); //정류소 ID 넘겨받음

        BusListActivity=BusList.this;

        try {
            //mList= buslistViewmodel.clearBusList(mList); //없어도 상관 x
            mList= buslistViewmodel.useBusArrInfo(getApplicationContext(), busStID); //검색
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        buslistViewmodel.updateList(mList);

        buslistViewmodel.getBusListCurrentItem().observe(this, (Observer<ArrayList<BusList_Item>>) list->  {

            mAdapter.setItemsList((ArrayList<BusList_Item>) list);

        });
    }

    //이전화면으로 이동시 텀 없애기
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}