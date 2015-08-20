package com.example.david.kingofthehill_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Maps extends FragmentActivity {

    private GoogleMap _Map; // Might be null if Google Play services APK is not available.
    private Marker _Marker;
    private Rest _Server;
    private SharedPref _Share;
    private Button _Logout;
    private WifiManager _Wifi;
    private ImageView _Sig;
    private TextView t;
    private JSONObject _Coordenadas= new JSONObject();
    private  MyLocationListener myLocationListener = new MyLocationListener();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        t=(TextView)findViewById(R.id.textView11);
        _Map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
       // _Marker = _Map.addMarker(new MarkerOptions().position(new LatLng(9.313801,-83.663850)));
        _Server= new Rest();
        _Share=new SharedPref();
        _Sig =(ImageView)findViewById(R.id.imageView);

        _WifiSignal.start();

        _Cuadros.start();
        _Posicion.start();




        _Logout = (Button) findViewById(R.id.button15);
        _Logout.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        JSONObject J = new JSONObject();
                        _Server.sendToken(_Server.get_ActPos(), _Share.getPref("_Token", getApplicationContext()));
                        myLocationListener.set_Ingame(false);


                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                        finish();


                    }
                }
        );







        LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        myLocationListener.setMainActivity(this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) myLocationListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        myLocationListener.set_Ingame(true);

    }

    @Override
    protected void onPause() {
        super.onPause();
        myLocationListener.set_Ingame(false);
    }


    private void setUpMapIfNeeded() {
        if (_Map == null) {
            _Map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (_Map != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
    }


    /**
     * Thread para mover _Marker de google maps
     */
    private Thread _Posicion=(new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Codigo para mover posicion
                            try {
                                //Cambiar y agregar url
                                _Coordenadas = new JSONObject(_Server.sendToken(_Server.get_ActPos(), _Share.getPref("Token", getApplicationContext())));
                                _Marker.setPosition(new LatLng(_Coordenadas.getDouble("lat"), _Coordenadas.getDouble("long")));
                                _Marker.setTitle(_Coordenadas.getString("username"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }));
    private Thread _Cuadros=(new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String Result = _Server.sendToken(_Server.get_Zones(), _Share.getPref("_Token", getApplicationContext()));
                                JSONObject Res = new JSONObject(Result);
                                JSONArray jsonArray = null;
                                jsonArray = Res.getJSONArray("zonas");


                                //t.setText(json.get("lat2").toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject json = jsonArray.getJSONObject(i);
                                    PolygonOptions b = new PolygonOptions().add(new LatLng(json.getDouble("lat1"), json.getDouble("long1"))
                                            , new LatLng(json.getDouble("lat1"), json.getDouble("long2"))
                                            , new LatLng(json.getDouble("lat2"), json.getDouble("long2"))
                                            , new LatLng(json.getDouble("lat2"), json.getDouble("long1"))).strokeColor(Color.parseColor(json.getString("color")))
                                            .strokeWidth(1);
                                    Polygon a = _Map.addPolygon(b);
                                    a.setStrokeWidth(1);
                                    a.setFillColor(Color.parseColor(json.getString("color")));

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }));

    /**
     * Thread para senal _Wifi
     */
    private Thread _WifiSignal =(new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(2000);
                    _Wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    WifiInfo info = _Wifi.getConnectionInfo();
                    Message msg = _Handler.obtainMessage();
                    int signal=calculateSignalLevel(info.getRssi(),100);
                    if (signal==0){
                        msg.obj="a";
                    }
                    else if(signal>=1&&signal<=20){
                        msg.obj="b";
                    }
                    else if(signal>=1&&signal<=20){
                        msg.obj="b";
                    }
                    else if(signal>=21&&signal<=40){
                        msg.obj="c";
                    }
                    else if(signal>=41&&signal<=60){
                        msg.obj="d";
                    }
                    else if(signal>=61&&signal<=80){
                        msg.obj="e";
                    }
                    else if(signal>=81&&signal<=100){
                        msg.obj="f";
                    }
                    _Handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }));

    /**
     * Handler para manejar el cambio de imagenes de la senal _Wifi
     */
    final Handler _Handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj.toString().equals("a")){
                _Sig.setImageResource(R.drawable.a);
            }
            else if(msg.obj.toString().equals("b")){
                _Sig.setImageResource(R.drawable.b);
            }
            else if(msg.obj.toString().equals("c")){
                _Sig.setImageResource(R.drawable.c);
            }
            else if(msg.obj.toString().equals("d")){
                _Sig.setImageResource(R.drawable.d);
            }
            else if(msg.obj.toString().equals("e")){
                _Sig.setImageResource(R.drawable.e);
            }
            else if(msg.obj.toString().equals("f")){
                _Sig.setImageResource(R.drawable.f);
            }
        }
    };

    /**
     * Calcula el porcentaje de senal _Wifi
     * @param rssi
     * @param numLevels
     * @return
     */
    public int calculateSignalLevel(int rssi, int numLevels) {
        if(rssi <= -100) {
            return 0;
        } else if(rssi >= -10) {
            return numLevels - 1;
        } else {
            float inputRange = (-10- -100);
            float outputRange = (numLevels - 1);
            if(inputRange != 0)
                return (int) ((float) (rssi - -100) * outputRange / inputRange);
        }
        return 0;
    }


    private Thread _Check= new Thread(new Runnable() {
        public void run() {
            while (true) {
                try {
                    _Server.postContentUser(_Server.get_Battle(), null, _Share.getPref("Token", getApplicationContext()));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    public void setLocation(Location location){
        if(location.getLatitude() != 0.0 && location.getLongitude() != 0.0){
            try{
                JSONObject _Pos= new JSONObject();
                System.out.print(location.getLatitude());
                System.out.print(location.getLongitude());
                _Pos.put("lat", location.getLatitude());
                _Pos.put("long",location.getLongitude());
                _Pos.put("username", _Share.getPref("_User", getApplicationContext()));
                _Server.postContentUser(_Server.get_SendPos(), _Pos, _Share.getPref("_Token", getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}