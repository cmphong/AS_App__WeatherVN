package com.C_M_P.weathervn.middleware;

import android.graphics.*;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.C_M_P.weathervn.MyCustomXAxisRenderer;
import com.C_M_P.weathervn.R;
import com.C_M_P.weathervn.constant.MyUnit;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LinearChartCubic extends Fragment {
    LineChart chart;
    List<String> minuteArrX;
    int length = 8;
    float[] dataObj = new float[length];

    ArrayList<Integer> hourly_dt, minutely_dt;
    ArrayList<Float> hourly_pop, hourly_rain_1h, minutely_precipitation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_my_cubic_chart, container, false);

        // INITIAL VARIABLE
        minuteArrX              = new ArrayList<>();
        hourly_dt               = new ArrayList<>();
        hourly_pop              = new ArrayList<>();
        hourly_rain_1h          = new ArrayList<>();
        minutely_dt             = new ArrayList<>();
        minutely_precipitation  = new ArrayList<>();

        handleReceiveData();

        chart   = (LineChart) v.findViewById(R.id.chart);

        for(int i = 0 ; i <= 24 ; i++){
            String tmp = "0"+i;
            String hour = tmp.substring(tmp.length() - 2);
            minuteArrX.add(hour + ":00");
        }

        for(int i = 0 ; i < dataObj.length ; i++){
            dataObj[i] = hourly_rain_1h.get(i);
        }
        // [1] Wrap each data object you have into an Entry object
        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 1 ; i < dataObj.length ; i++) {
            // turn your data into Entry objects
            // param1: count điểm sẽ hiển thị
            // param2: là giá trị của điểm đó
            // => hiển thị 6 data
            entries.add(new Entry(i, dataObj[i]));
        }

        // [2] Add List<Entry> to LineDataSet
        LineDataSet set1 = new LineDataSet(entries, ""); // add entries to dataset

        // [3] Add LineDataSet to LineData
        LineData lineData = new LineData(set1);
        chart.setData(lineData);
        chart.invalidate(); // refresh

        styleForChart(chart, set1);

        return v;
    }

    private void styleForChart(LineChart chart, LineDataSet set1) {
        // GET WIDTH / HEIGHT
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();

        float textSize = 8f;

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setForm(Legend.LegendForm.EMPTY);
        // UNUSED
//        l.setTypeface(tfLight);
//        l.setTextSize(11f);
//        l.setTextColor(Color.RED);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setYOffset(5f);

        set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(true);
        set1.setDrawCircles(false);
        set1.setHighLightColor(Color.GREEN);
        set1.setFillDrawable(getResources().getDrawable(R.drawable.cyan_fade));
        set1.setDrawHorizontalHighlightIndicator(false);
        //HIDE LINE
        set1.setLineWidth(.2f); // hidden line
        set1.enableDashedLine(0, 1, 0);
        // HIDE TEXT VALUE
        set1.setDrawValues(false);
        // UNUSED
//        set1.setValueTextColor(Color.RED);
//        set1.setColor(Color.WHITE);
        // point indicate
//        set1.setCircleRadius(4f);
//        set1.setCircleColor(Color.WHITE);
//        set1.setFillColor(Color.parseColor("#89ecda"));
//        set1.setFillAlpha(128);

        chart.setScaleEnabled(false);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.setVisibleXRangeMaximum(10);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setExtraRightOffset(-20);
// CUSTOM MULTI-LINE LABEL XAXIS ===============================================================
        chart.setXAxisRenderer(new MyCustomXAxisRenderer(
                chart.getViewPortHandler(),
                chart.getXAxis(),
                chart.getTransformer(YAxis.AxisDependency.RIGHT)
        ));


        // UNUSED
//        chart.setBackgroundColor(Color.parseColor("#68F1AF"));
//        chart.setBackgroundColor(Color.rgb(104, 241, 175));
//        chart.setMaxHighlightDistance(300);

        XAxis x = chart.getXAxis();
        YAxis left = chart.getAxisLeft();
        YAxis right = chart.getAxisRight();

        x.setLabelCount(8, true);
        x.setDrawGridLines(false);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setTextColor(Color.parseColor("#4b4b4b"));
        x.setTextSize(textSize);
        x.setAxisMinimum(0);
        x.setDrawAxisLine(false);
        x.setCenterAxisLabels(true);
        XAxis_Hourly_Pop_Custom xAxis_hourly_pop_custom = new XAxis_Hourly_Pop_Custom(hourly_dt, hourly_pop);
        x.setValueFormatter(xAxis_hourly_pop_custom);
        x.setYOffset(12f);

//        left.setTypeface(tfLight); // set font
        left.setLabelCount(3);
        left.setTextColor(Color.parseColor("#4b4b4b"));
        left.setTextSize(textSize);
        // GET WIDTH CHART (dp)
        float dpChartWidth = chart.getWidth() / displayMetrics.density;
        left.setXOffset(0f);
        left.setYOffset(-6f);
        left.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
//         horizontal grid lines
        left.setAxisLineColor(Color.WHITE);
        left.enableGridDashedLine(10f, 10f, 0f);
        left.setDrawAxisLine(false);

        left.setAxisMinimum(0);
        right.setAxisMinimum(0);

        // disable dual axis (only use LEFT axis)
//        right.setEnabled(false);
        right.setTextColor(Color.parseColor("#4b4b4b"));
        right.setTextSize(textSize);
        right.setLabelCount(3);
        right.setDrawAxisLine(false);
        right.setDrawGridLines(false);
        right.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if(MyUnit.PRECIPITATION.equals(MyUnit.PRECIPITATION_in)){
                    return "in/h";
                }
                return "mm/h";
            }
        });
        // GET MEASURE WIDTH-HEIGHT OF LINECHART AFTER RENDER ================================
        chart.post(new Runnable() {
            @Override
            public void run() {
                String label = right.getLongestLabel();
                Paint paint = new Paint();

                // GET WIDTH CHART (dp)
                float dpChartWidth = chart.getWidth() / displayMetrics.density;
                // GET WIDTH LABEL
                float dpLabelWidth = paint.measureText(label);

                right.setXOffset(dpChartWidth*(-1) + 30);
                right.setYOffset(6f);

                chart.moveViewToX(50f);
                chart.invalidate(); // refresh
            }
        });
    }

    private void handleReceiveData()  {
        Bundle bundle = getArguments();

        if(bundle != null){
            String strMinutelyArr = bundle.getString("minutelyArr");
            String strHourlyArr = bundle.getString("hourlyArr");
            try {
                // HANDLE MINUTELY ==============================
                JSONArray jsonArrayMinutely = new JSONArray(strMinutelyArr);
                for(int i = 0 ; i < jsonArrayMinutely.length() ; i++){
                    JSONObject index = jsonArrayMinutely.getJSONObject(i);

                    int dateTime = index.getInt("dt");
                    float precipitation = (float) index.getDouble("precipitation");
                    minutely_dt.add(dateTime);
                    minutely_precipitation.add(precipitation);
                }
                // HANDLE HOURLY ==============================
                JSONArray jsonArrayHourly = new JSONArray(strHourlyArr);
                for(int i = 0 ; i < jsonArrayHourly.length() ; i++){
                    JSONObject index = jsonArrayHourly.getJSONObject(i);
                    int dateTime = index.getInt("dt");

                    hourly_dt.add(dateTime);

                    if(index.has("pop")){
                        float pop = (float) index.getDouble("pop");
                        hourly_pop.add(pop);
                    }else{
                        hourly_pop.add(0f);
                    }

                    if(index.has("rain")){
                        JSONObject jsonObjectRain = index.getJSONObject("rain");
                        float rain1h = (float) jsonObjectRain.getDouble("1h");
                        if(MyUnit.PRECIPITATION.equals(MyUnit.PRECIPITATION_in)){
                            // 1 inch = 25.4 mm
                            hourly_rain_1h.add((float) (rain1h / 25.4));
                        }else{
                            hourly_rain_1h.add(rain1h);
                        }
                    }else{
                        hourly_rain_1h.add(0f);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Logdln("Bundle is null", 214);
        }
    }


    // =========================================================
    public void Logd(String str){
        Log.d("Log.d", str + "\n === LinearChartCubi.java ==============================");
    }
    public void Logdln(String str, int n){
        Log.d("Log.d", str + "\n === LinearChartCubi.java - line: " + n + " ==============================");
    }
    public void showToast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
}
