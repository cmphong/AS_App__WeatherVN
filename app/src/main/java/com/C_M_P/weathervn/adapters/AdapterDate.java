package com.C_M_P.weathervn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.C_M_P.weathervn.DataObject.DailyObj;
import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.middleware.InterfaceDateItemSelected;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterDate extends RecyclerView.Adapter<AdapterDate.ViewHolder> {
  Context context;
  int index;
  ArrayList<DailyObj> arrayList;
  InterfaceDateItemSelected listener;

  public AdapterDate(Context context, InterfaceDateItemSelected listener) {
    this.context = context;
    this.listener = listener;
  }

  public void setData(ArrayList<DailyObj> arrayList){
    this.arrayList = arrayList;
    notifyDataSetChanged(); // re-render
  }
  public void setIndex(int index){
    this.index = index;
  }

  // MAPPING
  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tv_date, tv_day;
    LinearLayout wrapper;

    public ViewHolder(@NonNull @NotNull View itemView) {
      super(itemView);
      wrapper = itemView.findViewById(R.id.wrapper);
      tv_day = itemView.findViewById(R.id.tv_day);
      tv_date = itemView.findViewById(R.id.tv_date);

      wrapper.setOnClickListener(this);

    }


    @Override // InterfaceDateItemSelected
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition());
    }
  }

  @NonNull
  @NotNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
    View v = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.item_horizontal_date, parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull @NotNull AdapterDate.ViewHolder holder, int position) {
      DailyObj dailyitem = arrayList.get(position);
      JSONObject item = dailyitem.getDaily_item();
      try {
        int dt = item.getInt("dt");

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");

        holder.tv_day.setText(dayFormat.format(dt*1000L));
        holder.tv_date.setText(dateFormat.format(dt*1000L));
      } catch (JSONException e) {
        e.printStackTrace();
      }

//    background="@drawable/card_date"
      if(index == position){
        holder.wrapper.setBackgroundResource(R.drawable.card_date);
      }

  }

  @Override
  public int getItemCount() {
    if(arrayList != null){
      return arrayList.size();
    }
    return 0;
  }
}
