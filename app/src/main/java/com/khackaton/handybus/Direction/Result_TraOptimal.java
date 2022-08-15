package com.khackaton.handybus.Direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result_TraOptimal {
    @SerializedName("summary")
    @Expose
    private Result_Summary summary;

    @SerializedName("path")
    @Expose
    private Double[][] path;

    public Result_Summary getSummary ()
    {
        return summary;
    }
    public void setSummary (Result_Summary summary)
    {
        this.summary = summary;
    }

    public Double[][] getPath ()
    {
        return path;
    }
    public void setPath (Double[][] path)
    {
        this.path = path;
    }

    @Override
    public String toString()
    {
        return "ResultPath [summary = "+summary+", path = "+path+"]";
    }
}
