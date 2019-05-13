package com.example.vedcomputech.pvretrotest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ved Computech on 31-03-2018.
 */

public interface ApiInterface {


   /* //for all data
    @GET("preview")
    Call<Details> getDetails();*/

    //For a particular id
    @GET("image_upload/{id}")
    Call<Details> getDetails(@Path("id") int id);

}
