package com.khackathon.handybus.model;

import androidx.lifecycle.MutableLiveData;


public class RouteInfoRepository {
    private static RouteInfo_Item item = new RouteInfo_Item();
    private static MutableLiveData<RouteInfo_Item> mutableLiveDataItemsList = new MutableLiveData<>();
    private static RouteInfoRepository routeInfoRepository;

    public RouteInfoRepository(){
        //item.clear(); //초기화 필요하면 만들기
        mutableLiveDataItemsList.setValue(item);
    }

    public static RouteInfoRepository getRouteInfoFirst(){
        routeInfoRepository= new RouteInfoRepository();
        return routeInfoRepository;
    }

    //리스트 데이터 초기화
    public RouteInfo_Item clearRouteInfo(RouteInfo_Item item){
        //list.clear();
        this.item=item;
        mutableLiveDataItemsList.postValue(item);
        return this.item;
    }

    public MutableLiveData<RouteInfo_Item> getRouteInfoCurrentItem() {
        return mutableLiveDataItemsList;
    }

    public RouteInfo_Item getBusRouteInfo(){
        return item;
    }

    //갱신
    public void updateRouteInfo(RouteInfo_Item item){
        mutableLiveDataItemsList.postValue(item);
//        System.out.println("22222222447777"+item.busRouteNm);
    }
}
