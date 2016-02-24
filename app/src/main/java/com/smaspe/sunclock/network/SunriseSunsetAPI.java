package com.smaspe.sunclock.network;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SunriseSunsetAPI {

    @GET("/json?formatted=0")
    Observable<ResponseContainer> getTodaySunriseSunsetTimes(@Query("lat") double lat, @Query("lng") double lng);
}
