package com.example.apinotification.service;


import com.example.apinotification.config.BuildConfig;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // public static final String BASE_URI="http://192.168.0.119:53856/";
    // public static final String BASE_URI="http://192.168.29.33/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;

    public static Retrofit getClient() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                /*.retryOnConnectionFailure(true)*/
                .build();

        if (retrofit == null) {

            String BASE_URI = BuildConfig.BASE_URL;
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URI)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
        }
        return retrofit;
    }


/*    public static Retrofit getClient2() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                *//*.retryOnConnectionFailure(true)*//*
                .build();

        if (retrofit2 == null) {

            String BASE_URI = BuildConfig.BASE_URL2;
            retrofit2 = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URI)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
        }
        return retrofit2;
    }*/

}
