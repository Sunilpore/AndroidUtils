package com.example.apinotification.utils.abstract_method.interfaces;

import android.content.Context;


import com.example.apinotification.utils.abstract_method.notify_listener.OnError;
import com.example.apinotification.utils.abstract_method.notify_listener.OnSuccess;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public interface Rxapi {

    void observableObj(Observable<Response<ResponseBody>> observable, Context context);

    void observableObjFrag(Observable<Response<ResponseBody>> observable, Context context, OnSuccess onSuccess, OnError error);
}
