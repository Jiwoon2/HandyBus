package com.handy.handybus.ui.search;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.handy.handybus.model.BusStopRepository;
import com.handy.handybus.model.BusStop_Item;
import com.handy.handybus.model.StationNameAPI;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class BusStopViewmodel extends ViewModel {
    private BusStopRepository busStopRepository;
    private StationNameAPI stationNameAPI = new StationNameAPI();

    //없으면 새로 아이템 가져오는거고..
    public LiveData<ArrayList<BusStop_Item>> getBusStopCurrentItem() {

        return busStopRepository.getBusStopCurrentItem();
    }

    public BusStopViewmodel(BusStopRepository busStopRepository){
        this.busStopRepository=busStopRepository;
    }

    public void getBusStopFirst(){
        busStopRepository.getBusStopFirst();
    }

    public ArrayList<BusStop_Item> getBusStopRepository() {
        //ItemRepository repository =new ItemRepository();
        return busStopRepository.getBusStop();
    }

    public void updateBusStop(ArrayList<BusStop_Item> list){
        busStopRepository.updateBusStop(list);
    }

    public ArrayList<BusStop_Item> clearBusStop(ArrayList<BusStop_Item> list){
        return busStopRepository.clearBusStop(list);
    }

    public ArrayList<BusStop_Item> useStationName(View root, String busRouteNm) throws UnsupportedEncodingException {
        return stationNameAPI.search_stationName(root, busRouteNm);
    }

}
