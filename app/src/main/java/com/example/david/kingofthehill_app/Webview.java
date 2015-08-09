package com.example.david.kingofthehill_app;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;


public class Webview extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //SharedPreferences Pref = getPreferences(MODE_PRIVATE);
        String Result ="hola"; //Pref.getString("Token","");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //WebView webview = (WebView) findViewById(R.id.webView);
        //webview.loadUrl("http://192.168.1.135:8080/KingOfTheHill/");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
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
}
