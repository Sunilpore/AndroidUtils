package com.example.apinotification.utils.network;

import okhttp3.ResponseBody;
import retrofit2.Response;


public class NetworkErrorUtil {

    private static final String TAG = NetworkErrorUtil.class.getSimpleName();

    public static int getErrorCode(Response<ResponseBody> error) {

        if (error == null)
            return ErrorCode.Code.UNHANDLED_ERROR;

        if (error.errorBody() == null)
            return ErrorCode.Code.UNHANDLED_ERROR;

        return error.code();
    }

    public static String getMessage(Response<ResponseBody> error) {

        String errorMessage = ErrorCode.Message.UNHANDLED_ERROR;

        if (error == null)
            return errorMessage;

        String json = "";


        switch (error.code()) {
            case ErrorCode.Code.NETWORK_DISABLED_ERROR:
                errorMessage = ErrorCode.Message.NETWORK_DISABLED_ERROR;
                break;
            case ErrorCode.Code.NETWORK_UNAVAILABLE_ERROR:
                errorMessage = ErrorCode.Message.NETWORK_UNAVAILABLE_ERROR;
                break;
            case ErrorCode.Code.BAD_REQUEST:
                errorMessage = error.code() + ": " + ErrorCode.Message.BAD_REQUEST + " " + json;
                break;
            case ErrorCode.Code.FORBIDDEN_REQUEST:
                errorMessage = error.code() + ": " + ErrorCode.Message.FORBIDDEN_REQUEST + " " + json;
                break;
            case ErrorCode.Code.NOT_FOUND:
                errorMessage = error.code() + ": " + ErrorCode.Message.NOT_FOUND + " " + json;
                break;
            case ErrorCode.Code.NOT_ACCEPTABLE:
                errorMessage = error.code() + ": " + ErrorCode.Message.NOT_ACCEPTABLE + " " + json;
                break;
            case ErrorCode.Code.REQUEST_TIMEOUT:
                errorMessage = error.code() + ": " + ErrorCode.Message.REQUEST_TIMEOUT + " " + json;
                break;
            case ErrorCode.Code.GONE:
                errorMessage = error.code() + ": " + ErrorCode.Message.GONE + " " + json;
                break;
            case ErrorCode.Code.TOO_MANY_REQUESTS:
                errorMessage = error.code() + ": " + ErrorCode.Message.TOO_MANY_REQUESTS + " " + json;
                break;
            case ErrorCode.Code.INTERNAL_SERVER_ERROR:
            case ErrorCode.Code.BAD_GATEWAY:
            case ErrorCode.Code.SERVICE_UNAVAILABLE:
            case ErrorCode.Code.GATEWAY_TIMEOUT:
                errorMessage = error.code() + ": " + ErrorCode.Message.INTERNAL_SERVER_ERROR + " " + json;
                break;
            case ErrorCode.Code.NOT_IMPLEMENTED_ON_SERVER:
                errorMessage = error.code() + ": " + ErrorCode.Message.SERVICE_UNAVAILABLE + " " + json;
                break;
        }

        return errorMessage;
    }
}
