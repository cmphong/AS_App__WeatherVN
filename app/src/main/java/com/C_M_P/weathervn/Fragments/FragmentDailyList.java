package com.C_M_P.weathervn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.C_M_P.weathervn.DataObject.DailyObj;
import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.adapters.AdapterDaily;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * {
 *   temp
 * }
 */

public class FragmentDailyList extends Fragment {
  JSONObject mJSONObject;
  RecyclerView recyclerView;

  ArrayList<DailyObj> arrayList;
  AdapterDaily adapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_linear_daily_list, container, false);

    recyclerView = v.findViewById(R.id.rv_vertical_list);
    arrayList = new ArrayList<>();

    if(adapter != null){
      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    return v;
  }

  public void setValueElements(JSONObject jsonObject) throws JSONException {
      this.mJSONObject = jsonObject;

      arrayList.clear();

      //        daily            response
      JSONArray jsonArrayDaily = jsonObject.getJSONArray("daily");
      for(int i = 0 ; i < jsonArrayDaily.length() ; i++){
          JSONObject item = jsonArrayDaily.getJSONObject(i);
          arrayList.add(new DailyObj(item));
      }
      adapter = new AdapterDaily(getContext());
      adapter.setData(arrayList); // jsonArrayDaily
      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
      Log.d("Log.d", "=== FragmentDailyList.java ==============================\n" + str);
  }
  public void Logdln(String str, int n){
      Log.d("Log.d", "=== FragmentDailyList.java - line: " + n + " ==============================\n" + str);
  }
  public void showToast(String str){
    Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
  }
}