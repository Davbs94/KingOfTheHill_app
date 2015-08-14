package com.example.david.kingofthehill_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;




public class Game extends ActionBarActivity {
    //private TextView Token;
    private Rest _Server = new Rest();
    private MylocationListener myLocationListener;
    private JSONObject _Coor = new JSONObject();
    private TextView S;
    private LocationManager mlocManager;
    private Runnable run;
    private Thread hilo;
    private WifiManager wifi;
    private Handler handler = new Handler();
    private SharedPref Share= new SharedPref();


    static final LatLng TutorialsPoint = new LatLng(21 , 57);
    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }








/*

        Button Logout = (Button) findViewById(R.id.button5);
        Logout.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {


                        Server2.logout(getPref("Token", getApplicationContext())); _Logout
                        mlocManager.removeUpdates(myLocationListener);

                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                        finish();


                    }
                }
        );
        */
       //wifi();


    /**
         run = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    S.setText(Server3.logout(getPref("Token", getApplicationContext()))); //_Battle
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hilo= new Thread(run);
        hilo.start();
     **/

    /*
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocationListener = new MylocationListener();
        myLocationListener.setMainActivity(this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) myLocationListener);
        */
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    public void onStop() {
        /** JSONObject logout = new JSONObject();
        try {
            Server2.postContentUser(logout, getPref("Token", getApplicationContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         **/
        mlocManager.removeUpdates(myLocationListener);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String getPref(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public void setLocation(Location location) {
        if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
            try {
                _Coor.put("username", getPref("User", getApplicationContext()));
                _Coor.put("lat", Double.toString(location.getLatitude()));
                _Coor.put("long", Double.toString(location.getLongitude()));
                _Server.postContentUser(_Server.get_SendPos(), _Coor, getPref("Token", getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void onReceive(Context context, Intent intent) {
        List<ScanResult> results = wifi.getScanResults();
        ScanResult bestSignal = null;
        for (ScanResult result : results) {
            if (bestSignal == null
                    || WifiManager.compareSignalLevel(bestSignal.level, result.level) < 0)
                bestSignal = result;
        }

        String message = String.format("%s networks found. %s is the strongest.",
                results.size(), bestSignal.SSID);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        Log.d("Debug", "onReceive() message: " + message);
    }





}




