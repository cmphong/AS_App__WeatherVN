package com.C_M_P.weathervn.DataObject;

import org.json.JSONObject;

import java.io.Serializable;

public class DailyObj implements Serializable{
  // transient - solve issue JSONObject not Serializable
  private transient JSONObject daily_item;

  // CONSTRUCTOR
  public DailyObj(JSONObject daily_item) {
    this.daily_item = daily_item;
  }

  public JSONObject getDaily_item() {
    return daily_item;
  }

  public void setDaily_item(JSONObject daily_item) {
    this.daily_item = daily_item;
  }
}
