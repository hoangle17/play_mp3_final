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

public class AllAlbumAdapter extends RecyclerView.Adapter<AllAlbumAdapter.ViewHolder> {
    Context context;
    ArrayList<Album> albumArrayList;

    public AllAlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_all_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumArrayList.get(position);
        Picasso.with(context).load(album.getImageAlbum()).into(holder.imageViewAllAlbum);
        holder.textViewSingerAllAlbum.setText(album.getSingerAlbum());
        holder.textViewNameAllAlbum.setText(album.getNameAlbum());
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAllAlbum;
        TextView textViewSingerAllAlbum, textViewNameAllAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewAllAlbum = itemView.findViewById(R.id.imgAllAlbum);
            textViewNameAllAlbum = itemView.findViewById(R.id.txtNameAlbumLine);
            textViewSingerAllAlbum = itemView.findViewById(R.id.txtSingerAlbumLine);
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
