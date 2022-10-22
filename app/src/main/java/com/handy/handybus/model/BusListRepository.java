package com.handy.handybus.model;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class BusListRepository {
    private static ArrayList<BusList_Item> items = new ArrayList<>();
    private static MutableLiveData<ArrayList<BusList_Item>> mutableLiveDataItemsList = new MutableLiveData<>();
    private static BusListRepository busListRepository;

    public BusListRepository(){
        items.clear();
        mutableLiveDataItemsList.setValue(items);
    }

    public static BusListRepository getBusListFirst(){
        busListRepository= new BusListRepository();
        return busListRepository;
    }

    //리스트 데이터 초기화
    public ArrayList<BusList_Item> clearBusList(ArrayList<BusList_Item> list){
        list.clear();
        items=list;
        mutableLiveDataItemsList.postValue(list);
        return items;
    }

    public MutableLiveData<ArrayList<BusList_Item>> getBusListCurrentItem() {
        return mutableLiveDataItemsList;
    }

    public ArrayList<BusList_Item> getBusList(){
        return items;
    }

    //갱신
    public void updateList(ArrayList<BusList_Item> list){
        mutableLiveDataItemsList.postValue(list);
    }


}
