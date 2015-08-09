package com.example.david.kingofthehill_app;

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
    private String url;

    public Rest(String url) {
        this.url=url;
    }

    /**
     * Metodo encargado de solicitar los json
     *
     * @return String
     */

    public String getContent() {
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        HttpGet httpget = new HttpGet(url);
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
    public String logout(String token) {
        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = null;
        InputStream instream = null;
        try {
            httpget.addHeader("userToken", token);
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

    public String postContent(JSONObject json) throws JSONException, IOException {

        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        InputStream instream = null;
        try {
            //httppost.addHeader("access_token", "FuckingToken");
            //httppost.addHeader("expires_int", "3600");
            HttpPost httppost = new HttpPost(url);
            StringEntity params = new StringEntity(json.toString());
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
     * @param json
     * @param token
     * @return String
     * @throws JSONException
     * @throws IOException
     */
    public String postContentUser(JSONObject json,String token) throws JSONException, IOException {

        HttpClient httpclient = new DefaultHttpClient();
        String result = null;
        InputStream instream = null;
        try {

            HttpPost httppost = new HttpPost(url);
            StringEntity params = new StringEntity(json.toString());
            httppost.addHeader("content-type", "application/json");
            httppost.addHeader("userToken", token);
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
     * @param is
     * @return String
     */
    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }

    /**
     * Metodo para aplicar hash al password
     * @param s, contrasena
     * @return string con hash
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
