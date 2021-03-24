package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CoronaActivity extends AppCompatActivity {
    Button buttonVN, buttonGlobal;
    TextView textViewTotal, textViewDead, textViewNewDead, textViewRecover, textViewNewRecover;
    ImageView imageViewBack;
    String URL = "https://api.covid19api.com/summary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona);
        setViews();
        getData();
    }

    private void getData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Countries");
                    JSONObject jsonObjectVN = jsonArray.getJSONObject(186);

                    textViewTotal.setText(jsonObjectVN.getString("TotalConfirmed"));
                    textViewDead.setText(jsonObjectVN.getString("TotalDeaths"));
                    textViewRecover.setText(jsonObjectVN.getString("TotalRecovered"));
                    textViewNewRecover.setText(jsonObjectVN.getString("NewRecovered"));
                    textViewNewDead.setText(jsonObjectVN.getString("NewDeaths"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

        buttonGlobal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buttonVN.setBackgroundResource(R.drawable.statistic_button);
                buttonGlobal.setBackgroundResource(R.drawable.spinner_bg);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObjectGlo = response.getJSONObject("Global");

                            textViewTotal.setText(jsonObjectGlo.getString("TotalConfirmed"));
                            textViewDead.setText(jsonObjectGlo.getString("TotalDeaths"));
                            textViewRecover.setText(jsonObjectGlo.getString("TotalRecovered"));
                            textViewNewRecover.setText(jsonObjectGlo.getString("NewRecovered"));
                            textViewNewDead.setText(jsonObjectGlo.getString("NewDeaths"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });
        buttonVN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonVN.setBackgroundResource(R.drawable.spinner_bg);
                buttonGlobal.setBackgroundResource(R.drawable.statistic_button);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Countries");
                            JSONObject jsonObjectVN = jsonArray.getJSONObject(186);

                            textViewTotal.setText(jsonObjectVN.getString("TotalConfirmed"));
                            textViewDead.setText(jsonObjectVN.getString("TotalDeaths"));
                            textViewRecover.setText(jsonObjectVN.getString("TotalRecovered"));
                            textViewNewRecover.setText(jsonObjectVN.getString("NewRecovered"));
                            textViewNewDead.setText(jsonObjectVN.getString("NewDeaths"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });
    }

    private void setViews() {
        buttonVN = findViewById(R.id.btnVN);
        buttonGlobal = findViewById(R.id.btnGlobal);
        textViewTotal = findViewById(R.id.txtTotal);
        textViewDead = findViewById(R.id.txtDeath);
        textViewNewDead = findViewById(R.id.txtNewDeath);
        textViewRecover = findViewById(R.id.txtRecovered);
        textViewNewRecover = findViewById(R.id.txtNewRecovered);
        imageViewBack = findViewById(R.id.back);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}