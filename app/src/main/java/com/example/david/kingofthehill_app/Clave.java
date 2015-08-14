package com.example.david.kingofthehill_app;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by David on 05/08/2015.
 * hasheo de la clave
 */
public class Clave {
    //private String _Hash;

    public Clave() {


    }

    public String MD5_Hash(String pClave) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(pClave.getBytes(), 0, pClave.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }
}
