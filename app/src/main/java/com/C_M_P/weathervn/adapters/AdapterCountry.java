package com.C_M_P.weathervn.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.C_M_P.weathervn.DataObject.CountryObj;
import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.SearchActivity;
import com.C_M_P.weathervn.middleware.MyGlide;

import java.util.ArrayList;

public class AdapterCountry extends BaseAdapter {
  private Context context;
  private ArrayList<CountryObj> arrayList;
  private int layout;

  public AdapterCountry(Context context, ArrayList<CountryObj> arrayList, int layout) {
    this.context = context;
    this.arrayList = arrayList;
    this.layout = layout;
  }

  @Override
  public int getCount() {
    if (arrayList != null){
      return arrayList.size();
    }
    return 0;
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }


  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    convertView = LayoutInflater.from(context).inflate(
            layout, parent, false
    );
    LinearLayout item_country = (LinearLayout) convertView.findViewById(R.id.linear_item_country);
    TextView tv_country = convertView.findViewById(R.id.tv_countryName);
    ImageView iv_flag = convertView.findViewById(R.id.iv_flag);

    CountryObj country = arrayList.get(position);

    tv_country.setText(country.getName());
    MyGlide.loadFlag(context, country.getCountry_code(), iv_flag);

    item_country.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SearchActivity.receiveDataFromAdapter(arrayList.get(position), false);
      }
    });


    return convertView;
  }

  // =========================================================
  public void Logd(String str){
      Log.d("Log.d", "=== AdapterCountry.java ==============================\n" + str);
  }
  public void Logdln(String str, int n){
      Log.d("Log.d", "=== AdapterCountry.java - line: " + n + " ==============================\n" + str);
  }
  public void showToast(String str){
    Toast.makeText(context, str, Toast.LENGTH_LONG).show();
  }
}
