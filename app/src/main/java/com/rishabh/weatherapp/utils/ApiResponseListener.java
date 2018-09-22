package com.rishabh.weatherapp.utils;

import com.rishabh.weatherapp.model.weather_bean.WeatherBean;

/**
 * Created by Rishabh on 22-Sep-18.
 */
public interface ApiResponseListener {

    public void onApiResponseSuccess(WeatherBean response);

    public void onApiResponseFailure(String error);
}
