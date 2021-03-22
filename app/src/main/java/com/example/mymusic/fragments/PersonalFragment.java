package com.example.mymusic.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymusic.R;
import com.example.mymusic.activities.CoronaActivity;
import com.example.mymusic.activities.MusicDeviceActivity;
import com.example.mymusic.activities.WeatherActivity;

public class PersonalFragment extends Fragment {
    View view;
    ImageButton imageButtonFavorite, imageButtonPhone, imageButtonWeather, imageButtonCovid, imageButtonLogin;
    ImageView imageViewUser;
    TextView textViewUsername, textViewNameUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        setViews();
        clickEvent();
        return view;
    }

    private void clickEvent() {
        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MusicDeviceActivity.class);
                startActivity(intent);
            }
        });
        imageButtonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                startActivity(intent);
            }
        });
        imageButtonCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CoronaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setViews() {
        imageViewUser = view.findViewById(R.id.imgViewAccount);

        imageButtonFavorite = view.findViewById(R.id.btnFavorite);
        imageButtonPhone = view.findViewById(R.id.btnPhone);
        imageButtonWeather = view.findViewById(R.id.btnWheather);
        imageButtonCovid = view.findViewById(R.id.btnCovid);
        imageButtonLogin = view.findViewById(R.id.btnLogin);

        textViewNameUser = view.findViewById(R.id.txtNameUser);
        textViewUsername = view.findViewById(R.id.txtUsername);
    }

}