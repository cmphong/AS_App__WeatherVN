package com.C_M_P.weathervn.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.C_M_P.weathervn.DataObject.CurrentObj;
import com.C_M_P.weathervn.Fragments.FragmentHourly;
import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.constant.MyUnit;
import com.C_M_P.weathervn.middleware.MyGlide;
import com.C_M_P.weathervn.middleware.Weather;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterCurrent extends RecyclerView.Adapter<AdapterCurrent.ViewHolder> {

  FragmentHourly context;
  ArrayList<CurrentObj> arrayList;

  public AdapterCurrent(FragmentHourly context, ArrayList<CurrentObj> arrayList) {
    this.context = context;
    this.arrayList = arrayList;
  }

  @NonNull
  @NotNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(context.getContext());
    View v = layoutInflater.inflate(R.layout.item_horizontal_hourly, parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull AdapterCurrent.ViewHolder holder, int position) {
    CurrentObj currentObj = arrayList.get(position);
    int dt = currentObj.getDateTime();
    String ic = currentObj.getIcon();
    float temp = currentObj.getTemp();

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    holder.tv_hourly.setText(sdf.format(dt*1000L));
    MyGlide.loadIcon(context.getContext(), ic, holder.iv_icon);
    holder.tv_temp.setText(Weather.getDegreesAutoConvert(temp, MyUnit.TEMP, true));
  }

  @Override
  public int getItemCount() {
    return arrayList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tv_hourly, tv_temp;
    ImageView iv_icon;


    public ViewHolder(@NonNull @NotNull View itemView) {
      super(itemView);

      tv_hourly = (TextView) itemView.findViewById(R.id.tv_hourly);
      iv_icon = (ImageView) itemView.findViewById(R.id.imv_ic);
      tv_temp = (TextView) itemView.findViewById(R.id.tv_temp);
    }
  }
}
