package com.example.arecamithra;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagerWeather {

    SharedPreferences weatherSession;
    SharedPreferences.Editor editor;
    Context context;


    public static final String KEY_TEMPERATURE = "temperature";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";
    public static final String KEY_HUMIDITY = "humidity";

    public  static final String KEY_WEATHERICON="weathericon";

    public SessionManagerWeather(Context _context) {
        context = _context;
        weatherSession = _context.getSharedPreferences("weatherSession", Context.MODE_PRIVATE);
        editor = weatherSession.edit();
    }

    public void createWeatherSession(String temparature, String city, String state, String humidity, String weathericon) {


        editor.putString(KEY_TEMPERATURE, temparature);
        editor.putString(KEY_CITY, city);
        editor.putString(KEY_STATE, state);
        editor.putString(KEY_HUMIDITY, humidity);
        editor.putString(KEY_WEATHERICON, weathericon);
        editor.commit();
    }

    public void updateTemperature(String temperature){
        editor.putString(KEY_TEMPERATURE,temperature);
        editor.commit();
    }
    public void updateCity(String city){
        editor.putString(KEY_CITY,city);
        editor.commit();
    }
    public void updateState(String state){
        editor.putString(KEY_STATE,state);
        editor.commit();
    }
    public void updateHumidity(String humidity){
        editor.putString(KEY_HUMIDITY,humidity);
        editor.commit();
    }
    public  void updateWeatherIcon(String weathericon){
        editor.putString(KEY_WEATHERICON,weathericon);
        editor.commit();
    }

    public HashMap<String, String> getWeatherDetailFromSession() {
        HashMap<String, String> weatherData = new HashMap<String, String>();
        weatherData.put(KEY_TEMPERATURE, weatherSession.getString(KEY_TEMPERATURE, null));
        weatherData.put(KEY_CITY, weatherSession.getString(KEY_CITY, null));
        weatherData.put(KEY_STATE, weatherSession.getString(KEY_STATE, null));
        weatherData.put(KEY_HUMIDITY, weatherSession.getString(KEY_HUMIDITY, null));
        weatherData.put(KEY_WEATHERICON, weatherSession.getString(KEY_WEATHERICON, null));

        return weatherData;
    }
    public void logoutWeatherFromSession() {
        editor.clear();
        editor.commit();
    }

}
