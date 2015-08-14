package com.example.david.kingofthehill_app;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);


        _Datos = new JSONObject( );
        _Server = new Rest();
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
                            _Datos.put("password", MD5_Hash(_Password.getText().toString()));
                            _Datos.put("question", _Question.getText().toString());
                            _Datos.put("answer", _Answer.getText().toString());
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify _Server parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo para hash de contrasena
     * @param s
     * @return hash
     */
    public static String MD5_Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }
}
