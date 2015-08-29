package com.example.david.kingofthehill_app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Escuelas extends ActionBarActivity  {

    private SharedPref _Share = new SharedPref();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escuelas);
        Button mate=(Button)findViewById(R.id.button8);
        mate.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        _Share.editPref("Escuela", "Matematica", getApplicationContext());
                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                        finish();
                    }
                });
        Button compu=(Button)findViewById(R.id.button7);
        compu.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        _Share.editPref("Escuela", "Computacion", getApplicationContext());
                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                        finish();
                    }
                });
        Button fisica=(Button)findViewById(R.id.button9);
        fisica.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        _Share.editPref("Escuela", "Fisica", getApplicationContext());
                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                        finish();
                    }
                });
        Button fores=(Button)findViewById(R.id.button10);
        fores.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        _Share.editPref("Escuela", "Forestal", getApplicationContext());
                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                        finish();
                    }
                });
        Button electro=(Button)findViewById(R.id.button11);
        electro.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        _Share.editPref("Escuela", "Electromecanica", getApplicationContext());
                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                        finish();
                    }
                });
        Button produ=(Button)findViewById(R.id.button12);
        produ.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        _Share.editPref("Escuela", "Produccion Indus", getApplicationContext());
                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                        finish();
                    }
                });
        Button Diseno=(Button)findViewById(R.id.button13);
        Diseno.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        _Share.editPref("Escuela", "Disenio Indus", getApplicationContext());
                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                        finish();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_escuelas, menu);
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
