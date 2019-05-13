package com.example.apinotification.utils.abstract_method.notify_listener;

public interface OnSuccess {

    void onSuccessObj(String successResponse);

    void onSuccessErrorObj(int errorCode, Object obj);

}
