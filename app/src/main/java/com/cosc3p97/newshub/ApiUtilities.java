package com.cosc3p97.newshub;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {
    public static Retrofit r = null;

    public static ApiInterface getApiInterface() {
        if(r == null) r = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return r.create(ApiInterface.class);
    }
}
