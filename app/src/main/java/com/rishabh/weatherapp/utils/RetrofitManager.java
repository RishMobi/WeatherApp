package com.rishabh.weatherapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rishabh on 22-Sep-18.
 */
public class RetrofitManager {


    private static RetrofitManager instance;
    private final String BASEURL = "https://query.yahooapis.com/v1/public/";
    private RestAPI service;

    public static RetrofitManager getInstance() {
        if (instance == null) {
            instance = new RetrofitManager();
        }
        return instance;
    }

    public RestAPI getService() {
        return (service == null) ? setService() : service;
    }

    private RestAPI setService() {
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient.Builder httppClient = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = httppClient.build();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.client(client).build();
        service = retrofit.create(RestAPI.class);
        return service;

    }
}
