package com.handy.handybus.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.handy.handybus.data.source.RouteInfoAPI;
import com.handy.handybus.data.model.RouteInfo_Item;
import com.handy.handybus.data.repository.RouteInfoRepository;

import java.io.UnsupportedEncodingException;

public class RouteInfoViewmodel {
    private RouteInfoAPI routeInfoAPI = new RouteInfoAPI();
    RouteInfoRepository routeInfoRepository;

    //없으면 새로 아이템 가져오는거고..
    public LiveData<RouteInfo_Item> getRouteInfoCurrentItem() {
        return routeInfoRepository.getRouteInfoCurrentItem();
    }

    public RouteInfoViewmodel(RouteInfoRepository routeInfoRepository){
        this.routeInfoRepository=routeInfoRepository;
    }

    public void getRouteInfoFirst(){
        routeInfoRepository.getRouteInfoFirst();
    }
//
    public RouteInfo_Item getBusRouteInfoRepository() {
        //ItemRepository repository =new ItemRepository();
        return routeInfoRepository.getBusRouteInfo();
    }
//
    public void updateRouteInfo(RouteInfo_Item item){
        routeInfoRepository.updateRouteInfo(item);
    }
//
//    public ArrayList<RecyclerItem_BusList> clearBusList(ArrayList<RecyclerItem_BusList> list){
//        return busListRepository.clearBusList(list);
//    }

//    public ArrayList<RouteInfoItem> useRouteInfo(Context context, String edit) throws UnsupportedEncodingException {
//        return routeInfoAPI.search_RouteInfo(context, edit);
//    }
    public RouteInfo_Item useRouteInfo(Context context, String edit) throws UnsupportedEncodingException {
        return routeInfoAPI.search_RouteInfo(context, edit);
    }


}
