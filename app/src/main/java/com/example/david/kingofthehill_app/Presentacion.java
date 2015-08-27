package com.example.david.kingofthehill_app;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Presentacion extends ActionBarActivity {
    private Boolean _Running=true;
    private AnimationDrawable anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);


        ImageView _Image=(ImageView)findViewById(R.id.imageView2);
        _Image.setBackgroundResource(R.drawable.pres);
        anim = (AnimationDrawable) _Image.getBackground();
        _Anim.start();
       // _Change.start();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_presentacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Thread _Change=(new Thread(new Runnable() {
        @Override
        public void run() {


                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(7000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }



                        }
                    });
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


        }
    }));
    private Thread _Anim=(new Thread(new Runnable() {
        @Override
        public void run() {


            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        anim.start();

                    }
                });
                Thread.sleep(8500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent myIntent = new Intent(Presentacion.this, Register.class);
            startActivity(myIntent);
            finish();



        }
    }));
}
