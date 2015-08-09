package com.example.david.kingofthehill_app;

/**
 * Created by David on 04/08/2015.
 * POJO para com.example.david.prueba3.coordenadas de google maps
 */
public class coordenadas {
    private String lat;
    private String lon;

    public coordenadas(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
