package com.example.david.kingofthehill_app;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by David on 05/08/2015.
 * Clase para administracion de REST
 */
public class Rest {
    private final String _IP="http://192.168.1.135:8080/KingOfTheHill/webresources/";
    private final String _Registrar=_IP+"users/register";
    private final String _Login=_IP+"users/login";
    private final String _Forgot=_IP+"users/forgotpassword";
    private final String _NewPass=_IP+"users/setpassword";
    private final String _Logout=_IP+"users/logout";
    private final String _Battle=_IP+"mobile/checkbattle";
    private final String _SendPos=_IP+"mobile/send-position";
    private final String _Zones=_IP+"zones/retrieve-zones";
    private final String _ActPos=_IP+"mobile/retrieve-position";
    private final String _SendPoints=_IP+"mobile/sendresult";
    private final String _CheckServer=_IP+"mobile/check";
    private final String _GetPoints= _IP+"mobile/score";




    public Rest() {
        HotFix();
    }

    /**
     * Metodo encargado de solicitar los json
     * @param pUrl
     * @return String
     */

    public String getContent(String pUrl) {
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        HttpGet httpget = new HttpGet(pUrl);
        HttpResponse response = null;
        InputStream instream = null;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }
        } catch (Exception e) {
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {
                }
            }
        }
        return result;
    }

    /**
     * Envia token
     * @param pUrl
     * @param pToken
     * @return
     */
    public String sendToken(String pUrl, String pToken) {
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        HttpGet httpget = new HttpGet(pUrl);
        HttpResponse response = null;
        InputStream instream = null;
        try {
            httpget.addHeader("userToken", pToken);
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                instream = entity.getContent();
                result = convertStreamToString(instream);
            }
        } catch (Exception e) {
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (Exception exc) {
                }
            }
        }
        return result;
    }


    /**
     * Metodo para post
     * @param pUrl
     * @param pJson
     * @return respuesta
     * @throws JSONException
     * @throws IOException
     */
    public String postContent(String pUrl, JSONObject pJson) throws JSONException, IOException {

        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        InputStream instream = null;

        try {

            HttpPost httppost = new HttpPost(pUrl);
            StringEntity params = new StringEntity(pJson.toString());
            httppost.addHeader("content-type", "application/json");
            httppost.setEntity(params);
            HttpResponse httpResponse = httpclient.execute(httppost);
            instream = httpResponse.getEntity().getContent();
            result = convertStreamToString(instream);

        }



        catch (Exception ex) {

        } finally {
            httpclient.getConnectionManager().shutdown();

        }


        return result;

    }

    /**
     * Metodo de post con token para usuarios.
     * @param pUrl
     * @param pJson
     * @param pToken
     * @return respuesta
     * @throws JSONException
     * @throws IOException
     */
    public String postContentUser(String pUrl, JSONObject pJson,String pToken) throws JSONException, IOException {

        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        InputStream instream = null;
        try {

            HttpPost httppost = new HttpPost(pUrl);
            StringEntity params = new StringEntity(pJson.toString());
            httppost.addHeader("content-type", "application/json");
            httppost.addHeader("userToken", pToken);
            //httppost.addHeader("expiresIn", "3600");
            httppost.setEntity(params);
            HttpResponse httpResponse = httpclient.execute(httppost);
            instream = httpResponse.getEntity().getContent();
            result = convertStreamToString(instream);
        }
        catch (Exception ex) {
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return result;

    }
    /**
     * Convierte la informacion en un String utilizable
     * @param pIs
     * @return String
     */
    public String convertStreamToString(InputStream pIs) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(pIs));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                pIs.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }

    /**
     *
     * @return _Login
     */
    public String get_Login() {
        return _Login;
    }

    /**
     *
     * @return _Registrar
     */
    public String get_Registrar() {
        return _Registrar;
    }

    /**
     *
     * @return _Forgot
     */
    public String get_Forgot() {
        return _Forgot;
    }

    /**
     *
     * @return _NewPass
     */
    public String get_NewPass() {
        return _NewPass;
    }

    /**
     *
     * @return _Logout
     */
    public String get_Logout() {
        return _Logout;
    }

    /**
     *
     * @return _Battle
     */
    public String get_Battle() {
        return _Battle;
    }

    /**
     *
     * @return _SendPos
     */
    public String get_SendPos() {
        return _SendPos;
    }

    /**
     *
     * @return _Zones
     */
    public String get_Zones() {
        return _Zones;
    }

    /**
     * @return _ActPos
     */
    public String get_ActPos() {
        return _ActPos;
    }

    /**
     *
     * @return _SendPoints
     */
    public String get_SendPoints(){
        return _SendPoints;
    }

    /**
     *
     * @return _CheckServer
     */
    public String get_CheckServer() {
        return _CheckServer;
    }

    /**
     *
     * @return _GetPoints
     */
    public String get_GetPoints() {
        return _GetPoints;
    }


    /**
     * Metodo para corregir incompatibilidad con versiones de android mayores a 3.0
     */
    public void HotFix(){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
}
