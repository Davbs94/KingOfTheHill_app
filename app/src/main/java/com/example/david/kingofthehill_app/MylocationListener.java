package com.example.david.kingofthehill_app;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by David on 06/08/2015.
 */
public class MylocationListener implements LocationListener {
    Game mainActivity;
    private double last_latitude = 0;
    private double last_longitude = 0;
    private boolean first_update = true;


    public Game getMainActivity() {
        return mainActivity;
    }
    public void setMainActivity(Game mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onLocationChanged(Location location) {
            this.mainActivity.setLocation(location);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
       // mainActivity.messageTextView.setText("GPS desactivado");
    }

    @Override
    public void onProviderEnabled(String provider) {
        //mainActivity.messageTextView.setText("GPS activado");
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
