package com.khackaton.handybus.Direction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultPath {
    @SerializedName("code")
    @Expose
    private Integer code;

    @SerializedName("route")
    @Expose
    private Result_Route route;

    @SerializedName("message")
    @Expose
    private String message;

    public Integer getCode ()
    {
        return code;
    }
    public void setCode (Integer code)
    {
        this.code = code;
    }

    public Result_Route getRoute ()
    {
        return route;
    }
    public void setRoute (Result_Route route)
    {
        this.route = route;
    }

    public String getMessage ()
    {
        return message;
    }
    public void setMessage (String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ResultPath [code = "+code+", route = "+route+", message = "+message+"]";
    }
}
