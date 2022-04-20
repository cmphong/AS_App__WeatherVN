package com.C_M_P.weathervn.middleware;

public class UpperCase {
  public static String charFirst(String str) {
    String firstChar = String.valueOf(str.charAt(0));
    return firstChar.toUpperCase() + str.substring(1);
  }
}
