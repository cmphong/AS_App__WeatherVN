package com.C_M_P.weathervn.DataObject;

public class CurrentObj {
  int dateTime;
  String icon;
  float temp;

  public CurrentObj(int dateTime, String icon, float temp) {
    this.dateTime = dateTime;
    this.icon = icon;
    this.temp = temp;
  }

  public int getDateTime() {
    return dateTime;
  }

  public void setDateTime(int dateTime) {
    this.dateTime = dateTime;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public float getTemp() {
    return temp;
  }

  public void setTemp(float temp) {
    this.temp = temp;
  }
}
