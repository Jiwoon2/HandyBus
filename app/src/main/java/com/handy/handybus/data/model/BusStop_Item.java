package com.handy.handybus.data.model;

public class BusStop_Item {
    public String busStID;
    public String busStName;
    public String busArsId;


    public BusStop_Item() {

    }

    public BusStop_Item(String busStID, String busStName, String busArsId) {
        this.busStID = busStID;
        this.busStName = busStName;
        this.busArsId =busArsId;
    }

    public void clear() {
        busStID = "";
        busStName = "";
        busArsId = "";
    }

    public boolean checkRecvAllData() {
        return busStID.length() > 0
                && busStName.length() > 0
                && busArsId.length() > 0;
    }


    public void setBusStName(String busstname) {
        busStName = busstname;
    }

    public void setBusStID(String busstID) {
        busStID = busstID;
    }

    public void setBusArsId(String busArsId) {
        this.busArsId = busArsId;
    }

    public String getBusStName() {
        return this.busStName;
    }

    public String getBusStID() {
        return this.busStID;
    }

    public String getBusArsId() {
        return busArsId;
    }
}
