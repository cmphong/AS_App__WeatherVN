package com.C_M_P.weathervn;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.C_M_P.weathervn.DataObject.CountryObj;
import com.C_M_P.weathervn.DataObject.DailyObj;
import com.C_M_P.weathervn.Fragments.FragmentChart;
import com.C_M_P.weathervn.Fragments.FragmentCurrent;
import com.C_M_P.weathervn.Fragments.FragmentDailyDetail;
import com.C_M_P.weathervn.Fragments.FragmentHourly;
import com.C_M_P.weathervn.Fragments.FragmentOthers;
import com.C_M_P.weathervn.Fragments.FragmentDailyList;
import com.C_M_P.weathervn.SettingActivity.MainSettingActivity;
import com.C_M_P.weathervn.SettingActivity.UnitsSettingActivity;
import com.C_M_P.weathervn.constant.MyUnit;
import com.C_M_P.weathervn.middleware.InterfaceDetail;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SSLContext;

/**
* API: https://rapidapi.com/community/api/open-weather-map/<br />
* docs: https://openweathermap.org/api/
* <p />
* https://home.openweathermap.org/api_keys
* <p />
* weather icon code:<br />
* https://openweathermap.org/weather-conditions<br />
* <p style="color: '#ffff00'; font-size: '12px'">
*   Open Call API
* </p>
* https://api.openweathermap.org/data/2.5/onecall?lat=10.768614&lon=106.615063&appid={APIKey}&units=metric<br>
* Docs: https://openweathermap.org/api/one-call-api#example<br />
* <p style="color: '#ffff00'; font-size: '12px'">
*   Forecast 5 day / 3-hour step
* </p>
* https://api.openweathermap.org/data/2.5/forecast?lat=10.768614&lon=106.615063&appid={APIKey}<br />
* <ul>
*   <li>latitude: 10.768487</li>
*   <li>longitude: 106.6419394</li>
*   <li>exclude: minutely, hourly, daily, alert</li>
*   <li>appid: xxxxxxxxxxx</li>
*   <li>units: metric</li>
* </ul>
* <p style="color: '#ffff00'; font-size: '12px'">
*  List of city ID
* </p>
* <ul>
*   <li>Docs: https://openweathermap.org/current</li>
*   <li>Download file 'city.list.json.gz': http://bulk.openweathermap.org/sample/</li>
* </ul>
*
**/

// TODO
//    [ ] handle line "Precipitation will end within 18 minutes"
//    [ ] icon wind_speed
//    [x] process bar
//    [x] chart
//    [ ] performance viewpager
//    [ ] error on line 49 FragmentChart.java when re-render
//    [x] remove method static re-render
public class MainActivity extends AppCompatActivity implements InterfaceDetail {
    MyBroadcastReceiver myBroadcastReceiver;

    public static WeakReference<MainActivity> weakReference;

    final String    TEMPERATURE = "TEMPERATURE",
                    WIND_SPEED = "WIND_SPEED",
                    PRESSURE = "PRESSURE",
                    PRECIPITATION = "PRECIPITATION",
                    DISTANCE = "DISTANCE",
                    TIME_FORMAT = "TIME_FORMAT",

                    FIRSTSTART = "firstStart";

    TextView tv_Search;
    ImageButton ic_setting;

    ProgressBar progressBar;
    LinearLayout wrapper, linearSearch;


    FragmentCurrent fragmentCurrent;
    FragmentChart fragmentChart;
    FragmentOthers fragmentOthers;
    FragmentHourly fragmentHourly;
    FragmentDailyList fragmentDailyList;
    FragmentDailyDetail fragmentDailyDetail;

    private String apikey_locationiq = "";
    private String apikey_openweather = "";

    private final String latLondon = "51.50739021283517", lonLondon = "-0.1277409798053237";
    private String lat, lon;

//    private String exclude = "minutely";
    private String units   = "metric";
    private String url     = null;

    final int MY_REQUEST_CODE_COUNTRY = 44;
    boolean firstStart;
    FusedLocationProviderClient fusedLocationProviderClient;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor spEditor;

