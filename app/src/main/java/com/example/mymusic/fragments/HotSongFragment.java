package com.example.mymusic.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.adapters.HotSongAdapter;
import com.example.mymusic.models.Song;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotSongFragment extends Fragment {
    View view;
    RecyclerView recyclerViewHotSong;
    HotSongAdapter hotSongAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frament_hot_song, container, false);
        recyclerViewHotSong = view.findViewById(R.id.recyclerViewHotSong);
        getData();
        return view;
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Song>> call = dataService.getDataHotSong();
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                ArrayList<Song> songArrayList = (ArrayList<Song>) response.body();
                hotSongAdapter = new HotSongAdapter(getActivity(), songArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerViewHotSong.setLayoutManager(linearLayoutManager);
                recyclerViewHotSong.setAdapter(hotSongAdapter);
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }
}
