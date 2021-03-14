package com.example.mymusic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.activities.PlaySongActivity;
import com.example.mymusic.activities.SongsActivity;
import com.example.mymusic.adapters.PlayListSongsAdapter;

public class PlayListSongsFragment extends Fragment {
    View view;
    RecyclerView recyclerViewPlayListSong;
    PlayListSongsAdapter playListSongsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_list_songs, container, false);
        recyclerViewPlayListSong = view.findViewById(R.id.rcvPlayListSongs);
        if (PlaySongActivity.songArrayList.size() > 0) {
            playListSongsAdapter = new PlayListSongsAdapter(getActivity(), PlaySongActivity.songArrayList);
            recyclerViewPlayListSong.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewPlayListSong.setAdapter(playListSongsAdapter);
        }
        return view;
    }
}
