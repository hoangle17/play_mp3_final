package com.example.mymusic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.adapters.SearchSongAdapter;
import com.example.mymusic.models.Song;
import com.example.mymusic.services.APIService;
import com.example.mymusic.services.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    View view;
    Toolbar toolbar;
    RecyclerView recyclerViewListSearch;
    TextView textViewResultNull, textViewBeLow;
    SearchSongAdapter searchSongAdapter;
    View viewIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        toolbar = view.findViewById(R.id.tlbSearchSong);
        recyclerViewListSearch = view.findViewById(R.id.rcvSearchView);
        textViewResultNull = view.findViewById(R.id.txtNullResult);
        textViewBeLow = view.findViewById(R.id.txtBelow);
        viewIcon = view.findViewById(R.id.viewIconSearch);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_view, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSongs(query);
                return true;
//               return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                searchSongs(newText);
//                return true;
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchSongs(String query) {
        DataService dataService = APIService.getService();
        Call<List<Song>> call = dataService.getSearch(query);
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {

                ArrayList<Song> songArrayList = (ArrayList<Song>) response.body();
                if (songArrayList.size() > 0) {
                    searchSongAdapter = new SearchSongAdapter(songArrayList, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewListSearch.setLayoutManager(linearLayoutManager);
                    recyclerViewListSearch.setAdapter(searchSongAdapter);

                    textViewResultNull.setVisibility(View.GONE);
                    textViewBeLow.setVisibility(View.GONE);
                    viewIcon.setVisibility(View.GONE);
                    recyclerViewListSearch.setVisibility(View.VISIBLE);
                } else {
                    viewIcon.setVisibility(View.VISIBLE);
                    textViewResultNull.setVisibility(View.VISIBLE);
                    textViewBeLow.setVisibility(View.VISIBLE);
                    textViewResultNull.setText("No results found");
                    textViewBeLow.setText("Check the spelling or try a different search");
                    recyclerViewListSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }

}
