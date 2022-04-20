package com.C_M_P.weathervn.middleware;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MyGlide {
  public static void loadIcon(Context context, String iconName, ImageView imageView){
    // USAGE Glide ==================================================
    String myImage = "http://openweathermap.org/img/wn/"+iconName+"@2x.png";
    Glide.with(context).load(myImage).into(imageView);
  }
  public static void loadFlag(Context context, String country_code, ImageView imageView){
    // USAGE Glide ==================================================
    String myImage = "https://www.countryflags.io/" +country_code+ "/flat/64.png";
    Glide.with(context).load(myImage).into(imageView);
  }
}
