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
import com.example.mymusic.models.Album;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    Context context;
    ArrayList<Album> albumArrayList;

    public AlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.line_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumArrayList.get(position);
        holder.textViewSingerAlbum.setText(album.getSingerAlbum());
        holder.textViewNameAlbum.setText(album.getNameAlbum());
        Picasso.with(context).load(album.getImageAlbum()).into(holder.imageViewAlbum);
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAlbum;
        TextView textViewNameAlbum, textViewSingerAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAlbum = itemView.findViewById(R.id.imgViewAlbum);
            textViewNameAlbum = itemView.findViewById(R.id.txtNameAlbum);
            textViewSingerAlbum = itemView.findViewById(R.id.txtNameSingerAlbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SongsActivity.class);
                    intent.putExtra("album", albumArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
