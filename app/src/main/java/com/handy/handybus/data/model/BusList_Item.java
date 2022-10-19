package com.handy.handybus.data.model;

public class BusList_Item {

    public String busRouteId;
    public String busRouteType;
    public String busRtNm;
    public String busArrmsg1;
    public String busArrmsg2;


    public void clear(){
        busRouteId = "";
        busRouteType = "";
        busRtNm = "";
        busArrmsg1 = "";
        busArrmsg2 = "";
    }

    public boolean checkRecvAllData(){
        return busRouteType.length()  > 0
                && busRouteId.length()  > 0
                && busRtNm.length()  > 0
                && busArrmsg1.length()  > 0
                && busArrmsg2.length()  > 0;
    }

    public void setBusRouteId(String busRouteId) {
        this.busRouteId = busRouteId;
    }

    public void setBusRtNm(String busRtNm) {
        this.busRtNm = busRtNm;
    }

    public void setBusRouteType(String busRouteType) {
        this.busRouteType = busRouteType;
    }

    public void setBusArrmsg1(String busArrmsg1) {
        this.busArrmsg1 = busArrmsg1;
    }

    public void setBusArrmsg2(String busArrmsg2) {
        this.busArrmsg2 = busArrmsg2;
    }

    public String getBusRouteId() {
        return busRouteId;
    }

    public String getBusRtNm() {
        return busRtNm;
    }

    public String getBusRouteType() {
        return busRouteType;
    }

    public String getBusArrmsg1() {
        return busArrmsg1;
    }

    public String getBusArrmsg2() {
        return busArrmsg2;
    }

}
