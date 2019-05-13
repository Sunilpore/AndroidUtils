package com.example.apinotification.utils.network;

import com.google.gson.Gson;
import com.vtsapplication.json_data_list_vehicle.VehicleState;

import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class RetrofitResponseUtil {

    public static String getResponseString(Response<ResponseBody> response) {
        try {
            if (response != null && response.errorBody() != null) {
                return RetrofitResponseUtil.getErrorResponseString(response);
            } else {
                return RetrofitResponseUtil.getDataResponseString(response);
            }
        } catch (Exception e) {
            //return null;
        }
        return null;
    }

    public static String getDataResponseString(Response<ResponseBody> response) {

        try {
            ResponseBody responseBody = response.body();
            return responseBody.string();
        } catch (Exception e) {
            return null;
        }
    }


    public static String getErrorResponseString(Response<ResponseBody> response) {
        try {
            ResponseBody responseErrorBody = response.errorBody();
            return responseErrorBody.string();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert Json string to java Map
     *
     * @param responseString -> json string
     * @param tClass         -> TypeToken converted from map by
     *                       Eg:
     *                       Type type = new TypeToken<Map<String, Object>>() {}.getType();
     * @param <T>            -> the type of the desired object
     * @return -> Converted java Map object
     * Eg: Map<String, Object>
     */
    public static <T> T getMapObject(String responseString, Type tClass) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(responseString, tClass);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert Json string to java object
     *
     * @param responseString -> json string
     * @param tClass         -> Class json object which will be in form of final result
     * @param <T>            -> the type of the desired object
     * @return -> Converted Java object
     */
    public static <T> Object getObject(String responseString, Class<T> tClass) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(responseString, tClass);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert java object to Json string
     *
     * @param obj -> object to convert in json string
     * @return -> converted json string
     */
    public static String getGSONString(Object obj) {
        try {
            Gson gson = new Gson();
            return gson.toJson(obj);
        } catch (Exception e) {
            return null;
        }
    }

    /*public static String getErrorResponseString(final retrofit2.Response<?> response) {
        try {
            ResponseBody responseErrorBody = response.errorBody();
            return responseErrorBody.string();
        } catch (Exception e) {
            return null;
        }
    }*/
}
