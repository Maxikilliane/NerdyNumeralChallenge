package de.mi.ur.WeatherExtras;

import android.content.Context;

import de.mi.ur.Constants;

/**
 * Created by Anna-Marie on 11.08.2016.
 * Diese Klasse wird derzeit nicht genutzt. Ist als private Klasse in die GameMainActivity ausgelagert.
 *
 */
public class WeatherManager {
    private int currentWeather;
    private String weatherUrlPart1 = "http://api.openweathermap.org/data/2.5/weather?";
    private String weatherUrlPart2Lat = "lat=";
    private String weatherUrlPart3Lon = "&lon=";
    private String weatherUrlPart4AppId = "&appid="+Constants.API_ID;
    private WeatherAsyncTask weatherTask;
    private LocationController locationController;

    public WeatherManager(Context context){
        locationController = new LocationController(context);
    }

    private String generateUrl(){
        String weatherUrl = weatherUrlPart1+weatherUrlPart2Lat+locationController.getLatitude()+weatherUrlPart3Lon+locationController.getLongitude()+weatherUrlPart4AppId;
        return weatherUrl;
    }

    private void calculateCurrentWeather(int weatherId) {
        if (weatherId >= 200 && weatherId < 600) {
            currentWeather = Constants.WEATHER_RAINY;
        } else if (weatherId >= 600 && weatherId < 700) {
            currentWeather = Constants.WEATHER_CLOUDY;
        } else if (weatherId == 800) {
            currentWeather = Constants.WEATHER_SUNNY;
        } else {
            currentWeather = Constants.WEATHER_CLOUDY;
        }
    }



    /*
     Weather id -> 200 - kleiner 600: Regen
     600-kleiner 700: Schnee
     800 -> Sonne
     else -> Wolken
     falls ausgeschaltet: Sonne

     siehe:
     http://openweathermap.org/weather-conditions

     */

    public int getCurrentWeather(){
        return currentWeather;
    }



}
