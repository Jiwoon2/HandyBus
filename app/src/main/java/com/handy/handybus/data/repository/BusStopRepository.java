package com.handy.handybus.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.handy.handybus.data.model.BusStop_Item;

import java.util.ArrayList;

public class BusStopRepository {
    private static ArrayList<BusStop_Item> items = new ArrayList<>();
    private static MutableLiveData<ArrayList<BusStop_Item>> mutableLiveDataItemsList = new MutableLiveData<>();
    private static BusStopRepository busStopRepository;


    public BusStopRepository() {
        items.clear();
//        items.add(new RecylcerItem_BusStop( "Item 1", "READY","Dd"));
//        items.add(new Item("Item 2", "READY"));
//        items.add(new Item( "Item 3", "READY"));

        //아이템을 mutableLiveDataList에 지정하기
        mutableLiveDataItemsList.setValue(items);
    }

    //리스트 데이터 초기화
    public ArrayList<BusStop_Item> clearBusStop(ArrayList<BusStop_Item> list){
        list.clear();
        items=list;
        mutableLiveDataItemsList.postValue(list);

        return items;
    }

    public MutableLiveData<ArrayList<BusStop_Item>> getBusStopCurrentItem() {
        return mutableLiveDataItemsList;
    }

    public static BusStopRepository getBusStopFirst(){
        busStopRepository= new BusStopRepository();
        return busStopRepository;
    }

    public ArrayList<BusStop_Item> getBusStop(){
        return items;
    }

    //갱신
    public void updateBusStop(ArrayList<BusStop_Item> list){
        mutableLiveDataItemsList.postValue(list);
    }

}
