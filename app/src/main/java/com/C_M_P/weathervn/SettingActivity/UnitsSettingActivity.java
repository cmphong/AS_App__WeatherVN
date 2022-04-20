package com.C_M_P.weathervn.SettingActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.constant.MyUnit;

import org.json.JSONObject;

public class UnitsSettingActivity extends AppCompatActivity {
  SharedPreferences sharedPreferences;
  SharedPreferences.Editor spEditor;
  final String TEMPERATURE    = "TEMPERATURE",
               WIND_SPEED     = "WIND_SPEED",
               PRESSURE       = "PRESSURE",
               PRECIPITATION  = "PRECIPITATION",
               DISTANCE       = "DISTANCE",
               TIME_FORMAT    = "TIME_FORMAT";

  float density;

  public static JSONObject response = null;

  ImageButton ic_back;

  TextView tv_tempC, tv_tempF,
           tv_ms   , tv_kmh  , tv_mph,
           tv_hPa  , tv_inHg ,
           tv_mm   , tv_inch ,
           tv_km   , tv_mile ,
           tv_24h  , tv_12h;
  View view_anim_temp    , view_anim_windspeed,
       view_anim_pressure, view_anim_precipitation,
       view_anim_distance, view_anim_time;
  ObjectAnimator animationX;
  int myTime = 300;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting_units);

    density = getResources().getDisplayMetrics().density;
    mapping();
    setPositionForViewAnim();

    // ICON BACK ==================================================================
    ic_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    // ROW 1 ==================================================================
    tv_tempC.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_temp, 0f);

        MyUnit.TEMP = MyUnit.TEMPC;
        putDataToSharedPreferences(TEMPERATURE, MyUnit.TEMPC);

      }
    });
    tv_tempF.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_temp, 105 * density);

        MyUnit.TEMP = MyUnit.TEMPF;
        putDataToSharedPreferences(TEMPERATURE, MyUnit.TEMPF);

      }
    });

    // ROW 2 ==================================================================
    tv_ms.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_windspeed, 0f);

        MyUnit.SPEED = MyUnit.SPEED_ms;
        putDataToSharedPreferences(WIND_SPEED, MyUnit.SPEED_ms);

      }
    });
    tv_kmh.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_windspeed, 70 * density);

        MyUnit.SPEED = MyUnit.SPEED_kmh;
        putDataToSharedPreferences(WIND_SPEED, MyUnit.SPEED_kmh);

      }
    });
    tv_mph.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_windspeed, 140 * density);

        MyUnit.SPEED = MyUnit.SPEED_mph;
        putDataToSharedPreferences(WIND_SPEED, MyUnit.SPEED_mph);

      }
    });

    // ROW 3 ==================================================================
    tv_hPa.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_pressure, 0f);

        MyUnit.PRESSURE = MyUnit.PRESSURE_hPa;
        putDataToSharedPreferences(PRESSURE, MyUnit.PRESSURE_hPa);

      }
    });
    tv_inHg.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_pressure, 105 * density);

        MyUnit.PRESSURE = MyUnit.PRESSURE_inHg;
        putDataToSharedPreferences(PRESSURE, MyUnit.PRESSURE_inHg);

      }
    });

    // ROW 4 ==================================================================
    tv_mm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_precipitation, 0f);

        MyUnit.PRECIPITATION = MyUnit.PRECIPITATION_mm;
        putDataToSharedPreferences(PRECIPITATION, MyUnit.PRECIPITATION_mm);

      }
    });
    tv_inch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_precipitation, 105 * density);

        MyUnit.PRECIPITATION = MyUnit.PRECIPITATION_in;
        putDataToSharedPreferences(PRECIPITATION, MyUnit.PRECIPITATION_in);

      }
    });

    // ROW 5 ==================================================================
    tv_km.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_distance, 0f);

        MyUnit.DISTANCE = MyUnit.DISTANCE_km;
        putDataToSharedPreferences(DISTANCE, MyUnit.DISTANCE_km);

      }
    });
    tv_mile.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_distance, 105 * density);

        MyUnit.DISTANCE = MyUnit.DISTANCE_mi;
        putDataToSharedPreferences(DISTANCE, MyUnit.DISTANCE_mi);

      }
    });

    // ROW 6 ==================================================================
    tv_24h.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_time, 0f);

        MyUnit.TIME_FORMAT = MyUnit.TIME_FORMAT_24;
        putDataToSharedPreferences(TIME_FORMAT, MyUnit.TIME_FORMAT_24);

      }
    });
    tv_12h.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation(view_anim_time, 105 * density);

        MyUnit.TIME_FORMAT = MyUnit.TIME_FORMAT_12;
        putDataToSharedPreferences(TIME_FORMAT, MyUnit.TIME_FORMAT_12);

      }
    });
  }

  private void putDataToSharedPreferences(String keyName, String value) {
    spEditor = sharedPreferences.edit();
    spEditor.putString(keyName, value);
    spEditor.apply();
  }

  private void setPositionForViewAnim() {
    sharedPreferences = getApplicationContext().getSharedPreferences("dataUnitsSetting", MODE_PRIVATE);

    String temp = sharedPreferences.getString(TEMPERATURE, "");
    if(temp.equals(MyUnit.TEMPF)){
      runAnimation(view_anim_temp, 105 * density);
    };

    String wind_speed = sharedPreferences.getString(WIND_SPEED, "");
    if(wind_speed.equals(MyUnit.SPEED_kmh)){
      runAnimation(view_anim_windspeed, 70 * density);
    };
    if(wind_speed.equals(MyUnit.SPEED_mph)){
      runAnimation(view_anim_windspeed, 140 * density);
    };

    String pressure = sharedPreferences.getString(PRESSURE, "");
    if(pressure.equals(MyUnit.PRESSURE_inHg)){
      runAnimation(view_anim_pressure, 105 * density);
    };

    String precipitation = sharedPreferences.getString(PRECIPITATION, "");
    if(precipitation.equals(MyUnit.PRECIPITATION_in)){
      runAnimation(view_anim_precipitation, 105 * density);
    };

    String distance = sharedPreferences.getString(DISTANCE, "");
    if(distance.equals(MyUnit.DISTANCE_mi)){
      runAnimation(view_anim_distance, 105 * density);
    };

    String time_format = sharedPreferences.getString(TIME_FORMAT, "");
    if(time_format.equals(MyUnit.TIME_FORMAT_12)){
      runAnimation(view_anim_time, 105 * density);
    };
  }

  private void runAnimation(View view_anim,  float Xpx){
    animationX = ObjectAnimator.ofFloat(
            view_anim,
            "translationX",
            Xpx // pixel unit = dp * density
    );
    animationX.setDuration(myTime);
    animationX.start();
  }

  public static void setData(JSONObject jsonObject){
    response = jsonObject;
  }

  private void mapping() {
    ic_back = (ImageButton) findViewById(R.id.units_ic_back);

    tv_tempC = (TextView) findViewById(R.id.tv_tempC);
    tv_tempF = (TextView) findViewById(R.id.tv_tempF);
    tv_ms = (TextView) findViewById(R.id.tv_ms);
    tv_kmh = (TextView) findViewById(R.id.tv_kmh);
    tv_mph = (TextView) findViewById(R.id.tv_mph);

    tv_hPa = (TextView) findViewById(R.id.tv_hPa);
    tv_inHg = (TextView) findViewById(R.id.tv_inHg);
    tv_mm = (TextView) findViewById(R.id.tv_mm);
    tv_inch = (TextView) findViewById(R.id.tv_inch);

    tv_km = (TextView) findViewById(R.id.tv_km);
    tv_mile = (TextView) findViewById(R.id.tv_mile);
    tv_24h = (TextView) findViewById(R.id.tv_24h);
    tv_12h = (TextView) findViewById(R.id.tv_12h);

    view_anim_temp = (View) findViewById(R.id.view_anim_temp);
    view_anim_windspeed = (View) findViewById(R.id.view_anim_windspeed);
    view_anim_pressure = (View) findViewById(R.id.view_anim_pressure);
    view_anim_precipitation = (View) findViewById(R.id.view_anim_precipitation);
    view_anim_distance = (View) findViewById(R.id.view_anim_distance);
    view_anim_time = (View) findViewById(R.id.view_anim_time);

  }

  // =========================================================
  public void Logd(String str){
      Log.d("Log.d", "=== UnitsSettingActivity.java ==============================\n" + str);
  }
  public void Logdln(String str, int n){
      Log.d("Log.d", "=== UnitsSettingActivity.java - line: " + n + " ==============================\n" + str);
  }
  public void showToast(String str){
    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
  }
}