package com.rishabh.weatherapp.utils;

import com.google.gson.Gson;

/**
 * Created by Rishabh on 22-Sep-18.
 */
public class GsonManagerUtils {

    public static String toJSON(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }

    public static Object fromJSON(String data, Class className) {
        Gson gson = new Gson();
        Object value = gson.fromJson(data, className);
        return value;
    }
}
