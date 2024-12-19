package com.cosc3p97.newshub.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cosc3p97.newshub.Adapter;
import com.cosc3p97.newshub.Constants;
import com.cosc3p97.newshub.R;
import com.cosc3p97.newshub.api.ApiUtilities;
import com.cosc3p97.newshub.models.MainNews;
import com.cosc3p97.newshub.models.Model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterFragment extends Fragment {
    String API_KEY = Constants.API_KEY;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Model> modelArrayList;
    private String query;
    private static final String TAG = "FilterFragment";

    public static FilterFragment newInstance(String query) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: FilterFragment is created");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        // Find the buttons
        Button filterRelevancyButton = view.findViewById(R.id.filterRelevancyButton);
        Button filterPopularityButton = view.findViewById(R.id.filterPopularityButton);
        Button filterPublishedAtButton = view.findViewById(R.id.filterPublishedAtButton);

        View v = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = v.findViewById(R.id.search_recycleView);
        modelArrayList = new ArrayList<>();
        adapter = new Adapter(getContext(), modelArrayList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set click listeners for each button
        filterRelevancyButton.setOnClickListener(v -> {
            Log.d(TAG, "Relevancy filter selected. Query: " + query);
            getNews("relevancy");
        });

        filterPopularityButton.setOnClickListener(v -> {
            Log.d(TAG, "Popularity filter selected. Query: " + query);
            getNews("popularity");
        });

        filterPublishedAtButton.setOnClickListener(v -> {
            Log.d(TAG, "Published At filter selected. Query: " + query);
            getNews("publishedAt");
        });

        // Get search query passed from MainActivity or FilterFragment
        if (getArguments() != null) {
            query = getArguments().getString("query");
            getNews(query);
        }

        return v;
    }


    private void getNews(String sortBy) {
        if (query == null || query.trim().isEmpty()) {
            Toast.makeText(getContext(), "Query is empty or null.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Query is null or empty. Cannot make API call.");
            return;
        }

        ApiUtilities.getApiInterface().getNewsWithSortBy(query, sortBy, API_KEY).enqueue(new Callback<MainNews>() {
            @Override
            public void onResponse(Call<MainNews> call, Response<MainNews> response) {
                ArrayList<Model> validArticles = new ArrayList<>();
                for (Model model : response.body().getArticles()) {
                    if (model != null && model.getTitle() != null && !model.getTitle().isEmpty()) {
                        validArticles.add(model);
                    } else {
                        Log.d(TAG, "Null or empty article removed: " + model);
                    }
                }

                if (!validArticles.isEmpty()) {
                    modelArrayList.clear();
                    modelArrayList.addAll(validArticles);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "API response unsuccessful. Status code: " + response.code() + ", message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MainNews> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
