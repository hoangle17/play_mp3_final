package com.example.mymusic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.mymusic.R;
import com.example.mymusic.activities.GenreFromTopicActivity;
import com.example.mymusic.activities.ListTopicActivity;
import com.example.mymusic.activities.SongsActivity;
import com.example.mymusic.models.Genre;
import com.example.mymusic.models.Topic;
import com.example.mymusic.models.TopicAndGenre;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicAndGenreFragment extends Fragment {
    View view;
    TextView textViewMoreTopicGenre;
    HorizontalScrollView horizontalScrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_genre_and_topic, container, false);
        horizontalScrollView = view.findViewById(R.id.horizontalScrollView);
        textViewMoreTopicGenre = view.findViewById(R.id.txtMoreTopicGenre);
        textViewMoreTopicGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListTopicActivity.class);
                startActivity(intent);
            }
        });
        getData();
        return view;
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<TopicAndGenre> call = dataService.getDataTopicAndGenre();
        call.enqueue(new Callback<TopicAndGenre>() {
            @Override
            public void onResponse(Call<TopicAndGenre> call, Response<TopicAndGenre> response) {
                TopicAndGenre topicAndGenre = response.body();

                final ArrayList<Topic> topicArrayList = new ArrayList<>();
                topicArrayList.addAll(topicAndGenre.getTopic());
                final ArrayList<Genre> genreArrayList = new ArrayList<>();
                genreArrayList.addAll(topicAndGenre.getGenre());

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(580, 250);
                layout.setMargins(10, 20, 10, 30);

                int sizeTopic = topicArrayList.size();
                for (int count = 0; count < sizeTopic; count++) {
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (topicArrayList.get(count).getImageTopic() != null) {
                        Picasso.with(getActivity()).load(topicArrayList.get(count).getImageTopic()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    int finalCount = count;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), GenreFromTopicActivity.class);
                            intent.putExtra("topic", topicArrayList.get(finalCount));
                            startActivity(intent);
                        }
                    });
                }
                int sizeGenre = topicArrayList.size();
                for (int count = 0; count < sizeGenre; count++) {
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (genreArrayList.get(count).getImageGenre() != null) {
                        Picasso.with(getActivity()).load(genreArrayList.get(count).getImageGenre()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    int finalCount = count;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), SongsActivity.class);
                            intent.putExtra("genre", genreArrayList.get(finalCount));
                            startActivity(intent);
                        }
                    });
                }
                horizontalScrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(Call<TopicAndGenre> call, Throwable t) {

            }
        });
    }

}
