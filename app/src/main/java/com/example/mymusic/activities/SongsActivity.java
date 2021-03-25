package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

import com.example.mymusic.R;
import com.example.mymusic.adapters.HotSongAdapter;
import com.example.mymusic.adapters.ListSongsAdapter;
import com.example.mymusic.adapters.SearchSongAdapter;
import com.example.mymusic.models.Advertise;
import com.example.mymusic.models.Album;
import com.example.mymusic.models.Genre;
import com.example.mymusic.models.Playlist;
import com.example.mymusic.models.Song;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongsActivity extends AppCompatActivity {
    Advertise advertise;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewListSong;
    FloatingActionButton floatingActionButton;
    ImageView imageViewListSong;
    ArrayList<Song> songArrayList;
    ListSongsAdapter listSongsAdapter;
    Playlist playlist;
    Genre genre;
    Album album;
    public static boolean isClickFB = false;
    boolean isRetrieveFavoriteSongs = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        dataMoveIntentSongs();
        setViews();
        init();
        if (advertise != null && !advertise.getNameSong().equals("")) {
            setValueInView(advertise.getNameSong(), advertise.getImageSong());
            getDataAdvertise(advertise.getIdAdvertise());
        }
        if (playlist != null && !playlist.getName().equals("")) {
            setValueInView(playlist.getName(), playlist.getIcon());
            getDataPlaylist(playlist.getIdPlaylist());
        }
        if (genre != null && !genre.getNameGenre().equals("")) {
            setValueInView(genre.getNameGenre(), genre.getImageGenre());
            getDataGenre(genre.getIdGenre());
        }
        if (album != null && !album.getNameAlbum().equals("")) {
            setValueInView(album.getNameAlbum(), album.getImageAlbum());
            getDataAlbum(album.getIdAlbum());
        }
        if (isRetrieveFavoriteSongs) {
            setValueInView("Your favorite song", "https://tenebrous-segments.000webhostapp.com/Image/favorite.jpg");
            getFavoriteSong(MainActivity.getUser().getIdUser());
        }
    }

    private void getFavoriteSong(String idUser) {
        DataService dataService = APIService.getService();
        Call<List<Song>> call = dataService.getFavoriteSongs(idUser);
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = (ArrayList<Song>) response.body();
                listSongsAdapter = new ListSongsAdapter(SongsActivity.this, songArrayList);
                recyclerViewListSong.setLayoutManager(new LinearLayoutManager(SongsActivity.this));
                recyclerViewListSong.setAdapter(listSongsAdapter);
                eventClickListSongs();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }

    private void getDataAlbum(String idAlbum) {
        DataService dataService = APIService.getService();
        Call<List<Song>> call = dataService.getListAlbumSongs(idAlbum);
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = (ArrayList<Song>) response.body();
                listSongsAdapter = new ListSongsAdapter(SongsActivity.this, songArrayList);
                recyclerViewListSong.setLayoutManager(new LinearLayoutManager(SongsActivity.this));
                recyclerViewListSong.setAdapter(listSongsAdapter);
                eventClickListSongs();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }

    private void getDataGenre(String idGenre) {
        DataService dataService = APIService.getService();
        Call<List<Song>> call = dataService.getListGenreSongs(idGenre);
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = (ArrayList<Song>) response.body();
                listSongsAdapter = new ListSongsAdapter(SongsActivity.this, songArrayList);
                recyclerViewListSong.setLayoutManager(new LinearLayoutManager(SongsActivity.this));
                recyclerViewListSong.setAdapter(listSongsAdapter);
                eventClickListSongs();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }

    private void getDataPlaylist(String idPlaylist) {
        DataService dataService = APIService.getService();
        Call<List<Song>> call = dataService.getListPlaylistSongs(idPlaylist);
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = (ArrayList<Song>) response.body();
                listSongsAdapter = new ListSongsAdapter(SongsActivity.this, songArrayList);
                recyclerViewListSong.setLayoutManager(new LinearLayoutManager(SongsActivity.this));
                recyclerViewListSong.setAdapter(listSongsAdapter);
                eventClickListSongs();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }

    private void getDataAdvertise(String idAdvertise) {
        DataService dataService = APIService.getService();
        Call<List<Song>> call = dataService.getListAdvertiseSongs(idAdvertise);
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = (ArrayList<Song>) response.body();
                listSongsAdapter = new ListSongsAdapter(SongsActivity.this, songArrayList);
                recyclerViewListSong.setLayoutManager(new LinearLayoutManager(SongsActivity.this));
                recyclerViewListSong.setAdapter(listSongsAdapter);
                eventClickListSongs();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }

    private void setValueInView(String name, String image) {
        collapsingToolbarLayout.setTitle(name);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        try {
            URL url = new URL(image);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                collapsingToolbarLayout.setBackground(bitmapDrawable);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(image).into(imageViewListSong);
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void setViews() {
        collapsingToolbarLayout = findViewById(R.id.collapsingToolBar);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        toolbar = findViewById(R.id.toolbarTitleList);
        recyclerViewListSong = findViewById(R.id.recListSongs);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        imageViewListSong = findViewById(R.id.imgListSongs);
    }

    private void dataMoveIntentSongs() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("banner")) {
                advertise = (Advertise) intent.getSerializableExtra("banner");
            }
            if (intent.hasExtra("itemplaylist")) {
                playlist = (Playlist) intent.getSerializableExtra("itemplaylist");
            }
            if (intent.hasExtra("genre")) {
                genre = (Genre) intent.getSerializableExtra("genre");
            }
            if (intent.hasExtra("album")) {
                album = (Album) intent.getSerializableExtra("album");
            }
            if (intent.hasExtra("favorite_song")) {
                isRetrieveFavoriteSongs = true;
            }
        }
    }

    private void eventClickListSongs() {
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchSongAdapter.isClickedItemSearch = false;
                HotSongAdapter.isClickedHotSong = false;
                isClickFB = true;

                Intent intent = new Intent(SongsActivity.this, PlaySongActivity.class);
                intent.putExtra("songs", songArrayList);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}