package de.mi.ur.WeatherExtras;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.mi.ur.Constants;

/**
 * Created by Anna-Marie on 11.08.2016.
 */
public class WeatherAsyncTask extends AsyncTask<String, Integer, String> {
    private WeatherListener listener;
    private int currentWeather;

    public WeatherAsyncTask(WeatherListener listener) {
        this.listener = listener;
    }

    //überarbeiten!
    @Override
    protected String doInBackground(String[] params) {
        String jsonString = "";

        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    jsonString += line;
                }
                br.close();
                is.close();
                conn.disconnect();
            } else {
                throw new IllegalStateException("HTTP response: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        int currentWeatherId = getWeatherIdFromJson(result);
        currentWeather = calculateCurrentWeather(currentWeatherId);
        listener.onDownloadFinished();
    }

    private int getWeatherIdFromJson(String text) {
        int weatherId = -1;
        try {
            JSONObject jsonObj = new JSONObject(text);
            JSONArray weatherArray = jsonObj.getJSONArray("weather");
            JSONObject weatherObj = weatherArray.getJSONObject(0);
            weatherId = weatherObj.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherId;
    }

    private int calculateCurrentWeather(int weatherId) {
        if (weatherId >= 200 && weatherId < 600) {
            return Constants.WEATHER_RAINY;
        } else if (weatherId >= 600 && weatherId < 700) {
            return Constants.WEATHER_CLOUDY;
        } else if (weatherId == 800) {
            return Constants.WEATHER_SUNNY;
        } else {
            return Constants.WEATHER_CLOUDY;
        }
    }

    public int getCurrentWeather(){
        return currentWeather;
    }


}
