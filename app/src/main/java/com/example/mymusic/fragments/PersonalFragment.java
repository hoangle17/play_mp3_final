package com.example.mymusic.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymusic.R;
import com.example.mymusic.activities.CoronaActivity;
import com.example.mymusic.activities.LoginActivity;
import com.example.mymusic.activities.MainActivity;
import com.example.mymusic.activities.MusicDeviceActivity;
import com.example.mymusic.activities.SongsActivity;
import com.example.mymusic.activities.WeatherActivity;
import com.facebook.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

public class PersonalFragment extends Fragment {
    View view;
    ImageButton imageButtonFavorite, imageButtonPhone, imageButtonWeather, imageButtonCovid;
    public static ImageView imageViewUser;
    public static TextView textViewUsername, textViewNameUser, textViewLogin;
    public static ImageButton imageButtonLogin;
    public static boolean isLogged = false;

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
        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginActivity.getUser() != null && isLogged) {
                    Intent intent = new Intent(getActivity(), SongsActivity.class);
                    intent.putExtra("favorite_song", "favorite");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
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
        imageButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLogged) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    LoginActivity.mGoogleSignInClient.signOut();
                    PersonalFragment.textViewNameUser.setText("");
                    PersonalFragment.textViewUsername.setText("");
                    PersonalFragment.imageButtonLogin.setImageResource(R.drawable.ic_baseline_login_24);
                    PersonalFragment.textViewLogin.setText("Login");
                    PersonalFragment.imageViewUser.setImageResource(R.drawable.ic_baseline_person_24);
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    isLogged = false;
                }
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
        textViewLogin = view.findViewById(R.id.txtLogin);
        textViewNameUser = view.findViewById(R.id.txtNameUserPs);
        textViewUsername = view.findViewById(R.id.txtUsernamePs);
    }
}