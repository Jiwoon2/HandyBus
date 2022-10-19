package com.handy.handybus.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserReserv_Item implements Parcelable {
    String resDepartures;
    String resArrivals;
    String resBusNum;
    String resBusRouteId;
    String resNum;
    String resWheel;
    String resHelp;
    String resDate;

    @Exclude
    String key;


    public UserReserv_Item() {
    }

    protected UserReserv_Item(Parcel in) {
        resDepartures = in.readString();
        resArrivals = in.readString();
        resBusNum = in.readString();
        resBusRouteId = in.readString();
        resNum = in.readString();
        resWheel = in.readString();
        resHelp = in.readString();
        resDate = in.readString();
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resDepartures);
        dest.writeString(resArrivals);
        dest.writeString(resBusNum);
        dest.writeString(resBusRouteId);
        dest.writeString(resNum);
        dest.writeString(resWheel);
        dest.writeString(resHelp);
        dest.writeString(resDate);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserReserv_Item> CREATOR = new Creator<UserReserv_Item>() {
        @Override
        public UserReserv_Item createFromParcel(Parcel in) {
            return new UserReserv_Item(in);
        }

        @Override
        public UserReserv_Item[] newArray(int size) {
            return new UserReserv_Item[size];
        }
    };

    public String getResDepartures() {
        return resDepartures;
    }

    public void setResDepartures(String resDepartures) {
        this.resDepartures = resDepartures;
    }

    public String getResArrivals() {
        return resArrivals;
    }

    public void setResArrivals(String resArrivals) {
        this.resArrivals = resArrivals;
    }

    public String getResBusNum() {
        return resBusNum;
    }

    public void setResBusNum(String resBusNum) {
        this.resBusNum = resBusNum;
    }

    public String getResBusRouteId() {
        return resBusRouteId;
    }

    public void setResBusRouteId(String resBusRouteId) {
        this.resBusRouteId = resBusRouteId;
    }

    public String getResNum() {
        return resNum;
    }

    public void setResNum(String resNum) {
        this.resNum = resNum;
    }

    public String getResWheel() {
        return resWheel;
    }

    public void setResWheel(String resWheel) {
        this.resWheel = resWheel;
    }

    public String getResHelp() {
        return resHelp;
    }

    public void setResHelp(String resHelp) {
        this.resHelp = resHelp;
    }

    public String getResDate() {
        return resDate;
    }

    public void setResDate(String resDate) {
        this.resDate = resDate;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
