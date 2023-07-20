package com.example.arecamithra;

import org.json.JSONException;
import org.json.JSONObject;

public class weatherData {

    private String mTemperature, mIcon, mCity, weatherType,humidity;
    private int mCondition;

    public static weatherData fromJson(JSONObject jsonObject) {
        try {
            weatherData weatherD = new weatherData();
            weatherD.mCity = jsonObject.getString("name");
            weatherD.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.weatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.mIcon = updateWeatherIcon(weatherD.mCondition);
            double tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
            int h=jsonObject.getJSONObject("main").getInt("humidity");

            int roundedValue = (int) Math.rint(tempResult);
            weatherD.mTemperature = Integer.toString(roundedValue);
            weatherD.humidity=Integer.toString(h);
            return weatherD;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String updateWeatherIcon(int mCondition) {
        if (mCondition >= 0 && mCondition <= 300) {
            return "thunderstorm";
        } else if (mCondition >= 300 && mCondition <= 500) {
            return "lightrain";
        } else if (mCondition >= 500 && mCondition <= 600) {
            return "shower";
        } else if (mCondition >= 600 && mCondition <= 700) {
            return "snow2";
        } else if (mCondition >= 701 && mCondition <= 771) {
            return "fog";
        } else if (mCondition >= 772 && mCondition <= 800) {
            return "overcast";
        } else if (mCondition == 800) {
            return "sunny";
        } else if (mCondition >= 801 && mCondition <= 804) {
            return "cloudy";
        } else if (mCondition >= 900 && mCondition <= 902) {
            return "thunderstorm";
        }
        if (mCondition == 903) {
            return "snow1";
        }
        if (mCondition == 904) {
            return "sunny";
        }
        if (mCondition >= 905 && mCondition <= 1000) {
            return "thunderstorm";
        }
        return "dunno";
    }

    public String getmTemperature() {
        return mTemperature + "°C";
    }

    public String getmIcon() {
        return mIcon;
    }

    public String getmCity() {
        return mCity;
    }

    public String getWeatherType() {
        return weatherType;
    }
    public String getHumidity(){
        return "humidity "+humidity+"%";
    }
}
