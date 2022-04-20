package com.C_M_P.weathervn.SettingActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.C_M_P.weathervn.R;

public class MainSettingActivity extends AppCompatActivity {
  ImageButton ic_back;
  RelativeLayout relative_different_weather,
                  relative_customize_units;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting_main);

    ic_back = (ImageButton) findViewById(R.id.setting_ic_back);
    relative_different_weather = (RelativeLayout) findViewById(R.id.relative_different_weather);
    relative_customize_units = (RelativeLayout) findViewById(R.id.relative_customize_units);

    ic_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    relative_different_weather.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainSettingActivity.this, FeedbackSettingActivity.class));
      }
    });
    relative_customize_units.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainSettingActivity.this, UnitsSettingActivity.class));
      }
    });
  }
}