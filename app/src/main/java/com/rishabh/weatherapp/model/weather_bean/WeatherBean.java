package com.rishabh.weatherapp.model.weather_bean;

import android.databinding.BaseObservable;
import android.view.View;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rishabh.weatherapp.R;

public class WeatherBean extends BaseObservable {

    @SerializedName("query")
    @Expose
    private Query query;
    private String mShowData;

    private Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public void showData(View view) {
        Item item = getQuery().getResults().getChannel().getItem();
        String seperator = ", ";
        switch (view.getId()) {
            case R.id.button_today:
                StringBuilder condition = new StringBuilder();
                condition.append(item.getCondition().getDate())
                        .append(seperator)
                        .append(item.getCondition().getText())
                        .append(seperator)
                        .append("Temp-")
                        .append(item.getCondition().getTemp());
                setmShowData(String.valueOf(condition));
                break;
            case R.id.button_forcaste:
                StringBuilder forcaste = new StringBuilder();
                String changeLine = "\n";
                for (int i = 0; i < item.getForecast().size(); i++) {
                    forcaste.append(item.getForecast().get(i).getDate())
                            .append(seperator)
                            .append(item.getForecast().get(i).getText())
                            .append(seperator)
                            .append("High Temp-")
                            .append(item.getForecast().get(i).getHigh())
                            .append(changeLine);
                }
                setmShowData(String.valueOf(forcaste));
                break;
            default:
                setmShowData(item.getForecast().get(0).getText());
                break;
        }
    }

    public String getmShowData() {
        return mShowData;
    }

    private void setmShowData(String data) {
        this.mShowData = data;
        notifyChange();
    }
}
