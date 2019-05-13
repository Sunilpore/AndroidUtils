package com.example.apinotification.utils.abstract_method;


import com.example.apinotification.utils.constants.Constants;

public class AbstractCreator {

    public static AbstractPackages initializeAbstract(String detector){

        switch (detector){
            case Constants.AbstractVarTag.SUB_CLASS_INITIALIZE:
                return new AssignFactorySubClass();
        }
        return null;
    }

}
