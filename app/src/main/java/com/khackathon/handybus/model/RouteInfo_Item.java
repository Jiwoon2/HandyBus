package com.khackathon.handybus.model;

public class RouteInfo_Item {
    public String busRouteNm;
    public String busStStationNm;
    public String busEdStationNm;
    public String busFirstBusTm;
    public String busLastBusTm;
    public String busTerm;


    public void setBusRouteNm(String busRouteNm) {
        this.busRouteNm = busRouteNm;
    }

    public void setBusStStationNm(String busStStationNm) {
        this.busStStationNm = busStStationNm;
    }

    public void setBusEdStationNm(String busEdStationNm) {
        this.busEdStationNm = busEdStationNm;
    }

    public void setBusFirstBusTm(String busFirstBusTm) {
        this.busFirstBusTm = busFirstBusTm;
    }

    public void setBusLastBusTm(String busLastBusTm) {
        this.busLastBusTm = busLastBusTm;
    }

    public void setBusTerm(String busTerm) {
        this.busTerm = busTerm;
    }

    public String getBusTerm() {
        return busTerm;
    }

    public String getBusEdStationNm() {
        return busEdStationNm;
    }

    public String getBusFirstBusTm() {
        return busFirstBusTm;
    }

    public String getBusLastBusTm() {
        return busLastBusTm;
    }

    public String getBusRouteNm() {
        return busRouteNm;
    }

    public String getBusStStationNm() {
        return busStStationNm;
    }
}
