package com.example.mymusic.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymusic.R;
import com.example.mymusic.activities.PlaySongActivity;
import com.example.mymusic.models.Song;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mymusic.activities.PlaySongActivity.imageButtonPlay;
import static com.facebook.FacebookSdk.getApplicationContext;

public class NowPlayingFragmentBottom extends Fragment {
    TextView textViewNameSongMini, textViewSingerMini;
    ImageView imageViewSongMini, imageViewLikeMini;
    public static ImageButton imageButtonPlayMini;
    View view;
    Song song;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_now_playing_bottom, container, false);
        setViews();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiverMini, new IntentFilter("MOVE_MINI"));
        imageButtonPlayMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlaySongActivity.getMediaPlayer().isPlaying()) {
                    PlaySongActivity.getMediaPlayer().pause();
                    imageButtonPlayMini.setImageResource(R.drawable.ic_play_arrow);
                    imageButtonPlay.setImageResource(R.drawable.ic_play_arrow);
                } else {
                    PlaySongActivity.getMediaPlayer().start();
                    imageButtonPlayMini.setImageResource(R.drawable.ic_baseline_pause_24);
                    imageButtonPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                }
            }
        });

        imageViewLikeMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewLikeMini.setImageResource(R.drawable.heartfull);
                DataService dataService = APIService.getService();
                Call<String> call = dataService.updateLiked("1", song.getIdSong());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String result = response.body();
                        if (result.equals("success")) {
                            Toast.makeText(getActivity(), "Liked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
        return view;
    }

    private void setViews() {
        imageButtonPlayMini = view.findViewById(R.id.btnPlayMini);
        imageViewLikeMini = view.findViewById(R.id.imgLikeMini);
        imageViewSongMini = view.findViewById(R.id.imgSongMini);
        textViewNameSongMini = view.findViewById(R.id.txtNameSongMini);
        textViewSingerMini = view.findViewById(R.id.txtSingerMini);
    }

    public void setViewPlayMini(String image, String name, String singer) {
        textViewSingerMini.setText(singer);
        textViewNameSongMini.setText(name);
        Picasso.with(getActivity()).load(image).into(imageViewSongMini);
    }

    private BroadcastReceiver mReceiverMini = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            song = (Song) intent.getParcelableExtra("moveToMini");
            setViewPlayMini(song.getImageSong(), song.getNameSong(), song.getSinger());
        }
    };

}