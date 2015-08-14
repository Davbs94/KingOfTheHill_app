package com.example.david.kingofthehill_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**Clase para guardar preferencias
 * Created by David on 07/08/2015.
 */
public class SharedPref {
    public SharedPref() {
    }

    /**
     * Coloca una preferencia
     * @param pKey
     * @param pValue
     * @param pContext
     */
    public static void putPref(String pKey, String pValue, Context pContext) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(pContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(pKey, pValue);
        editor.commit();
    }

    /**
     * edita una preferencia
     * @param pKey
     * @param pValue
     * @param pContext
     */
    public static void editPref(String pKey, String pValue, Context pContext) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(pContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(pKey, pValue);
        editor.apply();
    }

    /**
     * retorna una prefrencia
     * @param pKey
     * @param pContext
     * @return
     */
    public static String getPref(String pKey, Context pContext) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(pContext);
        return preferences.getString(pKey, null);
    }
}
