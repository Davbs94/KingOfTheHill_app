package com.example.david.kingofthehill_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.HttpResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.entity.StringEntity;
import android.content.SharedPreferences.Editor;



@SuppressWarnings( "deprecation" )

public class Login extends ActionBarActivity {
    private EditText User;
    private EditText Password;
    private EditText School;
    private TextView Test;
    private Rest Server= new Rest("http://192.168.56.1:8080/KingOfTheHill/webresources/users/login");
    private JSONObject Json=new JSONObject();
    private JSONObject Token;
    private Clave Hash;
    public SharedPref Share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        User= (EditText)findViewById(R.id.editText);
        Password=(EditText)findViewById(R.id.editText2);
        //School=(EditText)findViewById(R.id.editText10);
        Test = (TextView)findViewById(R.id.textView2);
        Button Login=(Button)findViewById(R.id.button);
        Share=new SharedPref();

        User.setText(Share.getPref("User",getApplicationContext()));
        //School.setText(Share.getPref("Escuela",getApplicationContext()));
        Button escuelas=(Button)findViewById(R.id.button14);
        escuelas.setText(Share.getPref("Escuela",getApplicationContext()));
        escuelas.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Share.editPref("User",User.getText().toString(),getApplicationContext());
                        Intent myIntent = new Intent(v.getContext(), Escuelas.class);
                        startActivity(myIntent);
                        finish();
                    }
                });

        Login.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Share.editPref("User",User.getText().toString(),getApplicationContext());
                        if (Share.getPref("Escuela", getApplicationContext()).equals("Select School")){
                            new AlertDialog.Builder(Login.this)
                                    .setTitle("Error!")
                                    .setMessage("No school selected")
                                    .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            User.setText(Share.getPref("User",getApplicationContext()));
                                            Password.setText("");
                                            //School.setText("");
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        else {


                            try {
                                Hash = new Clave(Password.getText().toString());
                                Json.put("username", User.getText().toString());
                                Json.put("password", Hash.MD5_Hash());
                                Json.put("school", Share.getPref("Escuela", getApplicationContext()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {

                                String Result = Server.postContent(Json);

                                if (Result == null) {
                                    new AlertDialog.Builder(Login.this)
                                            .setTitle("Error!")
                                            .setMessage("Invalid username or password")
                                            .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    User.setText("");
                                                    Password.setText("");

                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                } else {
                                    // guardado del token
                                    Token = new JSONObject(Result);
                                    Share.editPref("Token", Token.getString("access_token"), getApplicationContext());
                                    Share.editPref("User", Json.getString("username"), getApplicationContext());
                                    Intent myIntent = new Intent(v.getContext(), Game.class);
                                    startActivity(myIntent);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        Button Forgot=(Button)findViewById(R.id.button6);
        Forgot.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(v.getContext(), forgot_password.class);
                        startActivity(myIntent);
                    }
                });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
