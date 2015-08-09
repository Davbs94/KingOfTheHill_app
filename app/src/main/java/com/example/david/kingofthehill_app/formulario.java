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
    private JSONObject gson= new JSONObject( );
    private Rest a= new Rest("http://192.168.56.1:8080/KingOfTheHill/webresources/users/register");
    private EditText User;
    private EditText Password;
    private EditText Question;
    private EditText Answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_formulario);

        User = (EditText)findViewById(R.id.editText3);
        Password = (EditText)findViewById(R.id.editText4);
        Question = (EditText)findViewById(R.id.editText5);
        Answer = (EditText)findViewById(R.id.editText6);


        Button Register= (Button)findViewById(R.id.button3);
        Register.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        try {
                            gson.put("username",User.getText().toString());
                            gson.put("password",MD5_Hash(Password.getText().toString()));
                            gson.put("question",Question.getText().toString());
                            gson.put("answer",Answer.getText().toString());
                            a.postContent(gson);
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
        // as you specify a parent activity in AndroidManifest.xml.
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
