package com.khackaton.handybus.Direction;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("v1/driving")
    //DTO 클래스에서 받아온 getPath 값을 ResultPath로 변환
    Call<ResultPath> getPath(
            @Header("X-NCP-APIGW-API-KEY-ID")String apiKeyID,
            @Header("X-NCP-APIGW-API-KEY")String apiKey,
            @Query("start")String start,
            @Query("goal")String goal
            );
}
