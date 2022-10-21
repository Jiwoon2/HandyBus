package com.handy.handybus.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.handy.handybus.ui.adapter.BusListAdapter;
import com.handy.handybus.databinding.ActivityBusListBinding;
import com.handy.handybus.data.repository.BusListRepository;
import com.handy.handybus.data.model.BusList_Item;
import com.handy.handybus.viewmodel.BusListViewmodel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class BusListActivity extends AppCompatActivity {

    private ActivityBusListBinding binding;

    private String busStName = "";
    private String busStID = "";

    private BusListAdapter adapter = null;
    private ArrayList<BusList_Item> list = new ArrayList<>();

    private BusListViewmodel buslistViewmodel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        busStName = getIntent().getStringExtra("busStName");
        busStID = getIntent().getStringExtra("busStID");

        if (savedInstanceState != null) {
            busStName = savedInstanceState.getString("busStName");
            busStID = savedInstanceState.getString("busStID");
        }

        if (busStName == null || busStID == null) {
            finish();
            return;
        }

        binding = ActivityBusListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        buslistViewmodel = new BusListViewmodel(BusListRepository.getBusListFirst());

        //mList= buslistViewmodel.getBusListRepository(); //없어도 상관 x

        binding.toolbar.setTitle(busStName);
        binding.toolbar.setSubtitle(busStID);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        adapter = new BusListAdapter(busStName, list);
        binding.recyclerView.setHasFixedSize(true);//옵션
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        try {
            //mList= buslistViewmodel.clearBusList(mList); //없어도 상관 x
            list = buslistViewmodel.useBusArrInfo(getApplicationContext(), busStID); //검색

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        buslistViewmodel.updateList(list);

        buslistViewmodel.getBusListCurrentItem().observe(this, (Observer<ArrayList<BusList_Item>>) list -> {
            adapter.setItemsList(list);
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
        outState.putString("busStID", busStID);
    }
}