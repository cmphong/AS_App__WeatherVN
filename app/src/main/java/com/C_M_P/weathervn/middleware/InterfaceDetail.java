package com.C_M_P.weathervn.middleware;

import androidx.fragment.app.Fragment;

import com.C_M_P.weathervn.DataObject.DailyObj;

import java.util.ArrayList;

public interface InterfaceDetail {
  void showDetail(Fragment fragment, int index, ArrayList<DailyObj> arrayListdaily);
}
