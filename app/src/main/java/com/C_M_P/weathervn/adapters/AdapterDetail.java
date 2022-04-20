package com.C_M_P.weathervn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.C_M_P.weathervn.DataObject.DailyObj;
import com.C_M_P.weathervn.Fragments.FragmentDailyDetail;
import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.constant.MyUnit;
import com.C_M_P.weathervn.middleware.MyGlide;
import com.C_M_P.weathervn.middleware.UpperCase;
import com.C_M_P.weathervn.middleware.Weather;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdapterDetail extends RecyclerView.Adapter<AdapterDetail.MyViewHolder> {
  FragmentDailyDetail mContext;
  ArrayList<DailyObj> arrayList;

  public AdapterDetail(FragmentDailyDetail mContext, ArrayList<DailyObj> arrayList) {
    this.mContext = mContext;
    this.arrayList = arrayList;
  }

  @NonNull
  @NotNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    View v = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.item_vp2_detail, parent, false);
    return new MyViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull AdapterDetail.MyViewHolder holder, int position) {
      // Handle arrayList
      // Handle item JSONArray "daily"
      JSONObject item = arrayList.get(position).getDaily_item();

      try {
        JSONArray weather = item.getJSONArray("weather");
        JSONObject weather_item = weather.getJSONObject(0);
        String description = weather_item.getString("description");
        String icon = weather_item.getString("icon");

        JSONObject temp = item.getJSONObject("temp");
        float temp_max = (float) temp.getDouble("max");
        float temp_min = (float) temp.getDouble("min");

        float precipitation = (float) item.getDouble("rain");
        float pop  = (float) item.getDouble("pop");
        float wind_speed = (float) item.getDouble("wind_speed");
        float wind_deg = (float) item.getDouble("wind_deg");
        int pressure = item.getInt("pressure");
        int humidity = item.getInt("humidity");
        float uvi = (float) item.getDouble("uvi");
        int sunrise = item.getInt("sunrise");
        int sunset = item.getInt("sunset");

        holder.tv_weather_description.setText(UpperCase.charFirst(description));
        holder.tv_wind_speed.setText(UpperCase.charFirst(Weather.getNameWindSpeed(wind_speed)));

        holder.tv_temp_max_min.setText(
                Weather.getDegreesAutoConvert(temp_max, MyUnit.TEMP, false)
                +" / "
                + Weather.getDegreesAutoConvert(temp_min, MyUnit.TEMP, true));
        MyGlide.loadIcon(mContext.getActivity(), icon, holder.ic_weather);

        holder.tv_precipitation.setText(Weather.getPrecipitationAutoConvert(precipitation, MyUnit.PRECIPITATION));
        holder.tv_pop.setText(Weather.getPopAutoConvert(pop));
        holder.tv_wind.setText(Weather.getWindSpeedAutoConvert(wind_speed, MyUnit.SPEED));
        holder.tv_pressure.setText(Weather.getPressureAutoConvert(pressure, MyUnit.PRESSURE));
        holder.tv_humidity.setText(Weather.getHumidityAutoConvert(humidity));
        holder.tv_uvi.setText(Weather.getUviAutoCovert(uvi));

        holder.tv_sunrise.setText(Weather.getTimeFormatAutoConvert(sunrise, MyUnit.TIME_FORMAT));
        holder.tv_sunset.setText(Weather.getTimeFormatAutoConvert(sunset, MyUnit.TIME_FORMAT));

      } catch (JSONException e) {
        e.printStackTrace();
      }
  }

  @Override
  public int getItemCount() {
    if(arrayList != null){
      return arrayList.size();
    }
    return 0;
  }

  class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tv_weather_description, tv_wind_speed, tv_temp_max_min,
            tv_precipitation, tv_pop, tv_wind, tv_pressure,
            tv_humidity, tv_uvi, tv_sunrise, tv_sunset;
    ImageView ic_weather;


    public MyViewHolder(@NonNull @NotNull View itemView) {
      super(itemView);

      mapping(itemView);

    }
    private void mapping(View itemView) {
      tv_weather_description = (TextView) itemView.findViewById(R.id.tv_weather_description);
      tv_wind_speed = (TextView) itemView.findViewById(R.id.tv_wind_speed);
      tv_temp_max_min = (TextView) itemView.findViewById(R.id.tv_temp_max_min);
      ic_weather = (ImageView) itemView.findViewById(R.id.ic_weather);

      tv_precipitation = (TextView) itemView.findViewById(R.id.tv_precipitation);
      tv_pop = (TextView) itemView.findViewById(R.id.tv_pop);
      tv_wind = (TextView)itemView.findViewById(R.id.tv_wind);
      tv_pressure = (TextView)itemView.findViewById(R.id.tv_pressure);
      tv_humidity = (TextView)itemView.findViewById(R.id.tv_humidity);
      tv_uvi = (TextView) itemView.findViewById(R.id.tv_uvi);
      tv_sunrise = (TextView) itemView.findViewById(R.id.tv_sunrise);
      tv_sunset = (TextView)itemView.findViewById(R.id.tv_sunset);
    }
  }

}
