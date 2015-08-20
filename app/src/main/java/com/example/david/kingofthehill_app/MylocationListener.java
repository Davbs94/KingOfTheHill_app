package com.example.david.kingofthehill_app;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;

/**
 *
 * Created by RafaelAngel on 04/08/2015.
 */
class MyLocationListener implements LocationListener {
    Maps mainActivity;
    boolean ingame=false;


    public Maps getMainActivity() {
        return mainActivity;
    }
    public void setMainActivity(Maps mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onLocationChanged(Location location) {
        String Text = "Ubicacion actual: "+ "\n lat "+location.getLatitude() + "\n long "+location.getLongitude();

        if (ingame) {
            this.mainActivity.setLocation(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //mainActivity.messageTextView.setText("GPS desactivado");
    }

    @Override
    public void onProviderEnabled(String provider) {
        //mainActivity.messageTextView.setText("GPS activado");
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void set_Ingame(boolean pGame){
        ingame=pGame;
    }
}
