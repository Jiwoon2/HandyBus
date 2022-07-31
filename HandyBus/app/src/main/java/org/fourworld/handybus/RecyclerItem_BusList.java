package org.fourworld.handybus;

public class RecyclerItem_BusList {
    //public String busStID;
    public String busRouteType;
    public String busNum;
    public String busArrmsg1;
    public String busArrmsg2;


    public void clear(){
        busRouteType = "";
        busNum = "";
        busArrmsg1 = "";
        busArrmsg2 = "";
    }

    boolean checkRecvAllData(){
        return busRouteType.length()  > 0
                && busNum.length()  > 0
                && busArrmsg1.length()  > 0
                && busArrmsg2.length()  > 0;
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
