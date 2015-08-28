package com.example.david.kingofthehill_app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


public class Maps extends FragmentActivity {

    private GoogleMap _Map;
    private Marker _Marker;
    private Rest _Server;
    private SharedPref _Share;
    private Button _Logout;
    private WifiManager _Wifi;
    private ImageView _Sig;
    private  MyLocationListener myLocationListener = new MyLocationListener();
    private boolean _Running=false;
    private LatLng _Focus=new LatLng(9.8560355, -83.9120774);
    private TextView _Score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        //Cofiguracion del mapa
        _Map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        _Marker = _Map.addMarker(new MarkerOptions().position(new LatLng(0,0)));
        CameraUpdate center= CameraUpdateFactory.newLatLng(_Focus);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(17);
        _Map.moveCamera(center);
        _Map.animateCamera(zoom);


        _Server= new Rest();
        _Share=new SharedPref();
        _Sig =(ImageView)findViewById(R.id.imageView);
        _Score= (TextView)findViewById(R.id.puntos);


        //Threads
        _Running=true;
        _WifiSignal.start();
        _Cuadros.start();
        _Posicion.start();
        _Check.start();
        _Puntos.start();




        _Logout = (Button) findViewById(R.id.button15);
        _Logout.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        _Running = false;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        _Server.sendToken(_Server.get_Logout(), _Share.getPref("_Token", getApplicationContext()));
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
        _Running=true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        myLocationListener.set_Ingame(false);
        _Running=false;

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

            while (_Running) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Codigo para mover posicion
                        //Cambiar y agregar url

                        String Result = _Server.sendToken(_Server.get_ActPos(), _Share.getPref("_Token", getApplicationContext()));
                        if (Result != null) {
                            try {
                                JSONObject _Coordenadas = new JSONObject(Result);
                                _Marker.setPosition(new LatLng(_Coordenadas.getDouble("lat"), _Coordenadas.getDouble("long")));
                                _Marker.setTitle(_Coordenadas.getString("username"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }));

    //Thread para colocar el puntaje
    private Thread _Puntos=(new Thread(new Runnable() {
        @Override
        public void run() {

            while (_Running) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String Result = _Server.sendToken(_Server.get_GetPoints(), _Share.getPref("_Token", getApplicationContext()));
                        JSONObject _Pointjs;
                        try {
                            _Pointjs = new JSONObject(Result);
                            if (Result != null) {
                                _Score.setText("Score: " + _Pointjs.getString("score"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }));
    private Thread _Cuadros=(new Thread(new Runnable() {
        @Override
        public void run() {
            while (_Running) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            String Result = _Server.sendToken(_Server.get_Zones(), _Share.getPref("_Token", getApplicationContext()));
                            if (Result != null) {
                                JSONObject Res = new JSONObject(Result);
                                JSONArray jsonArray = null;
                                jsonArray = Res.getJSONArray("zonas");
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
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

                try {
                    Thread.sleep(1000);
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
            while(_Running){
                try {
                    Thread.sleep(5000);
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
            while (_Running) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String Result=_Server.sendToken(_Server.get_Battle(), _Share.getPref("_Token", getApplicationContext()));
                if (Result!=null){
                    try {
                        JSONObject _Bas=new JSONObject(Result);
                        if (_Bas.getString("battle").equals("true")){
                            _Running=false;
                            Message m= new Message();
                            _Pelea.sendMessage(m);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Handler _Pelea = new Handler(){
        public void handleMessage(Message m){
            Intent intent = new Intent (Maps.this, Pelea_sable.class);
            startActivity(intent);
            finish();
        }
    };

    public void setLocation(Location location){
        if(location.getLatitude() != 0.0 && location.getLongitude() != 0.0){
            try{
                JSONObject _Pos= new JSONObject();
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