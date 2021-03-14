package com.example.mymusic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.mymusic.R;
import com.example.mymusic.adapters.AllTopicsAdapter;
import com.example.mymusic.models.Topic;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTopicActivity extends AppCompatActivity {
    Toolbar toolbarAllTopic;
    RecyclerView recyclerViewAllTopic;
    AllTopicsAdapter allTopicsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_topic);
        setViews();
        getData();
    }

    private void getData() {
        DataService dataservice = APIService.getService();
        Call<List<Topic>> call = dataservice.getAllTopics();
        call.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                ArrayList<Topic> topicArrayList = (ArrayList<Topic>) response.body();
                allTopicsAdapter = new AllTopicsAdapter(ListTopicActivity.this, topicArrayList);
                recyclerViewAllTopic.setLayoutManager(new GridLayoutManager(ListTopicActivity.this, 1));
                recyclerViewAllTopic.setAdapter(allTopicsAdapter);
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {

            }
        });
    }

    private void setViews() {
        recyclerViewAllTopic = findViewById(R.id.rcvAllTopic);
        toolbarAllTopic = findViewById(R.id.tlbAllTopic);
        setSupportActionBar(toolbarAllTopic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All topics");
//        toolbarAllTopic.setTitleTextColor(getResources().getColor(R.color.purple));
        toolbarAllTopic.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}