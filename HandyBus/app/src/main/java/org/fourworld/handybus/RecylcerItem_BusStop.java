package org.fourworld.handybus;

public class RecylcerItem_BusStop {
    public String busStID;
    public String busStName;

    public void clear(){
        busStID = "";
        busStName = "";
    }

    boolean checkRecvAllData(){
        return busStID.length()  > 0
                && busStName.length()  > 0;
    }


    public void setBusStName(String busstname) {
        busStName = busstname ;
    }
    public void setBusStID(String busstID) {
        busStID = busstID ;
    }

    public String getBusStName() {
        return this.busStName ;
    }
    public String getBusStID() {
        return this.busStID ;
    }
}
