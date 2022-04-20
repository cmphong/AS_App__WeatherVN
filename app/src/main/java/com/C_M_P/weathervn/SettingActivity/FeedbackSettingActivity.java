package com.C_M_P.weathervn.SettingActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.C_M_P.weathervn.MainActivity;
import com.C_M_P.weathervn.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import papaya.in.sendmail.SendMail;

public class FeedbackSettingActivity extends AppCompatActivity {
    ImageButton ic_back;

    LinearLayout linear_group_icon, linear_anim,
            linear_clear_sky, linear_few_clouds, linear_overcast_clouds,
            linear_drizzle, linear_rain, linear_shower_rain,
            linear_thunderstorm, linear_snow, linear_mist;
    View view_anim;
    ObjectAnimator animationX;
    ObjectAnimator animationY;
    float density;
    float w = 0, h = 0;
    final int myTime = 500;


    private final String    CLEAR_SKY = "Clear Sky",
                            FEW_CLOUDS = "Few clouds",
                            OVERCAST_CLOUDS = "Overcast clouds",
                            DRIZZLE = "Drizzle",
                            RAIN = "Rain",
                            SHOWER_RAIN = "Shower rain",
                            THUNDERSTORM = "Thunderstorm",
                            SNOW = "Snow",
                            MIST = "Mist";
    private boolean PERSONAL_FEELING,
                    OWN_WEATHER_STATION_OR_DEVICES,
                    LOCAL_WEATHER_PROVIDER,
                    GLOBAL_WEATHER_PROVIDER,
                    OTHER;

