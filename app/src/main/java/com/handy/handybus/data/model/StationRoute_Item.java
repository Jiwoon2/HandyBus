package com.handy.handybus.data.model;

import com.google.firebase.firestore.PropertyName;

public class StationRoute_Item {
    public String busRouteId; //버스 ID
    public String stationNm; //정류소 이름

    public void clear(){
        busRouteId = "";
        stationNm = "";
    }
    public boolean checkRecvAllData(){
        return stationNm.length()  > 0;
    }

    public void setBusRouteId(String busRouteId) {
        this.busRouteId = busRouteId;
    }

    public void setStationNm(String stationNm) {
        this.stationNm = stationNm;
    }

    public String getBusRouteId() {
        return busRouteId;
    }

    public String getStationNm() {
        return stationNm;
    }
}
