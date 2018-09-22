package com.rishabh.weatherapp.utils;

import android.content.Context;

import com.rishabh.weatherapp.model.weather_bean.WeatherBean;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Rishabh on 22-Sep-18.
 */
public class ServiceCallUtils {
    public void doGetWeather(Context mContext, final ApiResponseListener mApiResponseListener, String cityName) {
        Call<WeatherBean> weatherCall = RetrofitManager.getInstance().getService().getWeather("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\""+cityName+"\")","json");
        weatherCall.enqueue(new CustomCallBacks<WeatherBean>(mContext, true) {
            @Override
            public void onSucess(Call<WeatherBean> call, Response<WeatherBean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mApiResponseListener.onApiResponseSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Throwable arg0) {
                mApiResponseListener.onApiResponseFailure(arg0.getMessage());
            }
        });
    }
}
