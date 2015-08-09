package com.example.david.kingofthehill_app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import static com.example.david.kingofthehill_app.R.raw.*;


public class Pelea_sable extends ActionBarActivity implements SensorEventListener,SensorListener {

    private long last_update = 0, last_movement = 0,game_timer = 0;
    private float prevX = 0, prevY = 0, prevZ = 0;
    private float curX = 0, curY = 0, curZ = 0;
    private MediaPlayer reproductor;
    private  float TotalMovement = 0;
    private float TotalMovementAux = 0;
    private Random rand = new Random();

    TextView x,y,z;

    private  Sensor mAccelerometer;
    SensorManager sm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelea_sable);
        x = (TextView)findViewById(R.id.posx);
        y = (TextView)findViewById(R.id.posy);
        z = (TextView)findViewById(R.id.posz);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensors.size() > 0){//valida que exista un sensor de tipo acelerometro
            mAccelerometer = sensors.get(0);
            sm.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_GAME);
        }
        reproductor = MediaPlayer.create(this, R.raw.inicio);
        if(!reproductor.isPlaying()){
            reproductor.start();
        }
    }

    protected void onResume(){
        super.onResume();

    }

    protected void onPause(){
        sm.unregisterListener(this, mAccelerometer);
        reproductor = MediaPlayer.create(this, R.raw.fin);
        if(!reproductor.isPlaying()){
            reproductor.start();
        }

        super.onPause();
    }

    protected void onStop(){
        sm.unregisterListener(this, mAccelerometer);
        reproductor = MediaPlayer.create(this, R.raw.fin);
        if(!reproductor.isPlaying()){
            reproductor.start();
        }
        super.onStop();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pelea_sable, menu);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        long current_time = event.timestamp;

        curX = event.values[0];
        curY = event.values[1];
        curZ = event.values[2];

        if (prevX == 0 && prevY == 0 && prevZ == 0) {
            last_update = current_time;
            last_movement = current_time;
            prevX = curX;
            prevY = curY;
            prevZ = curZ;
        }


        long time_difference = current_time - last_update;
        if (time_difference > 250000){
            //this.x.setText("diferencia: "+Long.toString(time_difference));
            this.y.setText("timer: "+Long.toString(game_timer));
            game_timer += 250;
            float movement = Math.abs((curX + curY + curZ) - (prevX - prevY - prevZ)) / time_difference;
            TotalMovement += movement;
            if(TotalMovementAux==0 ) {
                TotalMovementAux=TotalMovement;
            }
            int limit = 250000;
            float min_movement = 2E-6f;
            if (movement > min_movement) {
                if (current_time - last_movement >= limit) {
                    //Toast.makeText(getApplicationContext(), "Hay movimiento de " + movement, Toast.LENGTH_SHORT).show();
                    this.x.setText("puntaje: "+Float.toString(TotalMovement / 1E-5f));
                    if((TotalMovement - TotalMovementAux) > 2.5E-5f) {
                        TotalMovementAux = TotalMovement;
                        int val = rand.nextInt(5);
                        if(val == 0){

                            reproductor = MediaPlayer.create(this, R.raw.sable1);
                            if(!reproductor.isPlaying()){
                                reproductor.start();
                            }
                        }
                        if(val==1){
                            reproductor = MediaPlayer.create(this, R.raw.sable2);
                            if(!reproductor.isPlaying()){
                                reproductor.start();
                            }
                        }
                        if(val == 2){
                            reproductor = MediaPlayer.create(this, R.raw.sable3);
                            if(!reproductor.isPlaying()){
                                reproductor.start();
                            }}
                        if(val==3){
                            reproductor = MediaPlayer.create(this, R.raw.sable4);
                            if(!reproductor.isPlaying()){
                                reproductor.start();
                            }}
                        if(val==4){
                            reproductor = MediaPlayer.create(this, R.raw.sable5);
                            if(!reproductor.isPlaying()){
                                reproductor.start();

                            }}


                    }
                }
                last_movement = current_time;
            }
            prevX = curX;
            prevY = curY;
            prevZ = curZ;
            last_update = current_time;

            //this.x.setText("X = "+ curX);
            //this.y.setText("Y = "+ curY);
            //this.z.setText("Z = "+ curZ);
        }


        //long game_time = current_time - first_movement;
        if(game_timer>=100000) {
            last_update = 0;
            last_movement = 0;
            game_timer = 0;
            prevX = 0;
            prevY = 0;
            prevZ = 0;
            curX = 0;
            curY = 0;
            curZ = 0;

            this.x.setText("finalizo la pelea");
            this.z.setText("maximo puntaje: " + Float.toString(TotalMovement / 1E-5f));

            reproductor = MediaPlayer.create(this, R.raw.fin);
            if(!reproductor.isPlaying()){
                reproductor.start();
            }

            TotalMovement = 0;
            TotalMovementAux = 0;

            sm.unregisterListener(this, mAccelerometer);
            Intent myIntent = new Intent(Pelea_sable.this, Game.class);
            startActivity(myIntent);
            finish();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {

    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
