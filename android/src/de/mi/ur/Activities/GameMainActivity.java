package de.mi.ur.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import de.mi.ur.AndroidCommunication.WeatherDataListener;
import de.mi.ur.AndroidLauncher;
import de.mi.ur.Constants;
import de.mi.ur.R;
import de.mi.ur.WeatherExtras.WeatherManager;

public class GameMainActivity extends AppCompatActivity implements View.OnClickListener, WeatherDataListener {

    private Button buttonStartGame;
    private Button buttonWeather;
    private Button buttonViewHighscore;
    private Button buttonHelp;


    private WeatherManager weatherManager;

    private Toolbar myToolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main_activity);
        setupUI();
        setupToolbar();
        weatherManager = new WeatherManager(this, this);
    }

    private void setupToolbar() {
        myToolbar = (Toolbar) findViewById(R.id.game_main_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.game_main_toolbar_headline);
       // myToolbar.setNavigationIcon(R.drawable.toolbar_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                i.putExtra(Constants.CURRENT_WEATHER, weatherManager.getCurrentWeather());
                break;
            case R.id.game_update_weather_button:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                String weather = convertToWeatherName(weatherManager.getCurrentWeather());
                    toastMessage = "Wetter aktualisiert! Gerade " + weather + ". Der Spielhintergrund wurde angepasst.";
                } else {
                    requestWeatherPermission(this);
                    toastMessage = "Default-Wetter: Die Sonne scheint!";
                }
                break;
            case R.id.game_highscore_button:
                i = new Intent(GameMainActivity.this, HighscoreActivity.class);
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
        if (toastMessage != null) {
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();

        }

    }

    private String convertToWeatherName(int weatherNumber) {

        switch (weatherNumber) {
            case Constants.WEATHER_SUNNY:
                return "scheint die Sonne";
            case Constants.WEATHER_CLOUDY:
                return "ist es wolkig";
            case Constants.WEATHER_RAINY:
                return "regnet es";
            case Constants.WEATHER_SNOWY:
                return "schneit es";
            default:
                return "ist es wolkig";
        }
    }

    @Override
    public int getCurrentWeather() {
        return weatherManager.getCurrentWeather();
    }

    public static void requestWeatherPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            String toastMessage = activity.getResources().getString(R.string.location_permission_explanation);
            Toast.makeText(activity, toastMessage, Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION);

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSION_REQUEST_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    weatherManager.getCurrentWeather();
                }
                break;
            }
        }
    }



}

