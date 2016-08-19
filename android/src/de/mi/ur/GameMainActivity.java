package de.mi.ur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import de.mi.ur.WeatherExtras.LocationController;
import de.mi.ur.WeatherExtras.WeatherAsyncTask;
import de.mi.ur.WeatherExtras.WeatherListener;
import de.mi.ur.WeatherExtras.WeatherManager;

public class GameMainActivity extends Activity  implements View.OnClickListener {

    private Button buttonStartGame;
    private Button buttonWeather;
    private Button buttonViewHighscore;
    private Button buttonHelp;

    private WeatherManager weatherManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main_activity);
        setupUI();
        weatherManager = new WeatherManager(this);
    }

    private void setupUI() {
        buttonStartGame = (Button) findViewById(R.id.game_start_button);
        buttonStartGame.setOnClickListener(this);
        buttonWeather = (Button) findViewById(R.id.game_update_weather_button);
        buttonWeather.setOnClickListener(this);
        buttonViewHighscore = (Button) findViewById (R.id.game_highscore_button);
        buttonViewHighscore.setOnClickListener(this);
        buttonHelp = (Button) findViewById (R.id.game_help_button);
        buttonHelp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        String toastMessage = null;
        switch (v.getId()){
            case R.id.game_start_button:
                i = new Intent(GameMainActivity.this, AndroidLauncher.class);
                break;
            case R.id.game_update_weather_button:
                weatherManager.weatherTask.execute(weatherManager.generateUrl());
                weatherManager.calculateCurrentWeather(weatherManager.weatherTask.getWeatherId());
                String weather = convertToWeatherName(weatherManager.getCurrentWeather());
                toastMessage = "Wetter aktualisiert! Gerade "+weather+".";
                break;
            case R.id.game_highscore_button:
                break;
            case R.id.game_help_button:
                i= new Intent (GameMainActivity.this, GameHelpActivity.class);
                break;
            default:
                break;
        }
        if(i!=null){
            startActivity(i);
        }
        if(toastMessage!=null){
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
        }

    }

    private String convertToWeatherName(int weatherNumber){

        switch(weatherNumber){
            case Constants.WEATHER_SUNNY:
                return "scheint die Sonne";
            case Constants.WEATHER_CLOUDY:
                return "ist es wolkig";
            case  Constants.WEATHER_RAINY:
                return "regnet es";
            case Constants.WEATHER_SNOWY:
                return "schneit es";
            default: return "ist es wolkig";
        }
    }

    //Listener nicht verwendet momentan
    private class WeatherManager implements WeatherListener{

        private int currentWeather;
        private String weatherUrlPart1 = "http://api.openweathermap.org/data/2.5/weather?";
        private String weatherUrlPart2Lat = "lat=";
        private String weatherUrlPart3Lon = "&lon=";
        private String weatherUrlPart4AppId = "&appid="+Constants.API_ID;
        private WeatherAsyncTask weatherTask;
        private LocationController locationController;

        public WeatherManager(Context context){
            locationController = new LocationController(context);
            weatherTask = new WeatherAsyncTask(this);
        }

        private String generateUrl(){
            String weatherUrl = weatherUrlPart1+weatherUrlPart2Lat+locationController.getLatitude()+weatherUrlPart3Lon+locationController.getLongitude()+weatherUrlPart4AppId;
            return weatherUrl;
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
        private void calculateCurrentWeather(int weatherId){
            if(weatherId >= 200 && weatherId<600){
                currentWeather = Constants.WEATHER_RAINY;
            }else if(weatherId >=600 && weatherId < 700){
                currentWeather = Constants.WEATHER_CLOUDY;
            }else if(weatherId == 800){
                currentWeather = Constants.WEATHER_SUNNY;
            }else{
                currentWeather = Constants.WEATHER_CLOUDY;
            }
        }

        public int getCurrentWeather(){
            return currentWeather;
        }


        @Override
        public void onDownloadFinished() {

        }
    }
}
