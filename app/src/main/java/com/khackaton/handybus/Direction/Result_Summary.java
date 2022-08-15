package com.khackaton.handybus.Direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result_Summary {
    @SerializedName("duration")
    @Expose
    private String duration;

    @SerializedName("distance")
    @Expose
    private String distance;

    public String getDuration ()
    {
        return duration;
    }
    public void setDuration (String duration)
    {
        this.duration = duration;
    }

    public String getDistance ()
    {
        return distance;
    }
    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [duration = "+duration+", distance = "+distance+"]";
    }
}
