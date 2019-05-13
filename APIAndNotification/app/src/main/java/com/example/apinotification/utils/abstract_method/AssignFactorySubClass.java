package com.example.apinotification.utils.abstract_method;


import com.example.apinotification.utils.abstract_method.interfaces.Rxapi;
import com.example.apinotification.utils.abstract_method.method_class.RxApiCall;
import com.example.apinotification.utils.constants.Constants;

public class AssignFactorySubClass extends AbstractPackages {

    @Override
    public Rxapi acessApiCall(String request) {

        switch (request){
            case Constants.AbstractVarTag.NULL_STRING:
                return null;
            case Constants.AbstractVarTag.API_CALL:
                return new RxApiCall();
        }

        return null;
    }


}
