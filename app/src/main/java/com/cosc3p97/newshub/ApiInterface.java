package com.cosc3p97.newshub;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "https://newsapi.org"; // check

    @GET("top-headlines")
    Call<MainNews> getNews (
            @Query("country") String country,
            @Query("pagesize") int pagesize,
            @Query("apikey") String apikey
    );

    @GET("top-headlines")
    Call<MainNews> getCategory (
            @Query("country") String country,
            @Query("category") String category,
            @Query("pageSize") int pagesize,
            @Query("apikey") String apikey
    );


}
