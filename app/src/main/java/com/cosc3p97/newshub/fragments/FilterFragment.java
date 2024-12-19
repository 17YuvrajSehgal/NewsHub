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

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends Fragment {

    private static final String TAG = "FilterFragment";
    private String query; // To store the query passed via Bundle
    String API_KEY = Constants.API_KEY;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Model> modelArrayList;

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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_filter, container, false);

        recyclerView = v.findViewById(R.id.filter_recycleView);
        modelArrayList = new ArrayList<>();
        adapter = new Adapter(getContext(), modelArrayList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get search query passed from MainActivity or FilterFragment
        if (getArguments() != null) {
            query = getArguments().getString("query");
        }

        // Find the buttons
        Button filterRelevancyButton = v.findViewById(R.id.filterRelevancyButton);
        Button filterPopularityButton = v.findViewById(R.id.filterPopularityButton);
        Button filterPublishedAtButton = v.findViewById(R.id.filterPublishedAtButton);

        // Set click listeners for each button
        filterRelevancyButton.setOnClickListener(view -> {
            Log.d(TAG, "Relevancy filter selected. Query: " + query);
            getNews("relevancy");
        });

        filterPopularityButton.setOnClickListener(view -> {
            Log.d(TAG, "Popularity filter selected. Query: " + query);
            getNews("popularity");
        });

        filterPublishedAtButton.setOnClickListener(view -> {
            Log.d(TAG, "Published At filter selected. Query: " + query);
            getNews("publishedAt");
        });

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
                if (response.isSuccessful() && response.body() != null) {
                    MainNews mainNews = response.body();
                    Log.d(TAG, "API Response successful. Articles count: " + mainNews.getArticles().size());

                    // Clear the existing list and add new data
                    modelArrayList.clear();
                    modelArrayList.addAll(mainNews.getArticles());

                    // Notify adapter about the data change
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "API Response unsuccessful. Code: " + response.code() + ", Message: " + response.message());
                    Toast.makeText(getContext(), "Failed to fetch news. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MainNews> call, Throwable t) {
                Log.e(TAG, "API Call failed: " + t.getMessage(), t);
                Toast.makeText(getContext(), "An error occurred while fetching data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
