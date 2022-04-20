package com.C_M_P.weathervn.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.constant.MyUnit;
import com.C_M_P.weathervn.middleware.UpperCase;
import com.C_M_P.weathervn.middleware.Weather;
import com.C_M_P.weathervn.middleware.MyGlide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentCurrent extends Fragment {
    JSONObject mJSONObject;
    ImageView imv_current;
    TextView tv_weather_description,
             tv_wind_speed,
             tv_temp,
             tv_feels_like;

    String icon, description;
    float wind_speed, temp, feels_like;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_linear_current, container, false);
        // mapping
        imv_current            = (ImageView) v.findViewById(R.id.imv_current);
        tv_weather_description = (TextView) v.findViewById(R.id.tv_weather_description);
        tv_wind_speed          = (TextView) v.findViewById(R.id.tv_wind_speed);
        tv_temp                = (TextView) v.findViewById(R.id.temp);
        tv_feels_like          = (TextView) v.findViewById(R.id.feels_like);

        return v;
    }



    public void setValueElements(JSONObject jsonObject) throws JSONException {
        this.mJSONObject = jsonObject;

        JSONObject currentObj = jsonObject.getJSONObject("current");
        JSONArray weatherArray = currentObj.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);

        icon = weatherObject.getString("icon");
        description = weatherObject.getString("description");
        wind_speed = (float) currentObj.getDouble("wind_speed");
        temp = (float) currentObj.getDouble("temp");
        feels_like = (float) currentObj.getDouble("feels_like");

        MyGlide.loadIcon(getActivity().getApplicationContext(), icon, imv_current);
        description = UpperCase.charFirst(description);
        tv_weather_description.setText(description);
        tv_wind_speed.setText(Weather.getNameWindSpeed(wind_speed));
        tv_temp.setText(Weather.getDegreesAutoConvert(temp, MyUnit.TEMP, true));
        tv_feels_like.setText("Feels like " + Weather.getDegreesAutoConvert(feels_like, MyUnit.TEMP, true));


    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh Fragment
        // onCreateView => onResume => setValueElement
        if(mJSONObject != null) {
            try {
                setValueElements(mJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // =========================================================
    public void Logd(String str){
        Log.d("Log.d", str + "\n === FragmentCurrent.java ==============================");
    }
    public void Logdln(String str, int n){
        Log.d("Log.d", str + "\n === FragmentCurrent.java - line: " + n + " ==============================");
    }
    public void showToast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}