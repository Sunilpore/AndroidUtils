package com.example.apinotification.service;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    /*@POST
    Observable<Response<ResponseBody>> login(@Url String url, @Body LoginReqModel loginReqModel);

    @POST
    Observable<Response<ResponseBody>> refreshTokenData(@Url String url, @Body Token para);

    @GET
    Observable<Response<ResponseBody>> updateVirusDefinition(@Url String url, @Header("authorization") String auth);

    @POST
    Observable<Response<ResponseBody>> updateEmail(@Url String url,
                                                   @Header("authorization") String auth,
                                                   @Body UpdateEmail updateEmail);

    @POST
    Observable<Response<ResponseBody>> updatePassword(@Url String url,
                                                      @Header("authorization") String auth,
                                                      @Body UpdatePassword updatePassword);

    @POST
    Observable<Response<ResponseBody>> updateMobileNumber(@Url String url,
                                                          @Header("authorization") String auth,
                                                          @Body UpdateMobileNumber updateMobileNumber);


    @GET
    Observable<Response<ResponseBody>> getUserDetiails(@Url String url, @Header("authorization") String auth);


    @POST
    Observable<Response<ResponseBody>> updateUserDetails(@Url String url,
                                                         @Header("authorization") String auth,
                                                         @Body UserDetailsUpdateReqRes updateReqRes);*/


    @Multipart
    @POST
    Observable<Response<ResponseBody>> postContactFile(@Url String url,
                                                       @Header("authorization") String auth,
                                                       @Part MultipartBody.Part document,
                                                       @Part("count") RequestBody countRequest);


    @Multipart
    @POST
    Observable<Response<ResponseBody>> postSmsInboxFile(@Url String url, @Header("authorization") String auth, @Part MultipartBody.Part document, @Part("count") RequestBody countRequest);


    @Multipart
    @POST
    Observable<Response<ResponseBody>> postSmsSentFile(@Url String url, @Header("authorization") String auth, @Part MultipartBody.Part document, @Part("count") RequestBody countRequest);

    @GET
    Observable<Response<ResponseBody>> getContactsFile(@Url String url, @Header("authorization") String auth);

    @GET
    Observable<Response<ResponseBody>> getSmsInboxFile(@Url String url, @Header("authorization") String auth);

    @GET
    Observable<Response<ResponseBody>> getSmsSentFile(@Url String url, @Header("authorization") String auth);

    @GET
    Observable<Response<ResponseBody>> logout(@Url String url, @Header("authorization") String auth);

}
