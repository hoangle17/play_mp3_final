package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.mymusic.R;
import com.example.mymusic.adapters.AllPlaylistAdapter;
import com.example.mymusic.models.Playlist;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPlaylistActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerViewListPlaylist;
    AllPlaylistAdapter allPlaylistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_playlist);
        setViews();
        init();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Playlist>> call = dataService.getAllPlaylists();
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                ArrayList<Playlist> playlistArrayList = (ArrayList<Playlist>) response.body();
                allPlaylistAdapter = new AllPlaylistAdapter(ListPlaylistActivity.this, playlistArrayList);
                recyclerViewListPlaylist.setLayoutManager(new GridLayoutManager(ListPlaylistActivity.this, 2));
                recyclerViewListPlaylist.setAdapter(allPlaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {

            }
        });
    }

    private void setViews() {
        toolbar = findViewById(R.id.tlbListPlaylist);
        recyclerViewListPlaylist = findViewById(R.id.rcvListPlaylist);
    }

    private void init() {
        String title = "All playlists";
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
//        toolbar.setTitleTextColor(getResources().getColor(R.color.purple));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}