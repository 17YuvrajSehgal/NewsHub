package com.cosc3p97.newshub;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    String API_KEY = Constants.API_KEY;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Model> modelArrayList;

    private static final String TAG = "HomeFragment"; // Tag for Log statements

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: HomeFragment is created"); // Debug log
        View v = inflater.inflate(R.layout.fragment_home, null);

        recyclerView = v.findViewById(R.id.home_recycleView);
        modelArrayList = new ArrayList<>();
        adapter = new Adapter(getContext(), modelArrayList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.d(TAG, "RecyclerView and Adapter initialized");
        getNews();

        return v;
    }

    void getNews() {
        String country = "us"; // Example: Retrieve news for the US

        Log.d(TAG, "Calling API for country: " + country);

        ApiUtilities.getApiInterface().getNews(country, API_KEY).enqueue(new Callback<MainNews>() {
            @Override
            public void onResponse(Call<MainNews> call, Response<MainNews> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "API response received. Number of articles: " + response.body().getArticles().size());
                    modelArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "API response unsuccessful. Status code: " + response.code() + ", message: " + response.message());
                    Toast.makeText(getContext(), "Failed to fetch news.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MainNews> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: HomeFragment paused");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: HomeFragment resumed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: HomeFragment destroyed");
    }
}
