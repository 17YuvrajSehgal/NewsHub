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
    String API_KEY = "";
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
        String query = "tesla";  // You can replace this with any search query
        String fromDate = "2024-11-17";  // Replace with the date you need
        String sortBy = "publishedAt";  // You can adjust sorting criteria

        ApiUtilities.getApiInterface().getNews(query, fromDate, sortBy, API_KEY).enqueue(new Callback<MainNews>() {
            @Override
            public void onResponse(Call<MainNews> call, Response<MainNews> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: News fetched successfully");
                    modelArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG, "onResponse: Response not successful, status code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MainNews> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
