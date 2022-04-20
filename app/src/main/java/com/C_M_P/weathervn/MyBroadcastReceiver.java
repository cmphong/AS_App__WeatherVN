package com.C_M_P.weathervn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!isNetworAvailable(context)){
            MainActivity.startNetworkErrorActivity();
        }
    }

    private boolean isNetworAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null){
            return false;
        }


        // Android 7+
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Network network = connectivityManager.getActiveNetwork();
            if(network == null){
                return false;
            }else{
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                if(capabilities == null){
                    return false;
                }else{
                    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
                }
            }

        }else{
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

//            return networkInfo != null && networkInfo.isConnected();
            if(networkInfo != null){
                if(networkInfo.isConnected()){
                    return true;
                }else{
                    return false;
                }
            }else {
                return false;
            }
        }
    }





    // ====================================================================
    public void Logd(String str){
        Log.d("Log.d", "=== myBroadcastReceiver.java ==============================\n" + str);
    }
    public void Logdln(String str, int n){
        Log.d("Log.d", "=== myBroadcastReceiver.java - line: " + n + " ==============================\n" + str);
    }
    public static void LogdStatic(String str){
        Log.d("Log.d", "=== myBroadcastReceiver.java ==============================\n" + str);
    }
    public static void LogdlnStatic(String str, int n){
        Log.d("Log.d", "=== myBroadcastReceiver.java - line: " + n + " ==============================\n" + str);
    }

    public void showToast(Context context, String str ){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
