package com.example.apinotification;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.apinotification.service.ApiClient;
import com.example.apinotification.service.ApiInterface;
import com.example.apinotification.utils.abstract_method.AbstractCreator;
import com.example.apinotification.utils.abstract_method.AbstractPackages;
import com.example.apinotification.utils.abstract_method.interfaces.Rxapi;
import com.example.apinotification.utils.abstract_method.notify_listener.OnComplete;
import com.example.apinotification.utils.abstract_method.notify_listener.OnError;
import com.example.apinotification.utils.abstract_method.notify_listener.OnSuccess;
import com.example.apinotification.utils.constants.Constants;
import com.example.apinotification.utils.network.ErrorResponse;
import com.example.apinotification.utils.network.RetrofitResponseUtil;
import com.example.apinotification.utils.network.ServerResponse;
import com.example.apinotification.utils.network.UnProcessErrorResponse;
import com.example.apinotification.utils.toast.ToastHelper;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnSuccess, OnError, OnComplete {


    Context mContext;

    Rxapi photoReq;
    Observable<Response<ResponseBody>> statusOfDevice;
    AbstractPackages loginAbstract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        callApi();

    }





    private void callApi() {

        statusOfDevice = getObservablenew("1");
        loginAbstract = AbstractCreator.initializeAbstract(Constants.AbstractVarTag.SUB_CLASS_INITIALIZE);

        if (loginAbstract != null) {
            photoReq = loginAbstract.acessApiCall(Constants.AbstractVarTag.API_CALL);
            photoReq.observableObj(statusOfDevice, mContext);
        }

    }


    private Observable<Response<ResponseBody>> getObservablenew(String apiType) {
        ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        switch (apiType){

            case "1":return mApiInterface.logout("","");

            case "2":return mApiInterface.getSmsSentFile("","");

            case "3": return mApiInterface.getContactsFile("","");

            default:return null;
        }
    }







    /*
    API Response methods
     */

    //Error response
    @Override
    public void onError(String errorMsg) {

    }


    //Successs Response
    @Override
    public void onSuccessObj(String successResponse) {

        //Convert String Response into obje
        //Object objResponse = RetrofitResponseUtil.getObject(successResponse, PhotoRespModel.class);

        //PhotoRespModel loginResponse = (PhotoRespModel) objResponse;
    }



    //Success Error Respose
    @Override
    public void onSuccessErrorObj(int errorCode, Object obj) {

        switch (errorCode) {
            case ServerResponse.Code.UN_PROCESSABLE:
                UnProcessErrorResponse unProcessErrorResponse = (UnProcessErrorResponse) obj;
                ToastHelper.showDefaultToast(mContext, unProcessErrorResponse.getMessage(), Toast.LENGTH_LONG, Color.GREEN);
                break;

            case ServerResponse.Code.BAD_REQUEST:
                ErrorResponse errorResponse = (ErrorResponse) obj;
                ToastHelper.showDefaultToast(mContext, errorResponse.getMessage(), Toast.LENGTH_LONG, Color.GREEN);
                break;
            default:
                ToastHelper.showDefaultToast(mContext, ServerResponse.Message.UNHANDLED_ERROR, Toast.LENGTH_LONG, Color.GREEN);
                break;
        }

    }



}
