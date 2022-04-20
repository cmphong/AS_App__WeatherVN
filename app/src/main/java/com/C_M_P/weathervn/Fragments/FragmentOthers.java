package com.C_M_P.weathervn.Fragments;

import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.constant.MyUnit;
import com.C_M_P.weathervn.middleware.Weather;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentOthers extends Fragment {
    JSONObject mJSONObject;

    TextView current_wind_speed,
             current_humidity,
             current_uvi,
             current_pressure,
             current_visibility,
             current_dew_point;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_linear_others, container, false);
        // mapping
        current_wind_speed  = (TextView) v.findViewById(R.id.tv_wind_speed);
        current_humidity    = (TextView) v.findViewById(R.id.humidity);
        current_uvi         = (TextView) v.findViewById(R.id.uvi);
        current_pressure    = (TextView) v.findViewById(R.id.pressure);
        current_visibility  = (TextView) v.findViewById(R.id.visibility);
        current_dew_point   = (TextView) v.findViewById(R.id.dew_point);

        return v;
    }

    public void setValueElements(JSONObject jsonObject) throws JSONException {
        this.mJSONObject = jsonObject;

        JSONObject current = jsonObject.getJSONObject("current");
        float wind_speed = (float) current.getDouble("wind_speed");
        int humidity = current.getInt("humidity");
        float uvi = (float) current.getDouble("uvi");
        int pressure = current.getInt("pressure");
        float visibility = current.getInt("visibility");
        float dew_point = (float) current.getDouble("dew_point");

        current_wind_speed.setText("Wind: "+ Weather.getWindSpeedAutoConvert(wind_speed, MyUnit.SPEED));
        current_humidity.setText("Humidity: "+ Weather.getHumidityAutoConvert(humidity));
        current_uvi.setText("UV index: " + Weather.getUviAutoCovert(uvi));
        current_pressure.setText("Pressure: "+ Weather.getPressureAutoConvert(pressure, MyUnit.PRESSURE));
        current_visibility.setText("Visibility: "+ Weather.getDistanceAutoConvert(visibility, MyUnit.DISTANCE));
        current_dew_point.setText("Dew point: "+ Weather.getDegreesAutoConvert(dew_point, MyUnit.TEMP, true));
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mJSONObject != null){
            try {
                setValueElements(mJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

















