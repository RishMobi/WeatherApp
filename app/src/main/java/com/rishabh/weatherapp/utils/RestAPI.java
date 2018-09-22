package com.rishabh.weatherapp.utils;

import com.rishabh.weatherapp.model.weather_bean.WeatherBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rishabh on 22-Sep-18.
 */
public interface RestAPI {

    @GET("yql")
    Call<WeatherBean> getWeather(@Query("q") String query,
                                 @Query("format") String format);

}
