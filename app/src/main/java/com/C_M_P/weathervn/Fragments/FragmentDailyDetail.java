package com.C_M_P.weathervn.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.C_M_P.weathervn.DataObject.DailyObj;
import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.adapters.AdapterDate;
import com.C_M_P.weathervn.adapters.AdapterDetail;
import com.C_M_P.weathervn.middleware.InterfaceDateItemSelected;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FragmentDailyDetail extends Fragment {

  AdapterDate adapterDate;
  AdapterDetail adapterDetail;

  ImageButton btnBack;
  TextView tv_weather_description, tv_wind_speed, tv_temp_max_min,
           tv_precipitation, tv_pop, tv_wind, tv_pressure,
           tv_humidity, tv_uvi, tv_sunrise, tv_sunset;
  ImageView ic_weather;
  RecyclerView rc_date;
  ViewPager2 vp2_detail;

  List<DailyObj> listDate;
  ArrayList<DailyObj> arrayList;
  int index;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_linear_daily_detail, container, false);

    mapping(v);

    listDate = new ArrayList<>();

    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().getSupportFragmentManager().popBackStack();
      }
    });

    // passing from MainActivity.java
    Bundle bundle = getArguments();
    if(bundle != null){
      index = bundle.getInt("index");
      arrayList = (ArrayList<DailyObj>) bundle.getSerializable("arrayList");

      // RecyclerView Date
      adapterDate = new AdapterDate(getContext(), new InterfaceDateItemSelected() {
        @Override
        public void onClick(View v, int position) {
          vp2_detail.setCurrentItem(position);
        }
      });
      adapterDate.setData(arrayList);
      adapterDate.setIndex(index);
      rc_date.setAdapter(adapterDate);
      rc_date.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

      // ViewPager2 Detail
      adapterDetail = new AdapterDetail(FragmentDailyDetail.this, arrayList);
      vp2_detail.setAdapter(adapterDetail);
      vp2_detail.setCurrentItem(index);

      // ANIMATION FOR vpHorizontal
      // Initialize composite page transformer
      CompositePageTransformer transformer = new CompositePageTransformer();
      // Add margin between page
      transformer.addTransformer(new MarginPageTransformer(8));
      // Increase selected page size
      transformer.addTransformer(new ViewPager2.PageTransformer() {
        @Override
        public void transformPage(@NonNull @NotNull View page, float position) {
          float v = 1 - Math.abs(position);
//          Logdln("v: " + v, 100);
          // Set scale y
          page.setScaleY(0.8f + v * 0.2f);
        }
      });

      // Set page transform animation
      vp2_detail.setPageTransformer(transformer);

      // Set background for indicator
      vp2_detail.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
          // Khi Page thay đổi thì set lại adapter cho myDateAdapter
          adapterDate.setIndex(position);
          rc_date.setAdapter(adapterDate);
          super.onPageSelected(position);
        }
      });

    }else {
      Logd("bundle is null");
    }

    return v;
  }

  private void mapping(View v) {

    btnBack = v.findViewById(R.id.btnBack);
    rc_date = v.findViewById(R.id.rv_date);
    vp2_detail = v.findViewById(R.id.vp2_detail);

    tv_weather_description = (TextView) v.findViewById(R.id.tv_weather_description);
    tv_wind_speed = (TextView) v.findViewById(R.id.tv_wind_speed);
    tv_temp_max_min = (TextView) v.findViewById(R.id.tv_temp_max_min);
    ic_weather = (ImageView) v.findViewById(R.id.ic_weather);

    tv_precipitation = (TextView) v.findViewById(R.id.tv_precipitation);
    tv_pop = (TextView) v.findViewById(R.id.tv_pop);
    tv_wind = (TextView)v.findViewById(R.id.tv_wind);
    tv_pressure = (TextView)v.findViewById(R.id.tv_pressure);
    tv_humidity = (TextView)v.findViewById(R.id.tv_humidity);
    tv_uvi = (TextView) v.findViewById(R.id.tv_uvi);
    tv_sunrise = (TextView) v.findViewById(R.id.tv_sunrise);
    tv_sunset = (TextView)v.findViewById(R.id.tv_sunset);

  }


  @Override
  public void onResume() {
    super.onResume();
    // re-render ViewPager
    vp2_detail.setAdapter(adapterDetail);
  }



  // =========================================================
  public void Logd(String str){
      Log.d("Log.d", "=== FragmentDailyDetail.java ==============================\n" + str);
  }
  public void Logdln(String str, int n){
      Log.d("Log.d", "=== FragmentDailyDetail.java - line: " + n + " ==============================\n" + str);
  }
  public void showToast(String str){
    Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
  }
}