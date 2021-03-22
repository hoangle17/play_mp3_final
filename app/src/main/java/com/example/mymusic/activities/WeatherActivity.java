package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherActivity extends AppCompatActivity {
    ImageView imageViewIcon;
    TextView textViewHum, textViewC, textViewDes, textViewCity, textViewLastUpdate;
    String url = "https://api.openweathermap.org/data/2.5/weather?q=Hanoi&appid=ebeca01f868f416e3fe4ae7aacf815c2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        imageViewIcon = findViewById(R.id.iconWeather);
        textViewHum = findViewById(R.id.txtHumidity);
        textViewC = findViewById(R.id.txtC);
        textViewDes = findViewById(R.id.txtDescription);
        textViewCity = findViewById(R.id.txtCity);
        textViewLastUpdate = findViewById(R.id.txtLastUpdate);

        findWeather();
    }


    private void findWeather() {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonObject = new JSONObject(response.toString());
                    Log.i("Test", response.toString());
                    String day = jsonObject.getString("dt");
                    JSONArray jsonArray1 = response.getJSONArray("weather");
                    JSONObject jsonObject3 = response.getJSONObject("main");
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                    Long l = Long.valueOf(day);
                    String icon = jsonObject1.getString("icon");
                    Picasso.with(getApplicationContext()).load("https://openweathermap.org/img/w/" + icon + ".png").into(imageViewIcon);
                    String t = jsonObject3.getString("temp");
                    int t1 = (int) (Double.valueOf(t) / 11.173);
                    DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
                    Date date = new Date();
                    textViewCity.setText("Hà Nội");
                    textViewDes.setText(jsonObject1.getString("main"));
                    textViewHum.setText("Độ ẩm: " + jsonObject3.getString("humidity") + "%");
                    textViewC.setText("Nhiệt độ: " + String.valueOf(t1) + "°C");
                    textViewLastUpdate.setText(String.format("Last update: %s", dateFormat.format(date)));
                } catch (JSONException e) {
                    Log.e("errrr", String.valueOf(e));
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}