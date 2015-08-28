package com.example.david.kingofthehill_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.ActionBarActivity;


public class Register extends ActionBarActivity {
    private SharedPref _Share=new SharedPref();
    private Button _Register;
    private Button _Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        _Share.putPref("Escuela", "Select School", getApplicationContext());
        _Share.putPref("_User", "", getApplicationContext());
        _Share.putPref("_Pass", "", getApplicationContext());
        _Share.putPref("_Token", "", getApplicationContext());
        _Register = (Button)findViewById(R.id.button2);
        _Login = (Button)findViewById(R.id.button1);




        _Register.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(v.getContext(), formulario.class);
                        startActivity(myIntent);
                        finish();
                    }
                }
        );
        _Login.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(v.getContext(), Login.class);
                        startActivity(myIntent);
                        finish();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
}
