package com.example.david.kingofthehill_app;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by David on 05/08/2015.
 * hasheo de la clave
 */
public class Clave {
    private String Hash;

    public Clave(String Hash) {
        this.Hash = Hash;
    }

    public String MD5_Hash() {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(Hash.getBytes(),0,Hash.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }
}
