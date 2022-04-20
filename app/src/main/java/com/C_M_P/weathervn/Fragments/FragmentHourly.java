package com.C_M_P.weathervn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.C_M_P.weathervn.DataObject.CurrentObj;
import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.adapters.AdapterCurrent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentHourly extends Fragment {
  JSONObject mJSONObject;
  RecyclerView recyclerView;

  ArrayList<CurrentObj> arrayList = new ArrayList<>();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_linear_hourly, container, false);
    recyclerView = v.findViewById(R.id.rv_horizontal_list);

    return v;
  }

  public void setValueElements(JSONObject jsonObject) throws JSONException {
    this.mJSONObject = jsonObject;

    JSONArray hourlyArr = jsonObject.getJSONArray("hourly");
    for(int i = 0 ; i < hourlyArr.length() ; i++){
      JSONObject item = hourlyArr.getJSONObject(i);
      int dt = item.getInt("dt");
      JSONArray weatherArr = item.getJSONArray("weather");
      float temp = (float) item.getDouble("temp");
      String icon = "";
      // HANDLE weatherArr
      for(int j = 0 ; j < weatherArr.length() ; j++){
        JSONObject itemOfWeather = weatherArr.getJSONObject(j);
        icon = itemOfWeather.getString("icon");
      }
      arrayList.add(new CurrentObj(dt, icon, temp));
    }
    RecyclerView.Adapter adapter = new AdapterCurrent(FragmentHourly.this, arrayList);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

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

  // =========================================================
  public void Logd(String str){
      Log.d("Log.d", str + "\n === FragmentHourly.java ==============================");
  }
  public void Logdln(String str, int n){
      Log.d("Log.d", str + "\n === FragmentHourly.java - line: " + n + " ==============================");
  }
}