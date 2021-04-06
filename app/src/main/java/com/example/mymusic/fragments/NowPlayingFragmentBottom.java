package com.example.mymusic.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.mymusic.notification.CreateNotification;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mymusic.activities.PlaySongActivity.imageButtonPlay;
import static com.example.mymusic.activities.PlaySongActivity.mediaPlayer;

public class NowPlayingFragmentBottom extends Fragment {
    public static TextView textViewNameSongMini, textViewSingerMini;
    public static ImageView imageViewSongMini, imageViewLikeMini;
    public static ImageButton imageButtonPlayMini;
    View view;
    public static NowPlayingFragmentBottom activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = this;
        view = inflater.inflate(R.layout.fragment_now_playing_bottom, container, false);
        setViews();
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
//                    CreateNotification.createNotification(getActivity(), PlaySongActivity.songArrayList.get(),
//                            R.drawable.ic_baseline_pause_24);
                }
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

    public static NowPlayingFragmentBottom getContextMinimize() {
        return activity;
    }

}