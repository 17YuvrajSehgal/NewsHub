package com.cosc3p97.newshub.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cosc3p97.newshub.Constants;
import com.cosc3p97.newshub.MainActivity;
import com.cosc3p97.newshub.R;
import com.cosc3p97.newshub.api.ApiUtilities;
import com.cosc3p97.newshub.models.MainNews;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterFragment extends Fragment {

    private static final String TAG = "FilterFragment";
    private String query; // To store the query passed via Bundle
    String API_KEY = Constants.API_KEY;

    public static FilterFragment newInstance(String query) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Extract the query parameter from the arguments
        if (getArguments() != null) {
            query = getArguments().getString("query");
            Log.d(TAG, "Query parameter extracted: " + query);
        } else {
            Log.d(TAG, "No query parameter found in the Bundle");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        // Find the buttons
        Button filterRelevancyButton = view.findViewById(R.id.filterRelevancyButton);
        Button filterPopularityButton = view.findViewById(R.id.filterPopularityButton);
        Button filterPublishedAtButton = view.findViewById(R.id.filterPublishedAtButton);

        // Set click listeners for each button
        filterRelevancyButton.setOnClickListener(v -> {
            Log.d(TAG, "Relevancy filter selected. Query: " + query);
            makeApiCall("relevancy");
        });

        filterPopularityButton.setOnClickListener(v -> {
            Log.d(TAG, "Popularity filter selected. Query: " + query);
            makeApiCall("popularity");
        });

        filterPublishedAtButton.setOnClickListener(v -> {
            Log.d(TAG, "Published At filter selected. Query: " + query);
            makeApiCall("publishedAt");
        });

        return view;
    }

    private void makeApiCall(String sortBy) {
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
                    Log.d(TAG, "API response received. Number of articles: " + mainNews.getArticles().size());

                    // Send the updated data to the SearchFragment
                    if (getActivity() instanceof MainActivity) {
                        SearchFragment searchFragment = new SearchFragment();
                        Bundle args = new Bundle();
                        args.putString("query", query);  // Pass the query
                        searchFragment.setArguments(args);

                        // Mark the SearchFragment as filtered
                        searchFragment.setFiltered(true);

                        // Replace the current fragment with the updated SearchFragment
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content, searchFragment);
                        ft.commit();

                        // Optionally, close the FilterFragment itself
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
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
}
