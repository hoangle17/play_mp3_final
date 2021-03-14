package com.example.mymusic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.makeramen.roundedimageview.RoundedImageView;

public class ShowInformationSongFragment extends Fragment {
    View view;
    RoundedImageView roundedImageView;
    TextView textViewNameSongPlay, textViewSingerPlay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_infor_song, container, false);
        roundedImageView = view.findViewById(R.id.imgSongPlay);
        textViewNameSongPlay = view.findViewById(R.id.txtNameSongPlay);
        textViewSingerPlay = view.findViewById(R.id.txtSingerPlay);
        return view;
    }
}
