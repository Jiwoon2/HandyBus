package com.handy.handybus.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.handy.handybus.data.source.StationRouteAPI;
import com.handy.handybus.data.repository.StationRouteRepository;
import com.handy.handybus.data.model.StationRoute_Item;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class StationRouteViewmodel {
    private StationRouteRepository stationRouteRepository;
    private StationRouteAPI stationRouteAPI = new StationRouteAPI();

    //없으면 새로 아이템 가져오는거고..
    public LiveData<ArrayList<StationRoute_Item>> getStationRouteCurrentItem() {

        return stationRouteRepository.getStationRouteCurrentItem();
    }

    public StationRouteViewmodel(StationRouteRepository stationRouteRepository){
        this.stationRouteRepository=stationRouteRepository;
    }

    public void getStationRouteFirst(){
        stationRouteRepository.getStationRouteFirst();
    }

    public ArrayList<StationRoute_Item> getStationRoute() {
        return stationRouteRepository.getStationRoute();
    }

    public void updateStationRoute(ArrayList<StationRoute_Item> list){
        stationRouteRepository.updateStationRoute(list);
    }

    public ArrayList<StationRoute_Item> clearStationRoute(ArrayList<StationRoute_Item> list){
        return stationRouteRepository.clearStationRoute(list);
    }

    public ArrayList<StationRoute_Item> useStationRoute(Context context, String edit) throws UnsupportedEncodingException {
        return stationRouteAPI.search_StationRoute(context, edit);
    }

}
