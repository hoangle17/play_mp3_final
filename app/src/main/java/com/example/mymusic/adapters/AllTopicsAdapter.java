package com.example.mymusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.activities.GenreFromTopicActivity;
import com.example.mymusic.models.Genre;
import com.example.mymusic.models.Topic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllTopicsAdapter extends RecyclerView.Adapter<AllTopicsAdapter.ViewHolder> {
    Context context;
    ArrayList<Topic> topicArrayList;

    public AllTopicsAdapter(Context context, ArrayList<Topic> topicArrayList) {
        this.context = context;
        this.topicArrayList = topicArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_all_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = topicArrayList.get(position);
        Picasso.with(context).load(topic.getImageTopic()).into(holder.imageViewAllTopic);
    }

    @Override
    public int getItemCount() {
        return topicArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAllTopic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAllTopic = itemView.findViewById(R.id.imgLineAllTopic);
            imageViewAllTopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GenreFromTopicActivity.class);
                    intent.putExtra("topic", topicArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
