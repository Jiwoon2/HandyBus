package com.khackaton.handybus.Direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result_Route {
    @SerializedName("traoptimal")
    @Expose
    private Result_TraOptimal[] traoptimal;

    public Result_TraOptimal[] getTraOptimal ()
    {
        return traoptimal;
    }
    public void setTraOptimal (Result_TraOptimal[] traoptimal)
    {
        this.traoptimal = traoptimal;
    }

    @Override
    public String toString()
    {
        return "ResultPath [traoptimal = "+traoptimal+"]";
    }
}
