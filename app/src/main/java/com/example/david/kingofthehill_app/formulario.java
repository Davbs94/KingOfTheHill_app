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
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class formulario extends ActionBarActivity {
    private JSONObject _Datos;
    private Rest _Server;
    private EditText _User;
    private EditText _Password;
    private EditText _Question;
    private EditText _Answer;
    private Button _Register;
    private Clave _Clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);


        _Datos = new JSONObject( );
        _Server = new Rest();
        _Clave = new Clave();
        _User = (EditText)findViewById(R.id.editText3);
        _Password = (EditText)findViewById(R.id.editText4);
        _Question = (EditText)findViewById(R.id.editText5);
        _Answer = (EditText)findViewById(R.id.editText6);


        _Register = (Button)findViewById(R.id.button3);
        _Register.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                            try {
                                _Datos.put("username", _User.getText().toString());
                                _Datos.put("password", _Clave.MD5_Hash(_Password.getText().toString()));
                                _Datos.put("question", _Question.getText().toString());
                                _Datos.put("answer", _Clave.MD5_Hash(_Answer.getText().toString()));
                                _Server.postContent(_Server.get_Registrar(), _Datos);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Intent myIntent = new Intent(v.getContext(), Register.class);
                            startActivity(myIntent);
                            finish();


                    }
                }
        );



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
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
