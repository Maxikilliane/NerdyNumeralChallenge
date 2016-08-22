package de.mi.ur.WeatherExtras;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Anna-Marie on 11.08.2016.
 *
 * To-Do: GPS-Sensor verwenden! -> wenn kein Internet!
 */
public class LocationController extends Activity {
    private Context context;
    LocationManager locationManager;
    Location currentLocation;

    public LocationController(Context context){
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
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

    // evtl noch ne SupportLibrary einbinden
   /*public void setCurrentPosition(){
        String provider = getBestProvider();
        int permissionCheck = ContextCompat.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            currentLocation = locationManager.getLastKnownLocation(provider);
        }else{
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission_group.LOCATION});
        }

    }
    */

    public String getLatitude(){
        //double lat =  currentLocation.getLatitude();
        //return Double.toString(lat);
        return "12";

    }

    public String getLongitude(){
        //double lon =  currentLocation.getLongitude();
        //return Double.toString(lon);
        return "30";
    }
}
