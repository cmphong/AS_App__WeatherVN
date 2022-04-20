package com.C_M_P.weathervn.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.middleware.LinearChartCubic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentChart extends Fragment {
    TextView precipitation_forcasts;
    LinearLayout chart;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_linear_chart, container, false);
        // mapping
        precipitation_forcasts = (TextView) v.findViewById(R.id.precipitation_forecasts);
        chart = (LinearLayout) v.findViewById(R.id.mapChart);

        return v;
    }

    public void setValueElements(JSONObject jsonObject) throws JSONException {
        JSONArray minutelyArr = jsonObject.getJSONArray("minutely");
        JSONArray hourlyArr = jsonObject.getJSONArray("hourly");

        bundle = new Bundle();
        bundle.putString("minutelyArr", minutelyArr.toString());
        bundle.putString("hourlyArr", hourlyArr.toString());
        LinearChartCubic linearChartCubic = new LinearChartCubic();
        linearChartCubic.setArguments(bundle);
        loadFragment(R.id.mapChart, linearChartCubic);
    }

    private void loadFragment(int id, Fragment fragment){
        getParentFragmentManager()
                .beginTransaction()
                .replace(id, fragment)
                .commit();
//                .commitAllowingStateLoss();
    }



    // =========================================================
    public void Logd(String str){
        Log.d("Log.d", str + "\n === FragmentChart.java ==============================");
    }
    public void Logdln(String str, int n){
        Log.d("Log.d", str + "\n === FragmentChart.java - line: " + n + " ==============================");
    }
    public void showToast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}