    // INTENT - RESULT ========================================
    // Xử lý kết quả phân quyền (Allow hay Deny) từ activity SearchActivity
    // sau khi nhấn vào "Find my Location"
    CountryObj countryObj;
    ActivityResultLauncher<Intent>  mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){

                @Override
                public void onActivityResult(ActivityResult result) {
                    // Nếu chọn Allow
                    if(result.getResultCode() == Activity.RESULT_OK){
                        if(result.getData().getSerializableExtra("countryObjFromSearchActivity") != null){
                            countryObj = (CountryObj) result.getData().getSerializableExtra("countryObjFromSearchActivity");
                        }
                        if(result.getData().getBooleanExtra("isRequestPermission", true)){
                            // START REQUEST PERMISSION
                            ActivityCompat.requestPermissions(
                                    MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_REQUEST_CODE_COUNTRY);
                        };
                    }else{ // Nếu chọn Deny
                        Logdln(Activity.RESULT_CANCELED+"", 129);
                    };

                }
            }
    );


    @Override
    protected void onStart() {
        super.onStart();
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weakReference = new WeakReference<>(MainActivity.this);
        mapping();

        apikey_openweather = getResources().getString(R.string.APIKey_openweather);
        apikey_locationiq = getResources().getString(R.string.APIKey_locationiq);
        // inital for get location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        intialSharedPreferences();

        linearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                mActivityResultLauncher.launch(intent);
            }
        });

        ic_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainSettingActivity.class);
                mActivityResultLauncher.launch(intent);
            }
        });

        initialFragment();

        setVisibleAnimate();
        if(firstStart){
            // START REQUEST PERMISSION
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_REQUEST_CODE_COUNTRY);

            spEditor.putBoolean(FIRSTSTART, false);
            spEditor.apply();
        }else{
            String spLat = sharedPreferences.getString("lat", "");
            String spLon = sharedPreferences.getString("lon", "");
            String spName = sharedPreferences.getString("name", "");

            if(spLat.equals("") && spLon.equals("")){
                if(ActivityCompat.checkSelfPermission(MainActivity.this,  Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED) {
                        getLocation();
                        url = null;
                    }else{ // DENY from PERMISSION REQUEST AFTER USER SELECT
                        setVisibleAnimate();
                        getWeatherData(latLondon, lonLondon); // default: London, UK
                        tv_Search.setText("London, UK");
                    }

            }else{
//                setVisibleAnimate();
                getWeatherData(spLat, spLon);
                tv_Search.setText(spName);
            };
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        // M - là Android API 23
        // Nếu version Android thiết bị < 23 thì không làm gì hết
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }

        // ALLOW from PERMISSION REQUEST AFTER USER SELECT
        if(ActivityCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation();
            url = null;
        }else{ // DENY from PERMISSION REQUEST AFTER USER SELECT
//            setVisibleAnimate();
            getWeatherData(lat, lon); // default: London, UK
            tv_Search.setText("London, UK");
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(url != null){
            if (countryObj != null) {
                // from Intent return
                lat = countryObj.getLat();
                lon = countryObj.getLon();

                // Put to SharedPreferences
                spEditor.putString("lat", lat);
                spEditor.putString("lon", lon);
                spEditor.putString("name", countryObj.getName());
                spEditor.apply();

                // Get Weather data
//                setVisibleAnimate();
                getWeatherData(lat, lon);
                tv_Search.setText(countryObj.getName());
                countryObj = null;
            }
        }else{
            // London, UK
            url = "https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&appid="+apikey_openweather+"&units="+units+"";
        }

    }

    public static MainActivity getMyIntanceActivity(){
        return weakReference.get();
    }
    public String getLatitude(){
        return this.lat;
    }
    public String getLongtitude(){
        return this.lon;
    }


    public void setVisibleAnimate() {
        progressBar.setVisibility(View.VISIBLE);
        wrapper.setVisibility(View.INVISIBLE);
    }

    public void setInvisibleAnimate() {
        progressBar.setVisibility(View.GONE);
        wrapper.setVisibility(View.VISIBLE);
    }

    // GET LOCATION WITH GPS & PROVIDER NETWORK
    private void getLocation() {

        // USER SELECT "DENY"
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                // DENIED => London, UK
                setVisibleAnimate();
                lat = latLondon;
                lon = lonLondon;
                getWeatherData(lat, lon);
                tv_Search.setText("London, UK");

                return;
        }

        // USER SELECT "ALLOW" => GET LATITUDE & LONGTITUDE
        fusedLocationProviderClient
                .getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        // initialize location
                        Location location = task.getResult();
                        if (location != null) {

                            try {
                                // initialize geoCoder
                                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                // initialize address list
                                List<Address> addressList = geocoder.getFromLocation(
                                        location.getLatitude(),
                                        location.getLongitude(),
                                        1
                                );

                                // Get lat & lon
//                                String lat = String.valueOf(addressList.get(0).getLatitude());
//                                String lon = String.valueOf(addressList.get(0).getLongitude());
                                lat = String.valueOf(addressList.get(0).getLatitude());
                                lon = String.valueOf(addressList.get(0).getLongitude());

                                // Save SharedPreferences
                                spEditor.putString("lat", "");
                                spEditor.putString("lon", "");
                                spEditor.apply();

                                setVisibleAnimate();
                                getWeatherData(lat, lon);
                                setTextForTextView(tv_Search, lat, lon);
//                                tv_Search.setText("Houston");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void getWeatherData(String lat, String lon){
//        progressBar.setVisibility(View.VISIBLE);
//        wrapper.setVisibility(View.INVISIBLE);
//        setVisibleAnimate();

        url = "https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&appid="+apikey_openweather+"&units="+units+"";
        // Khởi tạo hàng đợi của request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Khởi tạo kiểu của request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

//                        progressBar.setVisibility(View.GONE);
//                        wrapper.setVisibility(View.VISIBLE);
                        setInvisibleAnimate();

                        // transfer data to Fragments
                        try {
                            // PASS DATA TO SETTING
                            UnitsSettingActivity.setData(response);

                            // PASS DATA TO FRAGMENT
                            fragmentCurrent.setValueElements(response);
                            fragmentChart.setValueElements(response);
                            fragmentOthers.setValueElements(response);
                            fragmentHourly.setValueElements(response);
                            fragmentDailyList.setValueElements(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logdln(error.toString(), 100);
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }


    private void setTextForTextView(TextView textView, String lat, String lon){

            StringBuilder result = new StringBuilder();

            String url =
                    "https://us1.locationiq.com/v1/reverse.php"+
                            "?key=" +apikey_locationiq+
                            "&lat=" +lat+
                            "&lon=" +lon+
                            "&format=json";
            // Khởi tạo hàng đợi của request
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            // Khởi tạo kiểu của request
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject address = response.getJSONObject("address");
                                if(address.has("city_block")){
                                    result.append(address.getString("city_block") + ", ");
                                }
                                if(address.has("suburd")){
                                    result.append(address.getString("suburb") + ", ");
                                }
                                if(address.has("town")){
                                    result.append(address.getString("town") + ", ");
                                }
                                if(address.has("city")){
                                    result.append(address.getString("city") + ", ");
                                }
                                if(address.has("state")){
                                    result.append(address.getString("state") + ", ");
                                }
                                if(address.has("country")){
                                    result.append(address.getString("country"));
                                }
                                textView.setText(result);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Logdln("get Error", 415);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showToast(error.toString());
                            Logdln(error.toString(), 413);
                        }
                    }
            );

            requestQueue.add(jsonObjectRequest);

    }

    private void initialFragment() {
        fragmentCurrent     = new FragmentCurrent();
        fragmentChart       = new FragmentChart();
        fragmentOthers      = new FragmentOthers();
        fragmentHourly      = new FragmentHourly();
        fragmentDailyList   = new FragmentDailyList();
        fragmentDailyDetail = new FragmentDailyDetail();

        // nạp Fragment vào các <LinearLayout> đã định vị trong "activity_main.xml"
        loadFragment(R.id.linear_current, fragmentCurrent);
        loadFragment(R.id.linear_chart  , fragmentChart);
        loadFragment(R.id.linear_others , fragmentOthers);
        loadFragment(R.id.linear_hourly , fragmentHourly);
        loadFragment(R.id.linear_daily  , fragmentDailyList);
    }

    private void intialSharedPreferences() {
        sharedPreferences = getSharedPreferences("dataUnitsSetting", MODE_PRIVATE);
        spEditor = sharedPreferences.edit();

        firstStart           = sharedPreferences.getBoolean(FIRSTSTART, true);

        MyUnit.TEMP          = sharedPreferences.getString(TEMPERATURE, MyUnit.TEMP);
        MyUnit.SPEED         = sharedPreferences.getString(WIND_SPEED, MyUnit.SPEED);
        MyUnit.PRESSURE      = sharedPreferences.getString(PRESSURE, MyUnit.PRESSURE);
        MyUnit.PRECIPITATION = sharedPreferences.getString(PRECIPITATION, MyUnit.PRECIPITATION);
        MyUnit.DISTANCE      = sharedPreferences.getString(DISTANCE, MyUnit.DISTANCE);
        MyUnit.TIME_FORMAT   = sharedPreferences.getString(TIME_FORMAT, MyUnit.TIME_FORMAT);
        lat = sharedPreferences.getString("lat", "51.50739021283517");
        lon = sharedPreferences.getString("lon", "-0.1277409798053237");

    }

    private void loadFragment(int fragment_container, Fragment fragment){
       getSupportFragmentManager().beginTransaction()
                .replace(fragment_container, fragment)
                .commit();
    }

    /**
     * - Ta override lại method của interface InterfaceDetail
     * <br>vì ở "MainActivity.java" này có chứa container R.id.linear_daily
     * <br>nên ta có thể replace fragment LinearDailyList và LinearDailyDetail
     * <br>- Và làm trung gian để chuyển dữ liệu qua lại giữa 2 fragment này
     *
     */
    @Override // implement InterfaceDetail
    public void showDetail(Fragment fragment, int index, ArrayList<DailyObj> arrayList) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putSerializable("arrayList", (Serializable) arrayList);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.linear_daily, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public static void startNetworkErrorActivity(){
        Intent intent = new Intent(getMyIntanceActivity(), NetworkErrorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getMyIntanceActivity().startActivity(intent);
    }

    private void mapping() {
        tv_Search = (TextView) findViewById(R.id.tv_search);
        ic_setting  = (ImageButton) findViewById(R.id.ic_setting);
        progressBar = findViewById(R.id.progress_circular);
        wrapper     = findViewById(R.id.wrapper);
        linearSearch = findViewById(R.id.linear_search);
    }


    // =========================================================
    public static void Logd(String str){
        Log.d("Log.d", str + "\n === MainActivity.java ==============================");
    }
    public static void Logdln(String str, int n){
        Log.d("Log.d", "=== MainActivity.java - line: " + n + " ==============================\n" + str);
    }
    public void showToast(String str){
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}