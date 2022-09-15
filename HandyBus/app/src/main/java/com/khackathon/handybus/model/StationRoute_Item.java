package com.khackathon.handybus.model;

public class StationRoute_Item {
    public String busRouteId;
    public String stationNm;

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
