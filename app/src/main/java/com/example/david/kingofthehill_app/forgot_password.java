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

    private TextView Question;
    private EditText Answer;
    private Rest Server=new Rest("http://192.168.1.135:8080/KingOfTheHill/webresources/users/forgotpassword");
    private Rest Server2=new Rest("http://192.168.1.135:8080/KingOfTheHill/webresources/users/setpassword");
    private User Usuario= new User();
    private JSONObject Quest= new JSONObject();
    private JSONObject Ans= new JSONObject();
    private Clave pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Question=(TextView)findViewById(R.id.textView8);
        Answer=(EditText)findViewById(R.id.editText7);
        Question.setText("User");
        Button Send= (Button)findViewById(R.id.button4);
        Send.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        if (Question.getText().equals("User")) {
                            try {
                                Usuario.setUsername(Answer.getText().toString());
                                Quest.put("username",Usuario.getUsername() );
                                Quest.put("question", "true");
                                Quest.put("answwer", "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                String response = Server.postContent(Quest);
                                if (response == null) {
                                    new AlertDialog.Builder(forgot_password.this)
                                            .setTitle("Error")
                                            .setMessage("No username found")
                                            .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Answer.setText("");
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                    Answer.setText("");
                                } else {
                                    Question.setText(response);
                                    Answer.setText("");
                                    Usuario.setQuestion(response);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                        else if(Question.getText().equals("Password")){
                            try {
                                pass= new Clave(Answer.getText().toString());
                                Ans.put("username",Usuario.getUsername());
                                Ans.put("password",pass.MD5_Hash());
                                Ans.put("answer",Usuario.getAnswer());
                                String response = Server2.postContent(Ans);
                                if (response == null){
                                    new AlertDialog.Builder(forgot_password.this)
                                            .setTitle("Error")
                                            .setMessage("Incorrect answer")
                                            .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Answer.setText("");
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                    Question.setText(Usuario.getQuestion());
                                    Answer.setText("");
                                }
                                else{
                                    new AlertDialog.Builder(forgot_password.this)
                                            .setTitle("Success")
                                            .setMessage("Password changed")
                                            .setNeutralButton("Continue", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Answer.setText("");

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
                        }
                        else{
                            Usuario.setAnswer(Answer.getText().toString());
                            Question.setText("Password");
                            Answer.setText("");
                        }

                        //Intent myIntent = new Intent(v.getContext(), formulario.class);
                        //startActivity(myIntent);
                    }
                }
        );
        JSONObject Quest= new JSONObject();

        //String Result=;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forgot_password, menu);
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