    private String typeSky = FEW_CLOUDS;
    TextView tv_type_sky, tv_temperature, tv_wind;
    SeekBar sb_temperature, sb_wind;
    int temperarture = 15;
    String wind = "Light";
    EditText edt_email, edt_message;
    CheckBox cb_1, cb_2, cb_3, cb_4, cb_5;
    Button btn_send_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_feedback);

        mapping();


        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // SET DIMENSION VIEW_ANIM ==========================================
        density = getResources().getDisplayMetrics().density;
        linear_group_icon.post(new Runnable() {
            @Override
            public void run() {
                w = linear_group_icon.getWidth(); // pixel
                h = linear_group_icon.getHeight(); // pixel
                Logdln("Width: "+ w +" - Height: "+ h, 36);

                int mMargin = (int) (5 * density);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) w, (int) h);
                layoutParams.setMargins(mMargin, mMargin, mMargin, mMargin);
                linear_anim.setLayoutParams(layoutParams);

                view_anim.setLayoutParams(new LinearLayout.LayoutParams((int) w/3, (int) h/3));
                runAnimation(w/3, 0f);
            }
        });


        View.OnClickListener onClickTypeSky = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.feedback_linear_clear_sky: // 1111111111
                        runAnimation(0f, 0f);
                        typeSky = CLEAR_SKY;
                        tv_type_sky.setText(CLEAR_SKY);
                        break;

                    case R.id.feedback_linear_few_clouds: // 2222222222
                        runAnimation(w/3, 0f * density);
                        typeSky = FEW_CLOUDS;
                        tv_type_sky.setText(FEW_CLOUDS);
                        break;

                    case R.id.feedback_linear_overcast_clouds: // 333333333
                        runAnimation(w/3 * 2, 0f);
                        typeSky = OVERCAST_CLOUDS;
                        tv_type_sky.setText(OVERCAST_CLOUDS);
                        break;

                    case R.id.feedback_linear_drizzle: // 4444444444
                        runAnimation(0f, h/3);
                        typeSky = DRIZZLE;
                        tv_type_sky.setText(DRIZZLE);
                        break;

                    case R.id.feedback_linear_rain: // 555555555
                        runAnimation(w/3, h/3);
                        typeSky = RAIN;
                        tv_type_sky.setText(RAIN);
                        break;

                    case R.id.feedback_linear_shower_rain: // 6666666666
                        runAnimation(w/3 * 2, h/3);
                        typeSky = SHOWER_RAIN;
                        tv_type_sky.setText(SHOWER_RAIN);
                        break;

                    case R.id.feedback_linear_thunderstorm: // 7777777777
                        runAnimation(0f, h/3 * 2);
                        typeSky = THUNDERSTORM;
                        tv_type_sky.setText(THUNDERSTORM);
                        break;

                    case R.id.feedback_linear_snow: // 8888888888
                        runAnimation(w/3, h/3 * 2);
                        typeSky = SNOW;
                        tv_type_sky.setText(SNOW);
                        break;

                    case R.id.feedback_linear_mist: // 9999999999
                        runAnimation(w/3 * 2, h/3 * 2);
                        typeSky = MIST;
                        tv_type_sky.setText(MIST);
                        break;

                }
            }
        };
        linear_clear_sky.setOnClickListener(onClickTypeSky);
        linear_few_clouds.setOnClickListener(onClickTypeSky);
        linear_overcast_clouds.setOnClickListener(onClickTypeSky);
        linear_drizzle.setOnClickListener(onClickTypeSky);
        linear_rain.setOnClickListener(onClickTypeSky);
        linear_shower_rain.setOnClickListener(onClickTypeSky);
        linear_thunderstorm.setOnClickListener(onClickTypeSky);
        linear_snow.setOnClickListener(onClickTypeSky);
        linear_mist.setOnClickListener(onClickTypeSky);

        View.OnClickListener onClickItemDataSource = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.feedback_cb_1:
                        if(((CheckBox) v).isChecked()){
                            PERSONAL_FEELING = true;
                        }else{
                            PERSONAL_FEELING = false;
                        }
                        break;
                    case R.id.feedback_cb_2:
                        if(((CheckBox) v).isChecked()){
                            OWN_WEATHER_STATION_OR_DEVICES = true;
                        }else{
                            OWN_WEATHER_STATION_OR_DEVICES = false;
                        }
                        break;
                    case R.id.feedback_cb_3:
                        if(((CheckBox) v).isChecked()){
                            LOCAL_WEATHER_PROVIDER = true;
                        }else{
                            LOCAL_WEATHER_PROVIDER = false;
                        }
                        break;
                    case R.id.feedback_cb_4:
                        if(((CheckBox) v).isChecked()){
                            GLOBAL_WEATHER_PROVIDER = true;
                        }else{
                            GLOBAL_WEATHER_PROVIDER = false;
                        }
                        break;
                    case R.id.feedback_cb_5:
                        if(((CheckBox) v).isChecked()){
                            OTHER = true;
                        }else{
                            OTHER = false;
                        }
                        break;
                }
            }
        };
        cb_1.setOnClickListener(onClickItemDataSource);
        cb_2.setOnClickListener(onClickItemDataSource);
        cb_3.setOnClickListener(onClickItemDataSource);
        cb_4.setOnClickListener(onClickItemDataSource);
        cb_5.setOnClickListener(onClickItemDataSource);

        sb_temperature.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                temperarture = progress;
                tv_temperature.setText(String.valueOf(progress) +"ºC");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_wind.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress){
                    case 1: wind = "Light"; break;
                    case 2: wind = "Moderate"; break;
                    case 3: wind = "Strong"; break;
                }
                tv_wind.setText(wind);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat = MainActivity.getMyIntanceActivity().getLatitude();
                String lon = MainActivity.getMyIntanceActivity().getLongtitude();
                long date_time = new Date().getTime();
                SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH-mm-ss");
                String content = "";

                content +=  "\nLatitude: " + (lat.length() == 0 ? "51.50739021283517" : lat) +
                            "\nLongtitude: " + (lon.length() == 0 ? "-0.1277409798053237" : lon) +
                            "\nDate time: " + df.format(date_time) +
                            "\nSky: " + typeSky +
                            "\nTemperature: " + temperarture +
                            "\nWind: " + wind +
                            "\nEmail: " + edt_email.getText().toString() +
                            "\nMessage: " + edt_message.getText().toString() +
                            "\nData source: " + PERSONAL_FEELING + ", " +
                                                OWN_WEATHER_STATION_OR_DEVICES + ", " +
                                                LOCAL_WEATHER_PROVIDER + ", " +
                                                GLOBAL_WEATHER_PROVIDER + ", " +
                                                OTHER;
                Logdln(content, 269);
                showToast("Your feedback sent, Thank you!");

                SendMail mail = new SendMail(
                        getResources().getString(R.string.sender_feedback_email),
                        getResources().getString(R.string.sender_feedback_password),
                        getResources().getString(R.string.receiver_feedback_email),
                        "Feedback from Weather App",
                        content
                );
                mail.execute();
            }
        });
    }


    private void runAnimation(float Xpx, float Ypx){
        animationX = ObjectAnimator.ofFloat(
                view_anim,
                "translationX",
                Xpx // pixel unit = dp * density
        );
        animationX.setDuration(myTime);
        animationX.start();
        animationY = ObjectAnimator.ofFloat(
                view_anim,
                "translationY",
                Ypx // pixel unit = dp * density
        );
        animationY.setDuration(myTime);
        animationY.start();

    }

    private void mapping() {
        ic_back = (ImageButton) findViewById(R.id.feedback_ic_back);

        linear_group_icon = (LinearLayout) findViewById(R.id.feedback_linear_group_icon);
        linear_anim = (LinearLayout) findViewById(R.id.feedback_linear_anim);
        linear_clear_sky = (LinearLayout) findViewById(R.id.feedback_linear_clear_sky);
        linear_few_clouds = (LinearLayout) findViewById(R.id.feedback_linear_few_clouds);
        linear_overcast_clouds = (LinearLayout) findViewById(R.id.feedback_linear_overcast_clouds);
        linear_drizzle = (LinearLayout) findViewById(R.id.feedback_linear_drizzle);
        linear_rain = (LinearLayout) findViewById(R.id.feedback_linear_rain);
        linear_shower_rain = (LinearLayout) findViewById(R.id.feedback_linear_shower_rain);
        linear_thunderstorm = (LinearLayout) findViewById(R.id.feedback_linear_thunderstorm);
        linear_snow = (LinearLayout) findViewById(R.id.feedback_linear_snow);
        linear_mist = (LinearLayout) findViewById(R.id.feedback_linear_mist);

        view_anim = (View) findViewById(R.id.feedback_view_anim_sky_icon);
        tv_type_sky = findViewById(R.id.feedback_tv_type_sky);
        tv_temperature = findViewById(R.id.feedback_tv_temperature);
        tv_wind = findViewById(R.id.feedback_tv_wind);
        sb_temperature = findViewById(R.id.feedback_sb_temperature);
        sb_wind = findViewById(R.id.feedback_sb_wind);
        edt_email = findViewById(R.id.feedback_edt_email);
        edt_message = findViewById(R.id.feedback_edt_message);
        cb_1 = findViewById(R.id.feedback_cb_1);
        cb_2 = findViewById(R.id.feedback_cb_2);
        cb_3 = findViewById(R.id.feedback_cb_3);
        cb_4 = findViewById(R.id.feedback_cb_4);
        cb_5 = findViewById(R.id.feedback_cb_5);
        btn_send_feedback = findViewById(R.id.feedback_btn_send);

    }

    // =========================================================
    public void Logd(String str){
        Log.d("Log.d", "=== DifferentSettingActivity.java ==============================\n" + str);
    }
    public void Logdln(String str, int n){
        Log.d("Log.d", "=== DifferentSettingActivity.java - line: " + n + " ==============================\n" + str);
    }
    public void showToast(String str){
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

}