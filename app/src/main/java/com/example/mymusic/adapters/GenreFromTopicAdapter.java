package com.example.mymusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.activities.SongsActivity;
import com.example.mymusic.models.Genre;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GenreFromTopicAdapter extends RecyclerView.Adapter<GenreFromTopicAdapter.ViewHolder> {
    Context context;
    ArrayList<Genre> genreArrayList;

    public GenreFromTopicAdapter(Context context, ArrayList<Genre> genreArrayList) {
        this.context = context;
        this.genreArrayList = genreArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_genre_from_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Genre genre = genreArrayList.get(position);
        Picasso.with(context).load(genre.getImageGenre()).into(holder.imageViewGenre);
        holder.textViewNameGenre.setText(genre.getNameGenre());
    }

    @Override
    public int getItemCount() {
        return genreArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewGenre;
        TextView textViewNameGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewGenre = itemView.findViewById(R.id.imgGenreFromTopic);
            textViewNameGenre = itemView.findViewById(R.id.txtGenreFromTopic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SongsActivity.class);
                    intent.putExtra("genre", genreArrayList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
