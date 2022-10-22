package com.handy.handybus.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.handy.handybus.data.model.StationRoute_Item;

import java.util.ArrayList;

public class StationRouteRepository {
    private static ArrayList<StationRoute_Item> items = new ArrayList<>();
    private static MutableLiveData<ArrayList<StationRoute_Item>> mutableLiveDataItemsList = new MutableLiveData<>();
    private static StationRouteRepository stationRouteRepository;

    public StationRouteRepository(){
        items.clear();
        mutableLiveDataItemsList.setValue(items);
    }

    public static StationRouteRepository getStationRouteFirst(){
        stationRouteRepository= new StationRouteRepository();
        return stationRouteRepository;
    }

    //리스트 데이터 초기화
    public ArrayList<StationRoute_Item> clearStationRoute(ArrayList<StationRoute_Item> list){
        list.clear();
        items=list;
        mutableLiveDataItemsList.postValue(list);
        return items;
    }

    public MutableLiveData<ArrayList<StationRoute_Item>> getStationRouteCurrentItem() {
        return mutableLiveDataItemsList;
    }

    public ArrayList<StationRoute_Item> getStationRoute(){
        return items;
    }

    //갱신
    public void updateStationRoute(ArrayList<StationRoute_Item> list){
        mutableLiveDataItemsList.postValue(list);
    }
}
