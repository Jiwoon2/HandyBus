package com.handy.handybus.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.handy.handybus.model.BusArrInfoAPI;
import com.handy.handybus.model.BusList_Item;
import com.handy.handybus.model.BusListRepository;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class BusListViewmodel {
    private BusListRepository busListRepository;
    private BusArrInfoAPI busArrInfoAPI = new BusArrInfoAPI();

    //없으면 새로 아이템 가져오는거고..
    public LiveData<ArrayList<BusList_Item>> getBusListCurrentItem() {

        return busListRepository.getBusListCurrentItem();
    }

    public BusListViewmodel(BusListRepository busListRepository){
        this.busListRepository=busListRepository;
    }

    public void getBusListFirst(){
        busListRepository.getBusListFirst();
    }

    public ArrayList<BusList_Item> getBusListRepository() {
        //ItemRepository repository =new ItemRepository();
        return busListRepository.getBusList();
    }

    public void updateList(ArrayList<BusList_Item> list){
        busListRepository.updateList(list);
    }

    public ArrayList<BusList_Item> clearBusList(ArrayList<BusList_Item> list){
        return busListRepository.clearBusList(list);
    }

    public ArrayList<BusList_Item> useBusArrInfo(Context context, String edit) throws UnsupportedEncodingException {
        return busArrInfoAPI.search_busArrInfo(context, edit);
    }


}
