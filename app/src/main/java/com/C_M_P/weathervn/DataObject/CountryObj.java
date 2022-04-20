package com.C_M_P.weathervn.DataObject;

import java.io.Serializable;

public class CountryObj implements Serializable {
    private String  id,
            name,
            lat,
            lon,
            country,
            country_code;

    public CountryObj(String id, String name, String lat, String lon, String country, String country_code) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.country = country;
        this.country_code = country_code;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
