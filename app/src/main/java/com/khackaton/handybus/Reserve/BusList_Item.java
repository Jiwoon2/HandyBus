package com.khackaton.handybus.Reserve;

public class BusList_Item {
    public String busRouteId;
    public String busRouteType;
    public String busNum;
    public String busArrmsg1;
    public String busArrmsg2;


    public void clear(){
        busRouteId = "";
        busRouteType = "";
        busNum = "";
        busArrmsg1 = "";
        busArrmsg2 = "";
    }

    boolean checkRecvAllData(){
        return busRouteType.length()  > 0
                && busRouteId.length()  > 0
                && busNum.length()  > 0
                && busArrmsg1.length()  > 0
                && busArrmsg2.length()  > 0;
    }

    public void setBusRouteId(String busRouteId) {
        this.busRouteId = busRouteId;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
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

    public String getBusNum() {
        return busNum;
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
