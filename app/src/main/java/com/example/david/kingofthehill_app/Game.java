package com.example.david.kingofthehill_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Game extends ActionBarActivity {
    private TextView Token;
    private Rest Server = new Rest("http://192.168.56.1:8080/KingOfTheHill/webresources/mobile/send-position");
    private Rest Server3 = new Rest("http://192.168.56.1:8080/KingOfTheHill/webresources/mobile/checkBattle");
    private Rest Server2 = new Rest("http://192.168.56.1:8080/KingOfTheHill/webresources/users/logout");
    private MylocationListener myLocationListener;
    private JSONObject coor = new JSONObject();
    private TextView S;
    private LocationManager mlocManager;
    private Runnable run;
    private Thread hilo;
    private WifiManager wifi;
    private Handler handler = new Handler();
    private SharedPref Share= new SharedPref();
    WebView mWebView;
    JavascriptInterface JSInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        S = (TextView) findViewById(R.id.textView10);
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl("http://192.168.1.135:8080/KingOfTheHill/");






        Button Logout = (Button) findViewById(R.id.button5);
        Logout.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {


                        Server2.logout(getPref("Token", getApplicationContext()));
                        mlocManager.removeUpdates(myLocationListener);

                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                        finish();


                    }
                }
        );
        S.setText(Share.getPref("Token",getApplicationContext()));
       //wifi();


    /**
         run = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    S.setText(Server3.logout(getPref("Token", getApplicationContext())));
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


        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocationListener = new MylocationListener();
        myLocationListener.setMainActivity(this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) myLocationListener);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    public void onStop() {
        JSONObject logout = new JSONObject();
        try {
            Server2.postContentUser(logout, getPref("Token", getApplicationContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                coor.put("username", getPref("User", getApplicationContext()));
                coor.put("lat", Double.toString(location.getLatitude()));
                coor.put("long", Double.toString(location.getLongitude()));
                Server.postContentUser(coor, getPref("Token", getApplicationContext()));
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

    private void wifi() {

        Runnable runnable = new Runnable() {

            WifiInfo info = wifi.getConnectionInfo();
            public void run() {

                    handler.post(new Runnable(){
                        public void run() {
                            while (true) {
                                try {
                                    Thread.sleep(3000);
                                    wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                    S.setText(info.getSSID() + "  " + WifiManager.calculateSignalLevel(info.getRssi(), 100) + "%");
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });

            }
        };
        new Thread(runnable).start();
    }

    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}




