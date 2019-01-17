package com.app.androidinsta.rest;

import com.app.androidinsta.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public  static RetrofitService getRetrofitService(){
        return new Retrofit.Builder().
                baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitService.class);
    }
}
