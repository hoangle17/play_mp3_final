package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.mymusic.R;
import com.example.mymusic.adapters.AllAlbumAdapter;
import com.example.mymusic.models.Album;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAlbumActivity extends AppCompatActivity {
    RecyclerView recyclerViewAllAlbum;
    Toolbar toolbarAllAlbum;
    AllAlbumAdapter allAlbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_album);
        setViews();
        getData();
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Album>> call = dataService.getAllAlbums();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumArrayList = (ArrayList<Album>) response.body();
                allAlbumAdapter = new AllAlbumAdapter(ListAlbumActivity.this, albumArrayList);
                recyclerViewAllAlbum.setLayoutManager(new GridLayoutManager(ListAlbumActivity.this, 2));
                recyclerViewAllAlbum.setAdapter(allAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void setViews() {
        recyclerViewAllAlbum = findViewById(R.id.rcvAllAlbum);
        toolbarAllAlbum = findViewById(R.id.tlbAllAlbum);
        setSupportActionBar(toolbarAllAlbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All albums");
        toolbarAllAlbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}