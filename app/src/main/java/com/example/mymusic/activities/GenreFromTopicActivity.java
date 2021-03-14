package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mymusic.R;
import com.example.mymusic.adapters.GenreFromTopicAdapter;
import com.example.mymusic.models.Genre;
import com.example.mymusic.models.Topic;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreFromTopicActivity extends AppCompatActivity {
    Topic topic;
    RecyclerView recyclerViewGenreFromTopic;
    Toolbar toolbarGenreFromTopic;
    GenreFromTopicAdapter genreFromTopicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_from_topic);
        getIntentFromTopic();
        setViews();
        getData();
    }

    private void getIntentFromTopic() {
        Intent intent = getIntent();
        if (intent.hasExtra("topic")) {
            topic = (Topic) intent.getSerializableExtra("topic");
        }
    }

    private void setViews() {
        recyclerViewGenreFromTopic = findViewById(R.id.rcvGenreFromTopic);
        toolbarGenreFromTopic = findViewById(R.id.tlbGenreFromTopic);
        setSupportActionBar(toolbarGenreFromTopic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(topic.getNameTopic());
        toolbarGenreFromTopic.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Genre>> call = dataService.getGenreFromTopic(topic.getIdTopic());
        call.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                ArrayList<Genre> genreArrayList = (ArrayList<Genre>) response.body();
                genreFromTopicAdapter = new GenreFromTopicAdapter(GenreFromTopicActivity.this, genreArrayList);
                recyclerViewGenreFromTopic.setLayoutManager(new GridLayoutManager(GenreFromTopicActivity.this, 2));
                recyclerViewGenreFromTopic.setAdapter(genreFromTopicAdapter);
            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {

            }
        });
    }
}