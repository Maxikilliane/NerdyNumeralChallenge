package de.mi.ur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import de.mi.ur.WeatherExtras.WeatherManager;

public class GameMainActivity extends Activity  implements View.OnClickListener{

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
                String weather = convertToWeatherName(weatherManager.getCurrentWeather());
                toastMessage = "Wetter aktualisiert! /nGerade "+weather+".";
                break;
            case R.id.game_highscore_button:
                break;
            case R.id.game_help_button:
                i = new Intent(GameMainActivity.this, GameHelpActivity.class);
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
}
