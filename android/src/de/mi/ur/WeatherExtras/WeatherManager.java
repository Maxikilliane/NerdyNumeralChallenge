package de.mi.ur.WeatherExtras;

import android.app.Activity;
import android.content.Context;

import de.mi.ur.Constants;

/**
 * Created by Anna-Marie on 11.08.2016.
 * Diese Klasse wird derzeit nicht genutzt. Ist als private Klasse in die GameMainActivity ausgelagert.
 *
 */
public class WeatherManager implements WeatherListener {
    private int currentWeather;
    private String weatherUrlPart1 = "http://api.openweathermap.org/data/2.5/weather?";
    private String weatherUrlPart2Lat = "lat=";
    private String weatherUrlPart3Lon = "&lon=";
    private String weatherUrlPart4AppId = "&appid="+Constants.API_ID;
    private WeatherAsyncTask weatherTask;
    private LocationController locationController;
    public WeatherManager(Context context, Activity activity) {
        locationController = new LocationController(context, activity);
        locationController.setCurrentPosition();
    }

    private String generateUrl(){
        String weatherUrl = weatherUrlPart1+weatherUrlPart2Lat+locationController.getLatitude()+weatherUrlPart3Lon+locationController.getLongitude()+weatherUrlPart4AppId;
        return weatherUrl;
    }


    public void startCurrentWeatherGetter() {
        String Url = generateUrl();
        weatherTask = new WeatherAsyncTask(this);
        weatherTask.execute(Url);
    }

    public int getCurrentWeather() {
        return currentWeather;

    }


    @Override
    public void onDownloadFinished() {
        currentWeather = weatherTask.getCurrentWeather();
    }
}
