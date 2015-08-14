package com.example.david.kingofthehill_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.ActionBarActivity;


public class Register extends ActionBarActivity {
    private SharedPref Share=new SharedPref();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Share.putPref("Escuela", "Select School", getApplicationContext());
        Share.putPref("User","",getApplicationContext());
        Share.putPref("Token","",getApplicationContext());
        Button Register= (Button)findViewById(R.id.button2);
        Button Login= (Button)findViewById(R.id.button1);
        Register.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(v.getContext(), formulario.class);
                        startActivity(myIntent);
                    }
                }
        );
        Login.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
