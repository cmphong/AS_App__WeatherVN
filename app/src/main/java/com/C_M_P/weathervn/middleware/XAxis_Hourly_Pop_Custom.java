package com.C_M_P.weathervn.middleware;

import android.util.Log;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class XAxis_Hourly_Pop_Custom extends IndexAxisValueFormatter {
  ArrayList<Integer> hourly_dt;
  ArrayList<Float> hourly_pop;

  public XAxis_Hourly_Pop_Custom(ArrayList<Integer> hourly_dt, ArrayList<Float> hourly_pop){
    this.hourly_dt = hourly_dt;
    this.hourly_pop = hourly_pop;
  }


  @Override
  public String getFormattedValue(float value) {

    String hour = getHour(hourly_dt.get((int) value));
    String pop = getPop(hourly_pop.get((int) value));
    return hour + "\n" + pop + "%";
  }

  private String getHour(int second){
    SimpleDateFormat df = new SimpleDateFormat("HH:mm");
    return df.format(second*1000L);
  }
  private String getPop(float pop){
    return String.valueOf(Math.round(pop*100));
  }

  // =========================================================
  public void Logd(String str){
      Log.d("Log.d", str + "\n === XAxis_Hourly_Pop_Custom.java ==============================");
  }
  public void Logdln(String str, int n){
      Log.d("Log.d", str + "\n === XAxis_Hourly_Pop_Custom.java - line: " + n + " ==============================");
  }
}
