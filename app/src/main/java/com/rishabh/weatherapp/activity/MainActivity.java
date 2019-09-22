package com.rishabh.weatherapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.rishabh.weatherapp.R;
import com.rishabh.weatherapp.databinding.ActivityMainBinding;
import com.rishabh.weatherapp.model.weather_bean.WeatherBean;
import com.rishabh.weatherapp.utils.ApiResponseListener;
import com.rishabh.weatherapp.utils.GsonManagerUtils;
import com.rishabh.weatherapp.utils.ServiceCallUtils;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ApiResponseListener, LocationListener {

    private static final int REQUEST_ID_LOCATION = 1;
    private ActivityMainBinding mBinding;
    private LocationManager mLocationManager;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationManager != null)
            mLocationManager.removeUpdates(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ID_LOCATION);
        } else {
            getLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String provider = mLocationManager.getBestProvider(criteria, false);

        Location location = mLocationManager.getLastKnownLocation(provider);
        mLocationManager.requestLocationUpdates(provider, 400, 1, this);

        if (location != null) {
            onLocationChanged(location);
        } else {
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onApiResponseSuccess(WeatherBean response) {
        String json = GsonManagerUtils.toJSON(response);
        WeatherBean mWeatherBean = (WeatherBean) GsonManagerUtils.fromJSON(json, WeatherBean.class);
        mBinding.setWeather(mWeatherBean);
    }

    @Override
    public void onApiResponseFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
//You had this as int. It is advised to have Lat/Loing as double.
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            String cityName = addresses.get(0).getLocality();
            new ServiceCallUtils().doGetWeather(this, this,cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (hasAllPermissionsGranted(grantResults)) {
            getLocation();
        } else {
            Toast.makeText(this, "Until you grant the permission, we canot fetch current location.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
