package com.example.mymusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mymusic.R;
import com.example.mymusic.activities.SongsActivity;
import com.example.mymusic.models.Advertise;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<Advertise> advertiseArrayList;

    public BannerAdapter(Context context, ArrayList<Advertise> advertiseArrayList) {
        this.context = context;
        this.advertiseArrayList = advertiseArrayList;
    }

    @Override
    public int getCount() {
        return advertiseArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_banner, null);

        ImageView imageViewBgBanner = view.findViewById(R.id.imageViewBackgroundBanner);
        ImageView imageViewSongBanner = view.findViewById(R.id.imageViewSongBanner);
        TextView textViewTitleSongBanner = view.findViewById(R.id.textViewTitleBannerSong);
        TextView textViewContentSong = view.findViewById(R.id.textViewContent);

        Picasso.with(context).load(advertiseArrayList.get(position).getImage()).into(imageViewBgBanner);
        Picasso.with(context).load(advertiseArrayList.get(position).getImageSong()).into(imageViewSongBanner);
        textViewTitleSongBanner.setText(advertiseArrayList.get(position).getNameSong());
        textViewContentSong.setText(advertiseArrayList.get(position).getContent());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongsActivity.class);
                intent.putExtra("banner", advertiseArrayList.get(position));
                context.startActivity(intent);
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
