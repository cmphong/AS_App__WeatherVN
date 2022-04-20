package com.C_M_P.weathervn.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.C_M_P.weathervn.DataObject.DailyObj;
import com.C_M_P.weathervn.Fragments.FragmentDailyDetail;
import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.constant.MyUnit;
import com.C_M_P.weathervn.middleware.InterfaceDetail;
import com.C_M_P.weathervn.middleware.MyGlide;
import com.C_M_P.weathervn.middleware.Weather;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterDaily extends RecyclerView.Adapter<AdapterDaily.ViewHolder> {
  Context context;
  ArrayList<DailyObj> arrayList;

  public AdapterDaily(Context context) {
    this.context = context;
  }

  public void setData(ArrayList<DailyObj> arrayList){
    this.arrayList = arrayList;
    notifyDataSetChanged();
  }

  @NonNull
  @NotNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    View v = LayoutInflater
            .from(context)
            .inflate(R.layout.item_vertical_daily, parent, false);

    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull AdapterDaily.ViewHolder holder, int position) {
    DailyObj dailyitem = arrayList.get(position);
    JSONObject item = dailyitem.getDaily_item();
    try {
      int dt = item.getInt("dt");
      JSONObject temp = item.getJSONObject("temp");
      float max = (float) temp.getDouble("max");
      float min = (float) temp.getDouble("min");
      JSONArray weather = item.getJSONArray("weather");
      JSONObject weather_item = weather.getJSONObject(0);
      String icon = weather_item.getString("icon");

      SimpleDateFormat df = new SimpleDateFormat("EEEE, dd MMMM");

      holder.tv_date.setText(df.format(dt*1000L));
      holder.tv_temp_max_min.setText(
              Weather.getDegreesAutoConvert(max, MyUnit.TEMP, false)
              +" / "
              + Weather.getDegreesAutoConvert(min, MyUnit.TEMP, false)
              + " " + MyUnit.TEMP);
      MyGlide.loadIcon(context, icon, holder.iv_icon);
    } catch (JSONException e) {
      e.printStackTrace();
    }


  }

  @Override
  public int getItemCount() {
    return arrayList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tv_date, tv_temp_max_min;
    ImageView iv_icon;

    public ViewHolder(@NonNull @NotNull View itemView) {
      super(itemView);

      tv_date         = (TextView) itemView.findViewById(R.id.tv_date);
      tv_temp_max_min = (TextView) itemView.findViewById(R.id.tv_temp_max_min);
      iv_icon         = (ImageView) itemView.findViewById(R.id.iv_icon);

      itemView.setOnClickListener(this);

    }

    @Override // implement View.OnClickListener
    public void onClick(View v) {
      Fragment linearDailyDetail = new FragmentDailyDetail();
      InterfaceDetail interfaceDetail = (InterfaceDetail) context;
      //                                                                  mảng daily cho LinearDailyDetail
      interfaceDetail.showDetail(linearDailyDetail, getAdapterPosition(), arrayList);
    }
  }
  // =========================================================
  public void Logd(String str){
      Log.d("Log.d", str + "\n === AdapterDaily.java ==============================");
  }
  public void Logdln(String str, int n){
      Log.d("Log.d", str + "\n === AdapterDaily.java - line: " + n + " ==============================");
  }
}
