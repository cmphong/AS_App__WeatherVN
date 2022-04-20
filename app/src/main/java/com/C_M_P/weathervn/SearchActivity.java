package com.C_M_P.weathervn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.C_M_P.weathervn.DataObject.CountryObj;
import com.C_M_P.weathervn.adapters.AdapterCountry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
  static SearchActivity context;
  EditText editText;
  ImageButton ic_cancel;
  RelativeLayout relative_findLoaction;
  ListView listView;
  ArrayList<CountryObj> arrayList;

  final int MY_REQUEST_CODE_COUNTRY = 55;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);

    context = SearchActivity.this;


    editText = findViewById(R.id.et_search);
    ic_cancel = (ImageButton) findViewById(R.id.ic_search_cancel);
    relative_findLoaction = (RelativeLayout) findViewById(R.id.relative_findLocation);
    listView = (ListView) findViewById(R.id.listview);
    arrayList = new ArrayList<>();

    editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        String content = v.getText().toString().trim();
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            arrayList.clear();
            getCity(content);
            handled = true;
        }
        return handled;
      }
    });

    ic_cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    relative_findLoaction.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // START REQUEST PERMISSION
        MainActivity.getMyIntanceActivity().setVisibleAnimate();
        receiveDataFromAdapter(null, true);
      }
    });

  }

  private void getCity(String content){
//    String url =
//            "http://192.168.111.254/LocationApi/fetchCity.php" +
//            "?cityName=" +content+
//            "&countryName=vn";
//    String url =
//            "http://192.168.111.254/LocationApi/fetchCity.php" +
//                    "?cityName=" +content;
    String url =
            "https://us1.locationiq.com/v1/search.php"+
            "?key=pk.a7f6d53a0778ee85477ce7f9c3e2b046"+
            "&q="+ content +
            "&addressdetails=1"+
            "&format=json";



    RequestQueue requestQueue = Volley.newRequestQueue(this);
    JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            new Response.Listener<JSONArray>() {
              @Override
              public void onResponse(JSONArray response) {
                try {

                    for(int i = 0 ; i < response.length() ; i++){
                      JSONObject item = response.getJSONObject(i);
                      String  id = item.getString("place_id"),
                              name = item.getString("display_name"),
                              lat = item.getString("lat"),
                              lon = item.getString("lon");
                      JSONObject address = item.getJSONObject("address");
                      String  country = address.getString("country"),
                              country_code = address.getString("country_code");

                      arrayList.add(new CountryObj(
                              id,
                              name,
                              lat,
                              lon,
                              country,
                              country_code
                      ));
                    }

                    AdapterCountry adapter = new AdapterCountry(
                            SearchActivity.this,
                            arrayList,
                            R.layout.item_lv_country
                    );
                    listView.setAdapter(adapter);


                } catch (JSONException e) {
                  e.printStackTrace();
                }
              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Logdln("error response", 149);
              }
            }
    );

    requestQueue.add(jsonObjectRequest);
  }

  public static void receiveDataFromAdapter(CountryObj country, boolean isRequestPermission){
    Intent intent = new Intent();
    intent.putExtra("countryObjFromSearchActivity", (Serializable) country);
    intent.putExtra("isRequestPermission", isRequestPermission);
    context.setResult(RESULT_OK, intent);
    context.finish();
  }


  // =========================================================
  public void Logd(String str){
      Log.d("Log.d", "=== SearchActivity.java ==============================\n" + str);
  }
  public void Logdln(String str, int n){
      Log.d("Log.d", "=== SearchActivity.java - line: " + n + " ==============================\n" + str);
  }
  public static void LogdStatic(String str){
    Log.d("Log.d", "=== SearchActivity.java ==============================\n" + str);
  }
  public static void LogdlnStatic(String str, int n){
    Log.d("Log.d", "=== SearchActivity.java - line: " + n + " ==============================\n" + str);
  }
  public void showToast(String str){
    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
  }
}