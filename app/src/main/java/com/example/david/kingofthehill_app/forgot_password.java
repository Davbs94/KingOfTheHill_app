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
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class forgot_password extends ActionBarActivity {

    private TextView _Question;
    private EditText _Answer;
    private Rest _Server;
    private Button _Send;
    private User _Usuario;
    private JSONObject _Quest;
    private JSONObject _Ans;
    private Clave _Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        _Question =(TextView)findViewById(R.id.textView8);
        _Answer =(EditText)findViewById(R.id.editText7);
        _Server =new Rest();
        _Usuario = new User();
        _Ans = new JSONObject();
        _Pass= new Clave();
        _Quest = new JSONObject();
        _Question.setText("User");
        _Send = (Button)findViewById(R.id.button4);
        _Send.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        if (_Question.getText().equals("User")) {
                            try {
                                _Usuario.setUsername(_Answer.getText().toString());
                                _Quest.put("username", _Usuario.getUsername());
                                _Quest.put("question", "true");
                                _Quest.put("answwer", "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                String response = _Server.postContent(_Server.get_Forgot(), _Quest);
                                if (response == null) {
                                    new AlertDialog.Builder(forgot_password.this)
                                            .setTitle("Error")
                                            .setMessage("No username found")
                                            .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    _Answer.setText("");
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                    _Answer.setText("");
                                } else {
                                    _Question.setText(response);
                                    _Answer.setText("");
                                    _Usuario.setQuestion(response);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        } else if (_Question.getText().equals("Password")) {
                            try {
                                _Ans.put("username", _Usuario.getUsername());
                                _Ans.put("password", _Pass.MD5_Hash(_Answer.getText().toString()));
                                _Ans.put("answer", _Usuario.getAnswer());
                                String response = _Server.postContent(_Server.get_NewPass(), _Ans);
                                if (response == null) {
                                    new AlertDialog.Builder(forgot_password.this)
                                            .setTitle("Error")
                                            .setMessage("Incorrect answer")
                                            .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    _Answer.setText("");
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                    _Question.setText(_Usuario.getQuestion());
                                    _Answer.setText("");
                                } else {
                                    new AlertDialog.Builder(forgot_password.this)
                                            .setTitle("Success")
                                            .setMessage("Password changed")
                                            .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    _Answer.setText("");

                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                    Intent myIntent = new Intent(v.getContext(), Login.class);
                                    startActivity(myIntent);
                                    finish();


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            _Usuario.setAnswer(_Answer.getText().toString());
                            _Question.setText("Password");
                            _Answer.setText("");
                        }

                        //Intent myIntent = new Intent(v.getContext(), formulario.class);
                        //startActivity(myIntent);
                    }
                }
        );
        //JSONObject Quest= new JSONObject();

        //String Result=;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_forgot_password, menu);
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
