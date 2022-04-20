package com.C_M_P.weathervn.middleware;

import com.C_M_P.weathervn.constant.MyUnit;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Weather {

    public static String getNameWindSpeed(float number){
        String result = "";
        int wind_speed = (int) Math.round(number);

        if(wind_speed > 0 && wind_speed < 20){
            result = "Gentle Breeze";
        }
        if(wind_speed >= 20 && wind_speed < 40){
            result = "Moderate Breeze";
        }
        if(wind_speed >= 40 && wind_speed < 60){
            result = "Strong Gale";
        }
        if(wind_speed >= 60 && wind_speed < 117){
            result = "Violent Storm";
        }
        if(wind_speed >= 117){
            result = "Hurricane Force";
        }

        return result;
    }

    /**
     * ºF = (ºC * 1.8) + 32<br>
     * ºC = (ºF - 32) / 1.8
     */
    public static String getDegreesAutoConvert(float C, String unit, boolean showUnit){
        if(unit.equals(MyUnit.TEMPF)){
            float result = (float) (C * 1.8 + 32);
            return Math.round(result) + " " + (showUnit ? unit : "");
        }
        return Math.round(C) + " " + (showUnit ? unit : "");
    }

    /**
     * 1 km = 0.621371 mile
     */
    public static String getWindSpeedAutoConvert(float speed_ms, String unit){
        DecimalFormat df = new DecimalFormat("0.0");

        if(unit.equals(MyUnit.SPEED_kmh)){
            return df.format(speed_ms / 1000 * 3600) + " " + unit;
        }else if(unit.equals(MyUnit.SPEED_mph)){
            return df.format(speed_ms / 1000 * 0.621371 * 3600) + " " + unit;
        }
        return df.format(speed_ms) + " " + unit;
    }


    /**
     * 1 inHg = 33.863886 (hPa)<br>
     * => 1 hPa = 1 / 33.863886 (inHg)
     */
    public static String getPressureAutoConvert(int hPa, String unit){
        DecimalFormat df = new DecimalFormat("0.00");
        if(unit.equals(MyUnit.PRESSURE_inHg)){
            return df.format(hPa / 33.863886) + " " + unit;
        }
        return hPa + " " + unit;
    }


    /**
     * 1 km = 0.621371 mile
     */
    public static String getDistanceAutoConvert(float m, String unit){
        DecimalFormat df = new DecimalFormat("0.0");
        if(unit.equals(MyUnit.DISTANCE_mi)){
            return df.format(m / 1000 * 0.621371) + " " + unit;
        }
        return df.format(m / 1000) + " " + unit;
    }


    /**
     * 1 inch = 25.4 mm
     */
    public static String getPrecipitationAutoConvert(float mm, String unit){
        DecimalFormat df = new DecimalFormat("0.0");
        if(unit.equals(MyUnit.PRECIPITATION_in)){
            return df.format(mm / 24.5) + " " + unit;
        }
        return Math.round(mm) + " " + unit;
    }

    public static String getUviAutoCovert(float uvi){
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(uvi);
    }

    public static String getTimeFormatAutoConvert(int seconds, String unit){
        SimpleDateFormat df;

        if(unit.equals(MyUnit.TIME_FORMAT_12)){
            df = new SimpleDateFormat("hh:mm a");
            return df.format(seconds * 1000L);
        }

        df = new SimpleDateFormat("HH:mm");
        return df.format(seconds * 1000L);
    }

    public static String getPopAutoConvert(float pop){
        return (int) (pop *  100) + " %";
    }

    public static String getHumidityAutoConvert(int humidity){
        return humidity + " %";
    }
}
