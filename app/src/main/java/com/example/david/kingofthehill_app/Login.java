package com.example.david.kingofthehill_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;


@SuppressWarnings( "deprecation" )

public class Login extends ActionBarActivity {
    private EditText _User;
    private EditText _Password;


    private Rest _Server;
    private JSONObject _Datos;
    private JSONObject _Token;
    private Clave _Hash;
    private SharedPref _Share;
    private Button _Login;
    private Button _Escuelas;
    private Button _Forgot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _User = (EditText)findViewById(R.id.editText);
        _Password =(EditText)findViewById(R.id.editText2);

        _Login =(Button)findViewById(R.id.button);
        _Share =new SharedPref();
        _Datos =new JSONObject();
        _Server = new Rest();
        _Hash= new Clave();
        _User.setText(SharedPref.getPref("_User", getApplicationContext()));

        _Escuelas =(Button)findViewById(R.id.button14);
        _Escuelas.setText(SharedPref.getPref("Escuela", getApplicationContext()));
        _Escuelas.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        SharedPref.editPref("_User", _User.getText().toString(), getApplicationContext());
                        Intent myIntent = new Intent(v.getContext(), Escuelas.class);
                        startActivity(myIntent);
                        finish();
                    }
                });

        _Login.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        _Share.editPref("_User", _User.getText().toString(), getApplicationContext());
                        if (_Share.getPref("Escuela", getApplicationContext()).equals("Select School")) {
                            new AlertDialog.Builder(Login.this)
                                    .setTitle("Error!")
                                    .setMessage("No school selected")
                                    .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            _User.setText(SharedPref.getPref("_User", getApplicationContext()));
                                            _Password.setText("");
                                            //School.setText("");
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        } else {


                            try {
                                _Datos.put("username", _User.getText().toString());
                                _Datos.put("password", _Hash.MD5_Hash(_Password.getText().toString()));
                                _Datos.put("school", SharedPref.getPref("Escuela", getApplicationContext()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {

                                String Result = _Server.postContent(_Server.get_Login(), _Datos);

                                if (Result == null) {
                                    new AlertDialog.Builder(Login.this)
                                            .setTitle("Error!")
                                            .setMessage("Invalid username or password")
                                            .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    _User.setText("");
                                                    _Password.setText("");

                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                } else {
                                    // guardado del token
                                    _Token = new JSONObject(Result);
                                    _Share.editPref("_Token", _Token.getString("access_token"), getApplicationContext());
                                    _Share.editPref("_User", _Datos.getString("username"), getApplicationContext());
                                    Thread.sleep(500);
                                    Intent myIntent = new Intent(v.getContext(), Maps.class);
                                    startActivity(myIntent);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        _Forgot =(Button)findViewById(R.id.button6);
        _Forgot.setOnClickListener(
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
