package com.cosc3p97.newshub.api;

import com.cosc3p97.newshub.models.MainNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "https://newsapi.org/v2/";

    @GET("top-headlines")
    Call<MainNews> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );


    @GET("top-headlines")
    Call<MainNews> getCategory(
            @Query("country") String country,
            @Query("category") String category,
            @Query("pageSize") int pageSize,
            @Query("apikey") String apikey
    );

    @GET("everything")
    Call<MainNews> getNewsFromQuery(
            @Query("language") String language,
            @Query("q") String query,
            @Query("apikey") String apikey
    );

    @GET("everything")
    Call<MainNews> getNewsFromQueryWithAllFilters(
            @Query("q") String query,                   // Search query (keywords)
            @Query("from") String fromDate,             // From date (ISO 8601)
            @Query("to") String toDate,                 // To date (ISO 8601)
            @Query("language") String language,         // Language of the articles
            @Query("sortBy") String sortBy,             // Sorting criteria (relevancy, popularity, publishedAt)
            @Query("apikey") String apiKey              // API key
    );

}
