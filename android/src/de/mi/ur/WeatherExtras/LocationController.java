package de.mi.ur.WeatherExtras;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import de.mi.ur.Activities.GameMainActivity;

/**
 * Created by Anna-Marie on 11.08.2016.
 *
 * To-Do: GPS-Sensor verwenden! -> wenn kein Internet!
 *
 *
 *
 *
 */
public class LocationController implements LocationListener {
    private Context context;
    LocationManager locationManager;
    Location currentLocation;
    private Activity currentActivity;

    public LocationController(Context context, Activity activity) {
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        currentActivity = activity;
    }

    private String getBestProvider(){
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);

        return locationManager.getBestProvider(criteria, true);
    }

    //momentan provider immer null
    public void setCurrentPosition() {
        String provider = getBestProvider();
        if (provider == null) {
            provider = LocationManager.NETWORK_PROVIDER;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            currentLocation = locationManager.getLastKnownLocation(provider);
        }else{
            GameMainActivity.requestWeatherPermission(currentActivity);
        }

    }


    // Momentan immer null :(
    public String getLatitude(){
        if (currentLocation == null) {
            return "49";
        } else {
            double lat = currentLocation.getLatitude();
            return Double.toString(lat);
        }

    }

    public String getLongitude(){
        if (currentLocation == null) {
            return "12";
        } else {
            double lon = currentLocation.getLongitude();
            return Double.toString(lon);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
