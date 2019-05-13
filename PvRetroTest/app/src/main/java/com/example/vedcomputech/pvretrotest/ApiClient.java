package com.example.vedcomputech.pvretrotest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ved Computech on 02-04-2018.
 */

public class ApiClient {
    public static final String Base_URL="http://192.168.29.35/restapi/public/api/";
    //public static final String Base_URL = "http://192.168.29.35/restapi/public/api/";
    private static Retrofit retrofit=null;

    public static Retrofit getClient(){

        if(retrofit==null)
            retrofit=new Retrofit.Builder().baseUrl(Base_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}
