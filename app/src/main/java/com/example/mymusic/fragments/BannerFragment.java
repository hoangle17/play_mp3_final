package com.example.mymusic.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mymusic.R;
import com.example.mymusic.adapters.BannerAdapter;
import com.example.mymusic.models.Advertise;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerFragment extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        initView();
        getData();
        return view;
    }

    private void initView() {
        viewPager = view.findViewById(R.id.viewPaper);
        circleIndicator = view.findViewById(R.id.indicatorDefault);
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Advertise>> callback = dataService.getDataBanner();
        callback.enqueue(new Callback<List<Advertise>>() {
            @Override
            public void onResponse(Call<List<Advertise>> call, Response<List<Advertise>> response) {
                ArrayList<Advertise> advertises = (ArrayList<Advertise>) response.body();
                bannerAdapter = new BannerAdapter(getActivity(), advertises);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentItem = viewPager.getCurrentItem();
                        currentItem++;
                        if (currentItem >= viewPager.getAdapter().getCount()) {
                            currentItem = 0;
                        }
                        viewPager.setCurrentItem(currentItem, true);
                        handler.postDelayed(runnable, 4500);
                    }
                };
                handler.postDelayed(runnable, 4500);
            }

            @Override
            public void onFailure(Call<List<Advertise>> call, Throwable t) {

            }
        });
    }
}
