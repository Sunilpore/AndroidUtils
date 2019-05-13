package com.example.apinotification.utils.abstract_method.method_class;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.example.apinotification.utils.abstract_method.interfaces.Rxapi;
import com.example.apinotification.utils.abstract_method.notify_listener.OnError;
import com.example.apinotification.utils.abstract_method.notify_listener.OnSuccess;
import com.example.apinotification.utils.exception.ExceptionUtils;
import com.example.apinotification.utils.helper.HelperMethods;
import com.example.apinotification.utils.network.ErrorResponse;
import com.example.apinotification.utils.network.RetrofitResponseUtil;
import com.example.apinotification.utils.network.ServerResponse;
import com.example.apinotification.utils.network.UnProcessErrorResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class RxApiCall implements Rxapi {


    @Override
    public void observableObj(Observable<Response<ResponseBody>> observable, final Context context) {

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {

                        OnSuccess onSuccess = null;
                        if(context!=null){
                        onSuccess = (OnSuccess) context;    
                        }
                        String responseString = RetrofitResponseUtil.getResponseString(response);
                        HelperMethods.showLogData("APIresp: "+responseString);

                        if (responseString != null) {
                            try {
                                switch (response.code()) {

                                    case ServerResponse.Code.OK:

                                        try {

                                            if(onSuccess!=null)
                                            onSuccess.onSuccessObj(responseString);
                                            //ToastHelper.showDefaultToast(context,"Login Successfull",Toast.LENGTH_LONG, Color.GREEN);

                                        } catch (Exception ignored) {
                                            HelperMethods.showLogError("on suceess error:"+ignored.getMessage());
                                            ExceptionUtils.writeCrashLogToFile(context, ignored);
                                        }

                                        break;
                                    case ServerResponse.Code.UN_PROCESSABLE:

                                        Object objUnProcess = RetrofitResponseUtil.getObject(responseString, UnProcessErrorResponse.class);

                                        onSuccess.onSuccessErrorObj(ServerResponse.Code.UN_PROCESSABLE,objUnProcess);

                                        //UnProcessErrorResponse unProcessErrorResponse = UnProcessErrorResponse.class.cast(objUnProcess);

                                        //HelperMethods.showToastLong(context, unProcessErrorResponse.getMessage());

                                        break;
                                    case ServerResponse.Code.BAD_REQUEST:

                                        Object objBadRequest = RetrofitResponseUtil.getObject(responseString, ErrorResponse.class);

                                        onSuccess.onSuccessErrorObj(ServerResponse.Code.BAD_REQUEST,objBadRequest);

                                        //   ErrorResponse errorResponse = ErrorResponse.class.cast(objBadRequest);
                                        //   HelperMethods.showToastLong(context, errorResponse.getMessage());
                                        break;
                                    default:
                                        onSuccess.onSuccessErrorObj(response.code(),responseString);
                                        HelperMethods.showLogError(ServerResponse.Message.UNHANDLED_ERROR);
                                        //HelperMethods.showToastLong(context, ServerResponse.Message.UNHANDLED_ERROR);
                                        break;

                                }

                            } catch (Exception ignored) {
                                    HelperMethods.showLogError(ignored.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        OnError error = (OnError) context;
                        error.onError(e.getMessage());
                        HelperMethods.showLogError("rx error msg: "+e.getMessage());
                        Toast.makeText(context, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    public void observableObjFrag(Observable<Response<ResponseBody>> observable, Context context, final OnSuccess onSuccess, final OnError error) {


        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<ResponseBody> response) {


                       // OnSuccess onSuccess = (OnSuccess) activity;
                        String responseString = RetrofitResponseUtil.getResponseString(response);

                        if (responseString != null) {
                            try {
                                switch (response.code()) {

                                    case ServerResponse.Code.OK:

                                        try {

                                            onSuccess.onSuccessObj(responseString);
                                            //ToastHelper.showDefaultToast(context,"Login Successfull",Toast.LENGTH_LONG, Color.GREEN);

                                        } catch (Exception ignored) {
                                            HelperMethods.showLogError("on success: "+ignored.getMessage());
                                        }

                                        break;
                                    case ServerResponse.Code.UN_PROCESSABLE:

                                        Object objUnProcess = RetrofitResponseUtil.getObject(responseString, UnProcessErrorResponse.class);

                                        onSuccess.onSuccessErrorObj(ServerResponse.Code.UN_PROCESSABLE,objUnProcess);

                                        //UnProcessErrorResponse unProcessErrorResponse = UnProcessErrorResponse.class.cast(objUnProcess);

                                        //HelperMethods.showToastLong(context, unProcessErrorResponse.getMessage());

                                        break;
                                    case ServerResponse.Code.BAD_REQUEST:

                                        Object objBadRequest = RetrofitResponseUtil.getObject(responseString, ErrorResponse.class);

                                        onSuccess.onSuccessErrorObj(ServerResponse.Code.BAD_REQUEST,objBadRequest);

                                        //   ErrorResponse errorResponse = ErrorResponse.class.cast(objBadRequest);
                                        //   HelperMethods.showToastLong(context, errorResponse.getMessage());
                                        break;
                                    default:

                                        onSuccess.onSuccessErrorObj(response.code(),responseString);
                                        HelperMethods.showLogError(ServerResponse.Message.UNHANDLED_ERROR);
                                        //HelperMethods.showToastLong(context, ServerResponse.Message.UNHANDLED_ERROR);
                                        break;

                                }

                            } catch (Exception ignored) {

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //OnError error = (OnError) context;
                        error.onError(e.getMessage());
                        //Toast.makeText(context, "ERROR: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
