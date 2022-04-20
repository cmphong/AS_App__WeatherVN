package com.C_M_P.weathervn.middleware;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class FnLocation {
    public static Context context;

    // =========================================================
    public static void Logd(String str){
        Log.d("Log.d", "=== FnLocation.java ==============================\n" + str);
    }
    public static void Logdln(String str, int n){
        Log.d("Log.d", "=== FnLocation.java - line: " + n + " ==============================\n" + str);
    }
    public static void showToast(String str){
      Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

}
