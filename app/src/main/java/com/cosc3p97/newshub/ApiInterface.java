package com.cosc3p97.newshub;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "https://newsapi.org/v2/";

    @GET("everything")
    Call<MainNews> getNews(
            @Query("q") String query,
            @Query("from") String fromDate,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );


    @GET("top-headlines")
    Call<MainNews> getCategory(
            @Query("country") String country,
            @Query("category") String category,
            @Query("pageSize") int pageSize,
            @Query("apikey") String apikey
    );



}
